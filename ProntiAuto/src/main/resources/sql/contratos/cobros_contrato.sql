------ COBROS CONTRATO ------

CREATE OR REPLACE FUNCTION cobros_contrato(jsonpar CHAR VARYING) RETURNS CHARACTER VARYING AS $$
    DECLARE
    count INTEGER;
    count2 INTEGER;
    count3 INTEGER;
    num INTEGER;
    num2 INTEGER;
    num3 INTEGER;
    id_cobro INTEGER;
    c public.contrato;
    hpc public.historico_plan_contrato;
    cuo public.cuota;
    monto_pagado_cuota NUMERIC;
    dispositivo_pagado NUMERIC;
    estado_pago_dispositivo CHAR(1);
    rastreo_pagado NUMERIC;
    estado_pago_rastreo CHAR(1);

    ----- historico plan contrato -----
    total_cobro_inscripcion NUMERIC;
    valor_pagado_inscripcion NUMERIC;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_cuotas_cobradas NUMERIC;
    total_monto_cobrado NUMERIC;
    total_cobro_primera_cuota NUMERIC;
    cargos_adjudicacion NUMERIC;
    inscripcion_esta_pagada CHAR(1);
    total_dispositivo_cobrado NUMERIC;
    valor_a_pagar_inscripcion NUMERIC;

    ----- otras variables -----
    cuota_a_cobrar INTEGER;
    valor_cuota NUMERIC;
    monto_pagado_dispositivo NUMERIC;
    monto_pagado_rastreo NUMERIC;
    asiento_contable_cabecera jsonb;

    ----- variables para actualizar grupo y fondo contrato -----
    id_grupo INTEGER;

    ----- tabla cobro -----
    valor_a_cobrar NUMERIC;
    id_contrato INTEGER;
    array_pagos jsonb;
    array_detalle_cobros jsonb;

    ---- tabla pagos ----
    tipo_documento CHAR VARYING;
    observaciones CHAR VARYING;
    numero_documento CHAR VARYING;
    valor NUMERIC;
    fecha_deposito DATE;
    detalle_pagos jsonb;
    id_cuenta_bancaria_empresa INTEGER;

    ----- tabla detalle de pagos
    valor_detalle NUMERIC;
    abono_capital CHAR VARYING;
    cuota_administrativa CHAR VARYING;
    oferta CHAR VARYING;
    cargo_adjudicacion CHAR VARYING;
    numero_cuota_pagos INTEGER;
    tipo CHAR VARYING;
    id_item_cobro_pago INTEGER;
    id_pago INTEGER;

    ----- tabla detalle de cobros
    numero_cuota INTEGER;
    a_cobrar NUMERIC;
    tipo_cobro CHAR VARYING;
    detalle_cobros TEXT;

    ----- asiento contable cabecera -----
    fecha DATE;
    tipo_transaccion CHAR VARYING(2);
    tipo_asiento_contable CHAR VARYING(2);
    codigo_referencial_asiento_contable CHAR VARYING;
    beneficiario CHAR VARYING;
    total_debito NUMERIC;
    total_credito NUMERIC;
    total_saldo_actual_fecha NUMERIC;
    diferencia NUMERIC;
    descripcion CHAR VARYING;
    transaccion_asiento_contable jsonb;

    BEGIN
    ------- sacar valores de jsonpar -------
    valor_a_cobrar := (jsonpar::jsonb->>'valorACobrar')::NUMERIC;
    id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
    array_pagos := (jsonpar::jsonb->>'pagos');
    array_detalle_cobros := (jsonpar::jsonb->>'detalleCobros');
    detalle_cobros := (SELECT jsonb_pretty(array_detalle_cobros));
    ------- creacion del cobro -------
    INSERT INTO cobro (
        "sisActualizado","sisCreado", "sisHabilitado",
        "valorACobrar", "detalleCobros", "idContrato"
    ) VALUES (
        now(), now(), 'S',
        valor_a_cobrar, detalle_cobros, id_contrato
    ) RETURNING id INTO id_cobro;

    count := 0;
    num := jsonb_array_length(array_pagos);

    ----- recorrer pagos -----
    IF (num <> 0) THEN
        WHILE (count < num)
        LOOP
            ----- sacar valores de array_pagos -----
            tipo_documento := ((array_pagos->count)->>'tipoDocumento');
            observaciones := ((array_pagos->count)->>'observaciones');
            numero_documento := ((array_pagos->count)->>'numeroDocumento');
            valor := ((array_pagos->count)->>'valor')::NUMERIC;
            fecha_deposito := ((array_pagos->count)->>'fechaDeposito')::DATE;
            detalle_pagos := ((array_pagos->count)->>'detallePago')::jsonb;
            id_cuenta_bancaria_empresa := ((array_pagos->count)->>'idCuentaBancariaEmpresa')::INTEGER;
            INSERT INTO pago (
                "sisActualizado","sisCreado", "sisHabilitado",
                "tipoDocumento", "observaciones", "numeroDocumento",
                "valor", "fechaDeposito", "idCobro", "idCuentaBancariaEmpresa"
            ) VALUES (
                now(), now(), 'S',
                tipo_documento, observaciones, numero_documento,
                valor, fecha_deposito, id_cobro, id_cuenta_bancaria_empresa
            ) RETURNING id INTO id_pago;

            ----- recorrer detalle de pagos -----
            count2 := 0;
            num2 := jsonb_array_length(detalle_pagos);
            IF(num2 <> 0)THEN
                WHILE (count2 < num2)
                LOOP
                    ----- sacar valores de detalle_pagos -----
                    valor_detalle := ((detalle_pagos->count2)->>'valor')::NUMERIC;
                    abono_capital := ((detalle_pagos->count2)->>'abonoCapital');
                    cuota_administrativa := ((detalle_pagos->count2)->>'cuotaAdministrativa');
                    oferta := ((detalle_pagos->count2)->>'oferta');
                    cargo_adjudicacion := ((detalle_pagos->count2)->>'cargoAdjudicacion');
                    numero_cuota_pagos := ((detalle_pagos->count2)->>'numeroCuota')::INTEGER;
                    tipo := ((detalle_pagos->count2)->>'tipo');
                    id_item_cobro_pago := ((detalle_pagos->count2)->>'idItemCobroPago')::INTEGER;
                    INSERT INTO detalle_pago (
                        "sisActualizado","sisCreado", "sisHabilitado",
                        "valor", "abonoCapital", "cuotaAdministrativa",
                        "oferta", "cargoAdjudicacion", "numeroCuota",
                        "tipo", "idItemCobroPago", "idPago"
                    ) VALUES (
                        now(), now(), 'S',
                        valor_detalle, abono_capital, cuota_administrativa,
                        oferta, cargo_adjudicacion, numero_cuota_pagos,
                        tipo, id_item_cobro_pago, id_pago
                    );
                    count2 := count2 + 1;
                END LOOP;
            END IF;
            count:=count+1;
        END LOOP;
    END IF;

    ----- sacar datos de tablas relacionadas -----
    c := (SELECT c1 FROM contrato c1 WHERE c1.id = id_contrato);
    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE hpc1."idContrato" = c.id ORDER BY hpc1."sisCreado" DESC LIMIT 1);

    ----- sacar datos para actualizar del historico de plan contrato -----
    total_cobro_inscripcion := hpc."totalCobroInscripcion";
    valor_pagado_inscripcion := hpc."valorPagadoInscripcion";
    abonos_capital_actual := hpc."abonosCapitalActual";
    saldo_capital := hpc."saldoCapital";
    total_tasa_administrativa_cobrada := hpc."totalTasaAdministrativaCobrada";
    total_cuotas_cobradas := hpc."totalCuotasCobradas";
    total_monto_cobrado := hpc."totalMontoCobrado";
    total_cobro_primera_cuota := hpc."totalCobroPrimeraCuota";
    cargos_adjudicacion := hpc."cargosAdjudicacion";
    inscripcion_esta_pagada := hpc."inscripcionEstaPagada";

    ----- valores iniciales para actualizar el historico de plan contrato -----
    valor_a_pagar_inscripcion := hpc."totalInscripcionPlan" - hpc."valorDsctoInscripcion";
    monto_pagado_cuota := 0;
    dispositivo_pagado := 0;
    rastreo_pagado := 0;
    monto_pagado_dispositivo := 0;
    monto_pagado_rastreo := 0;

    ----- recorrer detalle de cobros -----
    count3 := 0;
    num3 := jsonb_array_length(array_detalle_cobros);
    IF (num3 <> 0) THEN
        WHILE (count3 < num3)
        LOOP
            ----- sacar valores de array_detalle_cobros -----
            numero_cuota := ((array_detalle_cobros->count3)->>'noCuota')::INTEGER;
            a_cobrar := ((array_detalle_cobros->count3)->>'aCobrar')::NUMERIC;
            tipo_cobro := ((array_detalle_cobros->count3)->>'tipo');
            ----- actualizar cuotas pagadas -----
            cuo := (SELECT cuo1 FROM cuota cuo1 WHERE cuo1."numeroCuota" = numero_cuota AND cuo1."idHistoricoPlanContrato" = hpc.id);
            total_dispositivo_cobrado := (SELECT "totalDispositivoCobrado" FROM historico_plan_contrato WHERE id = hpc.id);
            valor_cuota := cuo."valorCuota" + cuo.dispositivo + cuo.rastreo;
            estado_pago_dispositivo := 'N';
            estado_pago_rastreo := 'N';
            IF (tipo_cobro = 'I') THEN
                inscripcion_esta_pagada := CASE WHEN a_cobrar = valor_a_pagar_inscripcion THEN 'S' ELSE 'N' END;
                total_cobro_inscripcion := total_cobro_inscripcion + a_cobrar;
                valor_pagado_inscripcion := valor_pagado_inscripcion + a_cobrar;
                IF(inscripcion_esta_pagada = 'S')THEN
                    UPDATE contrato SET "cuotaACobrar" = numero_cuota + 1 WHERE id = id_contrato;
                END IF;
            END IF;
            IF (tipo_cobro = 'AC') THEN
                monto_pagado_cuota := monto_pagado_cuota + a_cobrar;
                abonos_capital_actual := abonos_capital_actual + a_cobrar;
                saldo_capital := saldo_capital - a_cobrar;
                UPDATE cuota SET "valorPagadoCuota"= (cuo."valorPagadoCuota" + a_cobrar) WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            ELSEIF (tipo_cobro = 'CA') THEN
                monto_pagado_cuota := monto_pagado_cuota + a_cobrar;
                total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + a_cobrar;
                UPDATE cuota SET "valorPagadoCuota"= (cuo."valorPagadoCuota" + a_cobrar) WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'D') THEN
                monto_pagado_cuota := monto_pagado_cuota + a_cobrar;
                monto_pagado_dispositivo := cuo."valorPagadoDispositivo" + a_cobrar;
                IF(monto_pagado_dispositivo = cuo.dispositivo) THEN
                    total_dispositivo_cobrado := total_dispositivo_cobrado + 1;
                    estado_pago_dispositivo := 'S';
                    monto_pagado_dispositivo := 0;
                END IF;
                UPDATE cuota SET "dispositivoEstaPagado" = estado_pago_dispositivo, "valorPagadoDispositivo"= monto_pagado_dispositivo WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'R') THEN
                monto_pagado_cuota := monto_pagado_cuota + a_cobrar;
                monto_pagado_rastreo := cuo."valorPagadoRastreo" + a_cobrar;
                IF(monto_pagado_rastreo = cuo.rastreo) THEN
                    estado_pago_rastreo := 'S';
                    monto_pagado_rastreo := 0;
                END IF;
                UPDATE cuota SET "rastreoEstaPagado" = estado_pago_rastreo, "valorPagadoDispositivo"= monto_pagado_rastreo WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'CAD') THEN
                cargos_adjudicacion := cargos_adjudicacion + a_cobrar;
                UPDATE historico_plan_contrato SET "cargosAdjudicacion" = cargos_adjudicacion WHERE id = hpc.id;
            END IF;
            IF (monto_pagado_cuota = valor_cuota AND numero_cuota <> 1) THEN
                UPDATE cuota SET "estaPagado" = 'S' WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
                UPDATE contrato SET "cuotaACobrar" = numero_cuota + 1 WHERE id = id_contrato;
                total_cuotas_cobradas := numero_cuota;
                cuota_a_cobrar := numero_cuota + 1;
                total_monto_cobrado := total_monto_cobrado + monto_pagado_cuota;
                monto_pagado_cuota := 0;
            ELSEIF (monto_pagado_cuota < (valor_cuota - hpc."valorDsctoPrimeraCuota") AND numero_cuota = 1) THEN
                total_cobro_primera_cuota := monto_pagado_cuota + total_cobro_primera_cuota;
            ELSEIF (monto_pagado_cuota = (valor_cuota - hpc."valorDsctoPrimeraCuota") AND numero_cuota = 1) THEN
                UPDATE cuota SET "estaPagado" = 'S' WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
                UPDATE contrato SET "cuotaACobrar" = numero_cuota + 1 WHERE id = id_contrato;
                total_cobro_primera_cuota := monto_pagado_cuota;
                monto_pagado_cuota := 0;
            END IF;

            ----- actualizar historico plan contrato -----
            UPDATE historico_plan_contrato SET
            "totalCobroInscripcion" = total_cobro_inscripcion, "valorPagadoInscripcion" = valor_pagado_inscripcion,
            "abonosCapitalActual" = abonos_capital_actual, "saldoCapital" = saldo_capital,
            "totalTasaAdministrativaCobrada" = total_tasa_administrativa_cobrada, "totalCuotasCobradas" = total_cuotas_cobradas,
            "totalMontoCobrado" = total_monto_cobrado, "totalCobroPrimeraCuota" = total_cobro_primera_cuota,
            "cargosAdjudicacion" = cargos_adjudicacion, "inscripcionEstaPagada" = inscripcion_esta_pagada,
            "totalDispositivoCobrado" = total_dispositivo_cobrado
            WHERE id = hpc.id;
            count3 := count3 + 1;
        END LOOP;
    END IF;
    ------- json asiento contable cabecera -------
    fecha := now();
    tipo_transaccion := 'I';
    tipo_asiento_contable := 'B';
    codigo_referencial_asiento_contable := '1';
    beneficiario := ''||c."nombresCliente" || ' ' || c."apellidosCliente";
    total_debito := valor_a_cobrar;
    total_credito := valor_a_cobrar;
    total_saldo_actual_fecha := 0;
    diferencia := 0;
    descripcion := 'cobro de cuota';
    asiento_contable_cabecera := '{
        "fecha":'||fecha||',
        "tipo_transaccion":"'||tipo_transaccion||'",
        "tipo_asiento_contable":"'||tipo_asiento_contable||'",
        "codigo_referencial_asiento_contable":"'||codigo_referencial_asiento_contable||'",
        "beneficiario":"'||beneficiario||'",
        "total_debito":'||total_debito||',
        "total_credito":'||total_credito||',
        "total_saldo_actual_fecha":'||total_saldo_actual_fecha||',
        "diferencia":'||diferencia||',
        "descripcion":"'||descripcion||'",
        "transaccion_asiento_contable":'||transaccion_asiento_contable||'
        }';
    ----- generar asiento contable cabecera -----
    PERFORM public.generar_asiento_contable_cabecera(asiento_contable_cabecera);

    ------- actializar fondo del contrato -------
    PERFORM public.actualizar_fondo_contrato(id_contrato);

    ------- actualizar monto meta del grupo -------
    id_grupo := (SELECT cg."idGrupo" FROM cliente_en_grupo cg INNER JOIN contrato c1 ON c1."idClienteEnGrupo" = cg."id" WHERE c1.id = id_contrato);
    PERFORM actualizar_monto_meta(id_grupo);

    RETURN '{"correcto":true, "message":"El cobro se realizo correctamente"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;
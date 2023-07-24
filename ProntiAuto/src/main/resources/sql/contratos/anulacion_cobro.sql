------ ANULACION DE COBRO ------

    CREATE OR REPLACE FUNCTION anulacion_cobro(jsonbpar CHAR VARYING) RETURNS TEXT AS $$
    DECLARE
    id_cobro INTEGER;
    cob public.cobro;
    con public.contrato;
    hpc public.historico_plan_contrato;
    cuo public.cuota;
    cuo22 public.cuota;
    array_detalle_cobros jsonb;

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

    ----- detalle de cobros -----
    numero_cuota INTEGER;
    a_cobrar NUMERIC;
    tipo_cobro CHAR VARYING;
    dispositivo_pagado NUMERIC;
    valor_primera_cuota NUMERIC;
    tasa_administrativa NUMERIC;

    ----- otras variables -----
    monto_pagado_cuota NUMERIC;
    valor_cuota NUMERIC;
    estado_pago_cuota CHAR(1);
    count INTEGER;
    num INTEGER;
    BEGIN
    id_cobro := (jsonbpar::jsonb->>'id')::INTEGER;
    cob := (SELECT co FROM cobro co WHERE id_cobro = id_cobro);
    con := (SELECT c1 FROM cobro INNER JOIN contrato c1 ON c1.id=cobro."idContrato" WHERE cobro.id=jsonbpar.id_cobro);
    hpc := (SELECT h1 FROM historico_plan_contrato h1 WHERE h1."idContrato"=con.id ORDER BY h1.id DESC LIMIT 1);
    array_detalle_cobros := (cob."detalleCobros")::jsonb;

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
    total_dispositivo_cobrado := hpc."totalDispositivoCobrado";

    ----- recorrer detalle de cobros -----
    monto_pagado_cuota := 0;
    count := 0;
    num := jsonb_array_length(array_detalle_cobros) - 1;
    IF(num >= 0) THEN
        WHILE (num >= count)
        LOOP
            ----- sacar valores de array_detalle_cobros -----
            numero_cuota := ((array_detalle_cobros->num)->>'noCuota')::INTEGER;
            a_cobrar := ((array_detalle_cobros->num)->>'aCobrar')::NUMERIC;
            tipo_cobro := ((array_detalle_cobros->num)->>'tipoCobro')::CHAR VARYING;
            ----- actualizar cuotas a devolver -----
            cuo := (SELECT cuo1 FROM cuota cuo1 WHERE cuo1."numeroCuota" = numero_cuota AND cuo1."idHistoricoPlanContrato" = hpc.id);
            IF(tipo_cobro = 'I') THEN
                inscripcion_esta_pagada := 'N';
                total_cobro_inscripcion := total_cobro_inscripcion - a_cobrar;
                valor_pagado_inscripcion := valor_pagado_inscripcion - a_cobrar;
                UPDATE historico_plan_contrato SET 
                "inscripcionEstaPagada" = inscripcion_esta_pagada,
                "totalCobroInscripcion" = total_cobro_inscripcion, 
                "valorPagadoInscripcion" = valor_pagado_inscripcion
                WHERE id = hpc.id;
            END IF;
            IF (tipo_cobro = 'AC') THEN
                monto_pagado_cuota := cuo."valorPagadoCuota" - a_cobrar;
                abonos_capital_actual := abonos_capital_actual - a_cobrar;
                saldo_capital := saldo_capital + a_cobrar;
                UPDATE cuota SET "valorPagadoCuota"= monto_pagado_cuota WHERE id = cuo.id;
            ELSEIF (tipo_cobro = 'CA') THEN
                monto_pagado_cuota := cuo."valorPagadoCuota" - a_cobrar;
                total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada - a_cobrar;
                UPDATE cuota SET "valorPagadoCuota"= monto_pagado_cuota WHERE id = cuo.id;
            END IF;
            IF (tipo_cobro = 'D') THEN
                monto_pagado_cuota := cuo."valorPagadoDispositivo" - a_cobrar;
                UPDATE cuota SET "valorPagadoDispositivo"= monto_pagado_cuota WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'R') THEN
                monto_pagado_cuota := cuo."valorPagadoRastreo" - a_cobrar;
                UPDATE cuota SET "valorPagadoRastreo"= monto_pagado_cuota WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'CAD') THEN
                cargos_adjudicacion := cargos_adjudicacion - a_cobrar;
                UPDATE historico_plan_contrato SET "cargosAdjudicacion" = cargos_adjudicacion WHERE id = hpc.id;
            END IF;
            num := num - 1;
        END LOOP;
    PERFORM public.cuadrar_cuentas('{"idContrato":'||con.id||',"lastCuota":'||numero_cuota||'}');
    END IF;
    RETURN '{"correcto":true, "message":"La anulacion del cobro se realizo correctamente"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;
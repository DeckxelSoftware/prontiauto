------- cuadre de cuentas -------

    CREATE OR REPLACE FUNCTION cuadrar_cuentas(jsonbpar CHAR VARYING) RETURNS TEXT AS $$
    DECLARE
    id_contrato INTEGER;
    last_cuota INTEGER;
    cuo public.cuota;
    hpc public.historico_plan_contrato;
    ------ historico plan contrato ------
    id_hpc INTEGER;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_cuotas_cobradas NUMERIC;
    total_monto_cobrado NUMERIC;
    total_cobro_primera_cuota NUMERIC;
    total_dispositivo_cobrado INTEGER;

    ------ otras variables ------
    suma_abono_capital NUMERIC;
    suma_tasa_administrativa NUMERIC;
    suma_cuotas NUMERIC;
    numero_cuota_pagos INTEGER;
    sobrante_cuota NUMERIC;
    BEGIN
    id_contrato := (jsonbpar::jsonb->>'idContrato')::INTEGER;
    last_cuota := (jsonbpar::jsonb->>'lastCuota')::INTEGER;
    id_hpc := (SELECT h1.id FROM historico_plan_contrato h1 WHERE h1."idContrato" = id_contrato ORDER BY h1.id DESC LIMIT 1);
    UPDATE cuota SET "estadoPago"='N' 
    WHERE "idHistoricoPlanContrato" = id_hpc 
    AND "valorCuota" > "valorPagadoCuota" 
    AND dispositivo > "valorPagadoDispositivo" 
    AND rastreo > "valorPagadoRastreo";
    UPDATE cuota SET "dispositivoEstaPagado" = 'N' 
    WHERE "idHistoricoPlanContrato" = id_hpc 
    AND dispositivo > "valorPagadoDispositivo";
    UPDATE cuota SET "rastreoEstaPagado" = 'N' 
    WHERE "idHistoricoPlanContrato" = id_hpc
    AND rastreo > "valorPagadoRastreo";
    hpc := (SELECT h1 FROM historico_plan_contrato h1 WHERE h1.id = id_hpc);
    cuo := (SELECT c2 FROM cuota c2 WHERE c2."idHistoricoPlanContrato" = id_hpc AND c2."numeroCuota" = last_cuota);
    suma_cuotas := (SELECT SUM(c1."valorPagadoCuota") FROM cuota c1 WHERE c1."idHistoricoPlanContrato" = id_hpc);
    numero_cuota_pagos := (SELECT floor(suma_cuota / cuo."valorCuota"));
    sobrante_cuota := (suma_cuotas - (numero_cuota_pagos * cuo."valorCuota"));
    IF(sobrante_cuota >= cuo."valorTasaAdministrativa") THEN
        total_tasa_administrativa_cobrada := ((numero_cuota_pagos + 1) * cuo."valorTasaAdministrativa");
        abonos_capital_actual := (numero_cuota_pagos * cuo."abonoCapital") + sobrante_cuota;
    ELSE
        total_tasa_administrativa_cobrada := (numero_cuota_pagos * cuo."valorTasaAdministrativa") + sobrante_cuota;
        abonos_capital_actual := (numero_cuota_pagos * cuo."abonoCapital");
    END IF;
    saldo_capital := hpc."capitalTotal" - abonos_capital_actual;
    total_cuotas_cobradas := numero_cuota_pagos;
    total_monto_cobrado := suma_cuotas;
    total_cobro_primera_cuota := (SELECT c3."valorPagadoCuota" FROM cuota c3 WHERE c3."idHistoricoPlanContrato" = id_hpc AND c3."numeroCuota" = 1);
    total_dispositivo_cobrado := (SELECT count(*) FROM cuota c4 WHERE c4."idHistoricoPlanContrato" = id_hpc AND c4."dispositivoEstaPagado" = 'S');
    UPDATE contrato SET "cuotaACobrar" = last_cuota WHERE id = id_contrato;
    UPDATE historico_plan_contrato SET "abonoCapitalActual" = abonos_capital_actual, "saldoCapital" = saldo_capital, "totalTasaAdministrativaCobrada" = total_tasa_administrativa_cobrada, "totalCuotasCobradas" = total_cuotas_cobradas, "totalMontoCobrado" = total_monto_cobrado, "totalCobroPrimeraCuota" = total_cobro_primera_cuota, "totalDispositivoCobrado" = total_dispositivo_cobrado WHERE id = id_hpc;
    RETURN '{"correcto":true, "message":"El cuadre de cuentas es correcto"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;
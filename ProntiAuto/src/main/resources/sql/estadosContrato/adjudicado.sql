------- ESTADO ADJUDICADO -------

CREATE OR REPLACE FUNCTION estado_adjudicado(jsonpar CHAR VARYING) RETURNS TEXT AS $$
DECLARE
cg public.configuracion_general;
con public.contrato;
historico_plan_contrato public.historico_plan_contrato;
cuo public.cuota;
num INTEGER;
count INTEGER;
new_valor_cuota NUMERIC;
next_cuota public.cuota;
last_cuota public.cuota;
------- articulo -------
id_contrato INTEGER;
BEGIN
num := 12;
count := 1;
id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;

cg := (SELECT cg1 FROM configuracion_general cg1 LIMIT 1);
con := (SELECT c FROM contrato c WHERE c.id = id_contrato);
historico_plan_contrato := (SELECT hpc FROM historico_plan_contrato hpc WHERE hpc."idContrato" = id_contrato ORDER BY hpc.id DESC LIMIT 1);
------------- Actualizacion de la primera cuota con dispositivo y rastreo  -------------
cuo := (SELECT cuo11 FROM cuota cuo11 WHERE cuo11."idHistoricoPlanContrato" = historico_plan_contrato.id AND cuo11."estaPagado"='N' ORDER BY cuo11."numeroCuota" ASC LIMIT 1);
new_valor_cuota := cuo."valorCuota" + cg.dispositivo + cg.rastreo;
UPDATE cuota SET "valorCuota" = new_valor_cuota, dispositivo=cg.dispositivo, rastreo=cg.rastreo WHERE "id"=cuo.id;
------------- Actualizacion de las cuotas posteriores con dispositivo y rastreo  -------------
WHILE (count < num)
LOOP
    next_cuota := (SELECT cuo1 FROM cuota cuo1 WHERE cuo1."idHistoricoPlanContrato" = historico_plan_contrato.id AND cuo1."estaPagado"='N' AND cuo1."numeroCuota" = cuo."numeroCuota" + count);
    new_valor_cuota := next_cuota."valorCuota" + cg.dispositivo + cg.rastreo;
    UPDATE cuota SET "valorCuota" = new_valor_cuota, dispositivo=cg.dispositivo, rastreo=cg.rastreo WHERE "id"=next_cuota.id;
    count := count + 1;
END LOOP;
---------- Actualizar las demas cuotas con el rastreo  -------------
last_cuota := (SELECT cuo2 FROM cuota cuo2 WHERE cuo2."idHistoricoPlanContrato" = historico_plan_contrato.id AND cuo2."estaPagado"='N' ORDER BY cuo2."numeroCuota" DESC LIMIT 1);
num := last_cuota."numeroCuota";
count := next_cuota."numeroCuota" + 1;
WHILE (count <= num)
LOOP
    next_cuota := (SELECT cuo3 FROM cuota cuo3 WHERE cuo3."idHistoricoPlanContrato" = historico_plan_contrato.id AND cuo3."numeroCuota" = count);
    IF (next_cuota."estaPagado" = 'N') THEN
        new_valor_cuota := next_cuota."valorCuota" + cg.rastreo;
        UPDATE cuota SET "valorCuota" = new_valor_cuota, rastreo=cg.rastreo WHERE "id"=next_cuota.id;
    END IF;
    count := count + 1;
END LOOP;
RETURN '{"correcto":true, "message":"La adjudicacion se ha realizado correctamente"}';
ROLLBACK;
END;
$$ LANGUAGE plpgsql;
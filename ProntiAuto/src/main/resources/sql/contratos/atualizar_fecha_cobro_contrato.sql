------------- ATUALIZAR FECHAMENTO COBRO CONTRATO ----------------
CREATE OR REPLACE FUNCTION atualizar_fecha_inicia_cobro_contrato(json_par CHAR VARYING) RETURNS TEXT AS $$
DECLARE
count INTEGER;
num INTEGER;
contrato public.contrato;
num_cuota INTEGER;
new_fecha_inicia_cobro DATE;
fecha_inicia_cobro DATE;
id_historico_plan_contrato INTEGER;
next_month DATE;
BEGIN
fecha_inicia_cobro := (json_par::jsonb->>'fechaIniciaCobro')::DATE;
id_historico_plan_contrato := (json_par::jsonb->>'idHistoricoPlanContrato')::INTEGER;
count := 0;
num_cuota := 0;
num := (SELECT COUNT(*) FROM cuota WHERE "idHistoricoPlanContrato" = id_historico_plan_contrato AND "estaPagado" = 'N' AND "estaMora" = 'N');
contrato := (SELECT c FROM contrato c INNER JOIN historico_plan_contrato hpc ON hpc.id = c.id WHERE hpc.id=id_historico_plan_contrato);
IF num <> 0 THEN
    WHILE (count < num)
    LOOP
        next_month := (select (fecha_inicia_cobro::date + (concat(count ,'month'))::interval));
        num_cuota := count + 1 + contrato."cuotaACobrar";
        UPDATE cuota SET "fechaCobro"= next_month WHERE "idHistoricoPlanContrato" = id_historico_plan_contrato AND "numeroCuota" = num_cuota;
        count:=count+1;
    END LOOP;
END IF;
RETURN '{"correcto":true, "message":"Se actualizo correctamente la fecha de cobro"}';
ROLLBACK;
END;
$$ LANGUAGE plpgsql;
------- LIQUIDAR CONTRATO -------

CREATE OR REPLACE FUNCTION liquidar_contrato(jsonpar CHAR VARYING) RETURNS TEXT AS $$
DECLARE
con public.contrato;
hpc public.historico_plan_contrato;
num_cuotas INTEGER;
result TEXT;
id_contrato INTEGER;
capital_a_liquidar NUMERIC;
tasa_administrativa_a_liquidar NUMERIC;
BEGIN

id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
capital_a_liquidar := (jsonpar::jsonb->>'capitalALiquidar')::NUMERIC;
tasa_administrativa_a_liquidar := (jsonpar::jsonb->>'tasaAdministrativaALiquidar')::NUMERIC;

con := (SELECT co FROM contrato co WHERE co.id = id_contrato);

IF (con.estado = 'ADJ') THEN
    PERFORM public.liquidar_contrato_adjudicacion(id_contrato);
END IF;

PERFORM public.cobros_contrato(jsonpar);

hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE hpc1."idContrato" = id_contrato ORDER BY hpc1."sisCreado" DESC LIMIT 1);
num_cuotas := (SELECT count(cuo) FROM cuota cuo WHERE cuo."idHistoricoPlanContrato" = hpc.id AND cuo."estaPagado" = 'N');

IF (num_cuotas > 0) THEN
    result := '{"correcto":false, "message":"El contrato no tiene cuotas pendientes para poder liquidarlo"}';
    RETURN result;
END IF;

UPDATE contrato SET estado = 'LQD' WHERE id = id_contrato;
UPDATE historico_plan_contrato SET "capitalALiquidar" = capital_a_liquidar, "tasaAdministrativaALiquidar" = tasa_administrativa_a_liquidar WHERE id = hpc.id;

result := '{"correcto":true, "message":"El contrato ha sido liquidado"}';

RETURN result;
ROLLBACK;
END;
$$ LANGUAGE plpgsql;
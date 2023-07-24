------- LIQUIDAR CONTRATO ADJUDICACION -------

CREATE OR REPLACE FUNCTION liquidar_contrato_adjudicacion(id_contrato integer) RETURNS TEXT AS $$
DECLARE
con public.contrato;
hpc public.historico_plan_contrato;
num_cuotas INTEGER;
result TEXT;
BEGIN

con := (SELECT co FROM contrato co WHERE co.id = id_contrato);
hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE hpc1."idContrato" = id_contrato ORDER BY hpc1."sisCreado" DESC LIMIT 1);
num_cuotas := (SELECT count(cuo) FROM cuota cuo WHERE cuo."idHistoricoPlanContrato" = hpc.id);

IF (con.estado <> 'ADJ') THEN
    result := '{"correcto":false, "message":"El contrato no esta en estado adjudicado para poder liquidarlo"}';
    RETURN result;
END IF;

UPDATE cuota SET rastreo = 0 WHERE "idHistoricoPlanContrato" = hpc.id AND "numeroCuota" BETWEEN con."cuotaACobrar" AND num_cuotas;

SELECT public.obtener_contratos('', 10, 1, 'id', 'asc', '{id}', '{'|| id_contrato ||'}') INTO result;

RETURN result;
ROLLBACK;
END;
$$ LANGUAGE plpgsql;


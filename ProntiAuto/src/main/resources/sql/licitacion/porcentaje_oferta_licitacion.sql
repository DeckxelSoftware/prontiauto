CREATE OR REPLACE FUNCTION crear_actualizar_licitacion(id_contrato_par INTEGER, id_licitacion INTEGER,valor_oferta_par NUMERIC, observacion_par VARCHAR(255),crear_par BOOLEAN) RETURNS CHARACTER VARYING as $$
DECLARE
contrato public.contrato;
hpc public.hpc;
plan public.plan;
exist_plan INTEGER;
min_monto INTEGER;
porcetaje_oferta NUMERIC;
valor_oferta NUMERIC;
BEGIN
contrato := (SELECT c FROM contrato c WHERE id = id_contrato_par);
hpc := (SELECT h FROM historico_plan_contrato h WHERE h."idContrato" =id_contrato_par ORDER BY h."sisCreado" ASC LIMIT 1);
plan := (SELECT p FROM plan p WHERE id = hpc."idPlan");
exist_plan := (SELECT COUNT(*) FROM plan pl WHERE pl.id = p.id and lower(pl.modelo) LIKE '%pronti plan%');
IF (exist_plan = 0) THEN
    RETURN '{"correcto":false,"mensaje":"El contrato no tiene un pronti plan"}';
ELSE (contrato.estado = 'CSD') THEN
    RETURN '{"correcto":false,"mensaje":"El contrato no viene de una cesión de derechos"}';
END IF;
min_monto := plan.precio * 0.4;
IF (hpc."totalMontoCobrado" < min_monto) THEN
    RETURN '{"correcto":false,"mensaje":"Debe tener pagado mas del 40% del precio del plan del contrato"}';
END IF;

porcentaje_oferta := (valor_oferta_par / plan.precio) * 100 ;
valor_oferta := (porcentaje_oferta / 100) * plan.precio;

RETURN '{"correcto:":true,"mensaje":"Carta de licitación creada correctamente"}';
END;
$$ LANGUAGE plpgsql;
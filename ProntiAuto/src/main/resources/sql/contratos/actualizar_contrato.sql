--------- ACTUALIZAR CONTRATO ---------

CREATE OR REPLACE FUNCTION actualizar_contrato(json_par CHAR VARYING) RETURNS CHAR VARYING AS $$ DECLARE
contrato public.contrato;
nueva_cuota NUMERIC;
id_contrato INTEGER;
id_cliente INTEGER;
id_plan INTEGER;
id_vendedor INTEGER;
id_historico INTEGER;
result CHAR VARYING;
numero_contrato DOUBLE PRECISION;
fecha_inicio DATE;
fecha_fin DATE;
dscto_inscripcion NUMERIC;
dscto_primera_cuota NUMERIC;
observacion_par VARCHAR;
fecha_inicia_cobro DATE;
estado_par VARCHAR;
plazo_mes_seleccionado INTEGER;
BEGIN
    id_contrato :=(json_par::jsonb->>'idContrato')::INTEGER;
    contrato := (SELECT co FROM contrato co WHERE co.id = id_contrato);
    IF (contrato IS NULL) THEN
        RETURN '{"correcto":false, "message":"El contrato no existe"}';
    END IF;
    IF (contrato.estado='REG') THEN
        SELECT id FROM historico_plan_contrato WHERE "idContrato" = id_contrato INTO id_historico;
        DELETE FROM cuota WHERE "idHistoricoPlanContrato" = id_historico;
        DELETE FROM refinanciamiento WHERE "idHistoricoPlanContrato" = id_historico;
        DELETE FROM historico_plan_contrato WHERE "idContrato" = id_contrato;
        DELETE FROM licitacion WHERE "idContrato" = id_contrato;
        DELETE FROM contrato WHERE id = id_contrato;
        DELETE FROM cliente_en_grupo WHERE id = contrato."idClienteEnGrupo";
        PERFORM public.crear_contrato (json_par);
    ELSE
        RETURN '{"correcto":false, "message":"El contrato debe estar en estado registrado para poder ser actualizado"}';
    END IF;
RETURN '{"correcto":true, "message":"Se actualizo correctamente el contrato"}';
ROLLBACK;
END;
$$ LANGUAGE plpgsql;
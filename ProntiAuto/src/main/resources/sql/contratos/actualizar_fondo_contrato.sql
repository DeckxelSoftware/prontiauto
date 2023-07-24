------- ACTUALIZAR FONDO CONTRATO -------

CREATE OR REPLACE FUNCTION actualizar_fondo_contrato(id_contrato INTEGER) RETURNS CHARACTER VARYING AS $$
DECLARE
    contrato public.contrato; 
    hpc public.historico_plan_contrato;
    licitacion public.licitacion;
    precio_plan NUMERIC;
BEGIN
    contrato := (SELECT co FROM contrato co WHERE co.id = id_contrato);

    IF contrato IS NULL THEN
        RETURN '{"message":"No existe el contrato", correcto: false}';
    END IF;

    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE hpc1."idContrato" = id_contrato ORDER BY hpc1."sisCreado" DESC LIMIT 1);

    IF contrato.estado = 'EPR' THEN
        UPDATE contrato SET "valorFondo" = hpc."abonosCapitalActual" WHERE id = id_contrato;
        RETURN '{"message":"Se actualizo correctamente el valor del fondo del contrato", correcto: true}';
    END IF;

    IF contrato.estado = 'PAD' OR contrato.estado = 'OFR' THEN
        licitacion := (SELECT li FROM licitacion li WHERE li."idContrato" = id_contrato);
        IF licitacion IS NULL THEN
            RETURN '{"message":"No existe una licitacion en el contrato", correcto: false}';
        END IF;
        UPDATE contrato SET "valorFondo" = (hpc."abonosCapitalActual" - licitacion."valor_oferta") WHERE id = id_contrato;
        RETURN '{"message":"Se actualizo correctamente el valor del fondo del contrato", correcto: true}';
    END IF;
   
    IF contrato.estado = 'PAB' OR contrato.estado = 'PAC' OR contrato.estado = 'ADJ' THEN
        precio_plan := (SELECT p."precio" FROM plan p WHERE p.id = hpc."idPlan");
        UPDATE contrato SET "valorFondo" = (hpc."abonosCapitalActual" - precio_plan) WHERE id = id_contrato;
        RETURN '{"message":"Se actualizo correctamente el valor del fondo del contrato", correcto: true}';
    END IF;

RETURN '{"message":"Verifique que el contrato tenga alguno de los siguientes estado: En proceso, Preadjudicado, Ofertado, Preadjudicado buscando,Preadjudicado comprado, Adjudicado", "correcto": false}';
ROLLBACK;
END;
$$ LANGUAGE 'plpgsql';
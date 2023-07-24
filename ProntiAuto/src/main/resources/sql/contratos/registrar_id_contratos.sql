CREATE OR REPLACE FUNCTION registrar_id_contratos(id_contrato) RETURNS text AS $$
DECLARE
json_ids text;
array_ids text[];
result text;
BEGIN
json_ids:= (SELECT "idContratos" FROM contratos WHERE id = id_contrato);
array_ids:= SELECT array_append(string_to_array(json_ids), 3);
contratos := SELECT array_to_string(array_ids, ',');
result := 'UPDATE contratos SET "idContratos" = ' || contratos || ' WHERE id = ' || id_contrato;
EXECUTE result;
return "";
END;
$$ LANGUAGE plpgsql;
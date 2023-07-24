----------- OBTENER TRABAJADORES -----------

    CREATE OR REPLACE FUNCTION obtener_trabajadores(search_par CHAR VARYING, skip_par INTEGER, take_par INTEGER, sort_by_par CHAR VARYING, sort_direction_par CHAR VARYING, parametros_par CHAR VARYING, valores_par CHAR VARYING) RETURNS TEXT AS $$
    DECLARE
        trabajadores jsonb;
        query TEXT;
        query2 TEXT;
        filtros TEXT;
        count INTEGER;
        new_params TEXT[];
        new_values TEXT[];
        result TEXT;
    BEGIN
    SELECT string_to_array(trim('{}' FROM parametros_par), ',') INTO new_params;
    SELECT string_to_array(trim('{}' FROM valores_par), ',') INTO new_values;

    SELECT public.add_filtros_join(new_params, true, new_values) INTO filtros;

    query := '
    SELECT to_jsonb(array_agg(j)) FROM (
        SELECT distinct(tra.*), 
            usu as "idUsuario", 
            (SELECT to_jsonb(j3) as "idAgencia" FROM 
                (SELECT age.*, reg as "idRegicion")
            j3),
            (SELECT array_agg(j4) as "collectionInformacionFinanciera" FROM
                (SELECT info.* FROM informacion_financiera info WHERE info."idTrabajador"=tra.id)
            j4),
            (SELECT array_agg(j5) as "collectionHistorialLaboral" FROM 
                (SELECT his.* FROM historial_laboral his WHERE his."idTrabajador"=tra.id)
            j5)
        FROM trabajador tra
        INNER JOIN usuario usu ON usu.id = tra."idUsuario"
        INNER JOIN agencia age ON age.id = tra."idAgencia"
        INNER JOIN region reg ON reg.id = age."idRegion"
        WHERE (lower(usu.nombres) LIKE lower(''%'||$1||'%'')
        OR lower(usu.apellidos) LIKE lower(''%'||$1||'%'')
        OR lower(usu."documentoIdentidad") LIKE lower(''%'||$1||'%'')
        OR lower(usu.correo) LIKE lower(''%'||$1||'%''))
        '|| filtros ||'
        ORDER BY tra.'||quote_ident($4)||' '||$5||'
        LIMIT $2 OFFSET ($1 * $2)
    ) j';

    query2 := '
        SELECT count(distinct(tra))
        FROM trabajador tra
        INNER JOIN usuario usu ON usu.id = tra."idUsuario"
        INNER JOIN supervisor sup ON sup."idTrabajador" = tra.id
        INNER JOIN agencia age ON age.id = sup."idAgencia"
        INNER JOIN region reg ON reg.id = age."idRegion"
        WHERE (lower(usu.nombres) LIKE lower(''%'||$1||'%'')
        OR lower(usu.apellidos) LIKE lower(''%'||$1||'%'')
        OR lower(usu."documentoIdentidad") LIKE lower(''%'||$1||'%'')
        OR lower(usu.correo) LIKE lower(''%'||$1||'%''))
        '|| filtros ||'';

    EXECUTE query USING skip_par, take_par INTO trabajadores;

    EXECUTE query2 INTO count;

    SELECT jsonb_build_array(trabajadores, count) INTO trabajadores;

    SELECT jsonb_pretty(trabajadores) INTO result;

    RETURN result;

    END;
    $$ LANGUAGE plpgsql;
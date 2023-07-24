------------ OBTENER LICITACIONES ------------

CREATE OR REPLACE FUNCTION obtener_licitaciones(search_par CHAR VARYING, skip_par INTEGER, take_par INTEGER, sort_by_par CHAR VARYING, sort_direction_par CHAR VARYING, parametros_par CHAR VARYING, valores_par CHAR VARYING) RETURNS TEXT AS $$
DECLARE
    licitaciones jsonb;
    query TEXT;
    query2 TEXT;
    filtros TEXT;
    count INTEGER;
    new_params CHAR VARYING[];
    new_values CHAR VARYING[];
	result TEXT;
BEGIN
SELECT CAST(parametros_par AS CHAR VARYING[]) INTO new_params;
SELECT CAST(valores_par AS CHAR VARYING[]) INTO new_values;

SELECT public.add_filtros('l', true, new_params, new_values) INTO filtros;

query := '
SELECT to_jsonb(array_agg(j)) FROM (
    SELECT distinct(l.*), 
        ( SELECT to_jsonb(j22) AS "idContrato" FROM 
            ( SELECT c.*, 
                ( SELECT to_jsonb(j2) AS "idClienteEnGrupo" FROM 
                    ( SELECT cg1.*, g1 as "idGrupo", 
                        (SELECT to_jsonb(j4) as "idCliente" FROM ( SELECT cl1.*, u1 as "idUsuario" ) j4)
                    ) 
                j2),
                ( SELECT to_jsonb(j5) AS "idVendedor" FROM 
                    ( SELECT v1.*, 
                        ( SELECT to_jsonb(j7) as "idAgencia" FROM 
                            ( SELECT a1.*, 
                                ( SELECT array_agg(j8) as "supervisorCollection" FROM 
                                    ( SELECT s2.*, 
                                        ( SELECT to_jsonb(j9) as "idTrabajador" FROM 
                                            ( SELECT t3.*, ( SELECT u3 as "idUsuario" FROM usuario u3 WHERE u3.id=t3."idUsuario") 
                                            FROM trabajador t3 WHERE t3.id=s2."idTrabajador") 
                                        j9) 
                                    FROM supervisor s2 WHERE a1.id = s2."idAgencia")
                                j8)
                            ) 
                        j7),
                        ( SELECT to_jsonb(j6) as "idTrabajador" FROM ( SELECT t1.*, u2 as "idUsuario" ) j6)
                    ) 
                j5),
                ( SELECT array_agg(j3) AS "historicoPlanContratoCollection" FROM
                    ( SELECT hpc.*, pl1 as "idPlan",
                        ( SELECT array_agg(cuo) AS "cuotaCollection" FROM cuota cuo WHERE cuo."idHistoricoPlanContrato"=hpc.id),
                        ( SELECT array_agg(r) AS "refinanciamientoCollection" FROM refinanciamiento r WHERE r."idHistoricoPlanContrato"=hpc.id)
                    FROM historico_plan_contrato hpc
                    WHERE hpc."idContrato"=c.id
                    )
                j3)
            )
        j22)
    FROM licitacion l
    INNER JOIN contrato c ON c.id=l."idContrato"
    INNER JOIN cliente_en_grupo cg1 ON cg1.id = c."idClienteEnGrupo"
    INNER JOIN cliente cl1 ON cl1.id = cg1."idCliente"
    INNER JOIN usuario u1 ON u1.id = cl1."idUsuario"
    INNER JOIN grupo g1 ON g1.id = cg1."idGrupo" 
    INNER JOIN historico_plan_contrato hpc1 on hpc1."idContrato"=c.id
    INNER JOIN plan pl1 ON pl1.id = hpc1."idPlan"
    INNER JOIN cuota cuo1 ON cuo1."idHistoricoPlanContrato"=hpc1.id
    INNER JOIN vendedor v1 ON v1.id = c."idVendedor"
    INNER JOIN agencia a1 ON a1.id = v1."idAgencia"
    INNER JOIN trabajador t1 ON t1.id = v1."idTrabajador"
    INNER JOIN usuario u2 ON u2.id = t1."idUsuario"
    WHERE (lower(CAST(c."numeroDeContrato" AS CHAR VARYING)) LIKE lower(''%'||$1||'%'')
    OR lower(u1.nombres) LIKE lower(''%'||$1||'%'')
    OR lower(u1.apellidos) LIKE lower(''%'||$1||'%'')
    OR lower(u1."documentoIdentidad") LIKE lower(''%'||$1||'%''))
    '|| filtros ||'
    ORDER BY l.'||quote_ident($4)||' '||$5||'
    LIMIT $2 OFFSET ($1 * $2)
) j';

query2 := '
    SELECT count(distinct(l))
    FROM licitacion l
    INNER JOIN contrato c ON c.id=l."idContrato"
    INNER JOIN cliente_en_grupo cg1 ON cg1.id = c."idClienteEnGrupo"
    INNER JOIN cliente cl1 ON cl1.id = cg1."idCliente"
    INNER JOIN usuario u1 ON u1.id = cl1."idUsuario"
    INNER JOIN grupo g1 ON g1.id = cg1."idGrupo" 
    INNER JOIN historico_plan_contrato hpc1 on hpc1."idContrato"=c.id
    INNER JOIN plan pl1 ON pl1.id = hpc1."idPlan"
    INNER JOIN cuota cuo1 ON cuo1."idHistoricoPlanContrato"=hpc1.id
    INNER JOIN vendedor v1 ON v1.id = c."idVendedor"
    INNER JOIN trabajador t1 ON t1.id = v1."idTrabajador"
    INNER JOIN usuario u2 ON u2.id = t1."idUsuario"
    WHERE (lower(CAST(c."numeroDeContrato" AS CHAR VARYING)) LIKE lower(''%'||$1||'%'')
    OR lower(u1.nombres) LIKE lower(''%'||$1||'%'')
    OR lower(u1.apellidos) LIKE lower(''%'||$1||'%'')
    OR lower(u1."documentoIdentidad") LIKE lower(''%'||$1||'%''))
    '|| filtros ||'';

EXECUTE query USING skip_par, take_par INTO licitaciones;

EXECUTE query2 INTO count;

SELECT jsonb_build_array(licitaciones, count) INTO licitaciones;

SELECT jsonb_pretty(licitaciones) INTO result;

RETURN result;

END;
$$ LANGUAGE plpgsql;
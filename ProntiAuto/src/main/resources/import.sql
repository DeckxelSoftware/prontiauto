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

---------- ADD FILTROS JOIN ----------

    CREATE OR REPLACE FUNCTION add_filtros_join(params CHAR VARYING[], search BOOLEAN, values_par TEXT[]) RETURNS TEXT AS $$
    DECLARE
        result TEXT;
        count INTEGER;
        num INTEGER;
        exist INTEGER;
    BEGIN
    count := 1;
    num := array_length(params, 1);
    exist := char_length(params[num]);
    result := '';
    IF exist <> 0 THEN
    result := case when search = true then ' AND ( ' else ' WHERE ( ' end;
        WHILE (count <= num)
        LOOP
            IF (count = 1) THEN
                result := result || ' ('|| params[count] || '=' || quote_literal(values_par[count]) || ') ';
            ELSE
                result := result || ' AND ('|| params[count] || '=' || quote_literal(values_par[count]) || ') ';
            END IF;
            count:=count+1;
        END LOOP;
        result := result || ') ';
    END IF;

    RETURN result;
    END;
    $$ LANGUAGE plpgsql;

----------- CREAR PRESTAMO -----------

    CREATE OR REPLACE FUNCTION crear_prestamo(jsonpar CHAR VARYING) RETURNS TEXT AS $$
    DECLARE
    trabajador public.trabajador;
    id_prestamo INTEGER;
    id_trabajador INTEGER;
    tipo_prestamo CHAR VARYING;
    valor NUMERIC;
    cuotas INTEGER;
    tasa_interes NUMERIC;
    concepto CHAR VARYING;
    nombre_apellido_responsable CHAR VARYING;
    comprobante_egreso CHAR VARYING;
    modalidad_descuento CHAR VARYING;
    BEGIN
    id_trabajador :=(jsonpar::jsonb->>'idTrabajador')::INTEGER;
    tipo_prestamo :=(jsonpar::jsonb->>'tipoPrestamo')::CHAR VARYING;
    valor :=(jsonpar::jsonb->>'valor')::NUMERIC;
    cuotas :=(jsonpar::jsonb->>'cuotas')::INTEGER;
    tasa_interes :=(jsonpar::jsonb->>'tasaInteres')::NUMERIC;
    concepto :=(jsonpar::jsonb->>'concepto')::CHAR VARYING;
    nombre_apellido_responsable :=(jsonpar::jsonb->>'nombreApellidoResponsable')::CHAR VARYING;
    comprobante_egreso :=(jsonpar::jsonb->>'comprobanteEgreso')::CHAR VARYING;
    modalidad_descuento :=(jsonpar::jsonb->>'modalidadDescuento')::CHAR VARYING;

    INSERT INTO prestamo(
        "sisCreado","sisActualizado","sisHabilitado",
        "idTrabajador","tipoPrestamo","fechaPrestamo",
        "comprobanteEgreso",valor,cuotas,"tasaInteres",
        "concepto",estado,"modalidadDescuento",
        "totalPagado","totalSaldo","nombreApellidoResponsable","estadoSolicitud"
    ) VALUES (
        now(),now(),'A',
        id_trabajador,tipo_prestamo,now(),
        comprobante_egreso,valor,cuotas,tasa_interes,
        concepto,'PNT',modalidad_descuento,
        0,0,nombre_apellido_responsable,'VIG'
    ) RETURNING id INTO id_prestamo;

    PERFORM public.crear_abonos_prestamo(
        cuotas,modalidad_descuento,valor,tasa_interes,id_prestamo
    );

    RETURN '{"correcto":true, "message":"Se creo correctamente el prestamo"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;

------------- CREAR ABONOS PRESTAMO -------------

    CREATE OR REPLACE FUNCTION crear_abonos_prestamo(num_cuotas INTEGER, modalidad_descuento CHAR VARYING, monto NUMERIC, tasa_interes NUMERIC, id_prestamo INTEGER) RETURNS TEXT AS $$
    DECLARE
    last_date_of_month DATE;
    count INTEGER;
    valor_cuota NUMERIC;
    valor_capital NUMERIC;
    valor_tasa_interes NUMERIC;
    mes CHAR VARYING;
    anio INTEGER;
    num_mes INTEGER;
    suma_valor_capital NUMERIC;
    total_valor_capital NUMERIC;
    BEGIN
    suma_valor_capital := 0;
    count := 1;
    valor_capital := (monto/num_cuotas);
    total_valor_capital := (valor_capital * num_cuotas);
    WHILE (count <= num_cuotas)
        LOOP
        last_date_of_month := (select (date_trunc('month', now()) + concat(count,' month - 1 day')::interval)::date);
            num_mes := (SELECT EXTRACT(MONTH FROM last_date_of_month));
            mes := CASE num_mes WHEN 1 THEN 'ENE' WHEN 2 THEN 'FEB' WHEN 3 THEN 'MAR' WHEN 4 THEN 'ABR' WHEN 5 THEN 'MAY' WHEN 6 THEN 'JUN' WHEN 7 THEN 'JUL' WHEN 8 THEN 'AGO' WHEN 9 THEN 'SEP' WHEN 10 THEN 'OCT' WHEN 11 THEN 'NOV' WHEN 12 THEN 'DIC' END;
            anio := (SELECT EXTRACT(YEAR FROM last_date_of_month));
            valor_capital := CASE count WHEN num_cuotas THEN (monto - suma_valor_capital) ELSE valor_capital END;
            valor_tasa_interes := (valor_capital * (tasa_interes/100));
            valor_cuota := (valor_capital + (valor_capital * valor_tasa_interes));
            suma_valor_capital := suma_valor_capital + valor_capital;
            INSERT INTO abono_prestamo (
                "sisCreado","sisActualizado","sisHabilitado",
                "idPrestamo","fechaPago","numeroPago",
                "modalidadDescuento",mes,anio,
                "valorCuota","valorCapital","valorTasaInteres","estaPagado"
            ) VALUES (
                now(),now(),'A',
                id_prestamo,last_date_of_month,count,
                modalidad_descuento, mes, anio,
                valor_cuota,valor_capital,valor_tasa_interes,'P'
            );
            count := count + 1;
        END LOOP;

    RETURN '{"correcto":true, "message":"se aprobo correctamente el prestamo"}';
    END;
    $$ LANGUAGE plpgsql;

------------- ACTUALIZAR MONTO META -------------

    CREATE OR REPLACE FUNCTION actualizar_monto_meta(id_grupo INTEGER) RETURNS CHARACTER VARYING AS $$
    DECLARE
        valores_fondo NUMERIC[];
        suma_monto_meta NUMERIC;
        count INTEGER;
        num INTEGER;
    BEGIN
        count := 1;
        valores_fondo := (SELECT array_agg(co."valorFondo") FROM contrato co INNER JOIN cliente_en_grupo cg ON cg.id=co."idClienteEnGrupo" WHERE cg."idGrupo"=id_grupo);
        num := array_length(valores_fondo, 1);
        suma_monto_meta := 0;

        WHILE (count <= num) LOOP
            suma_monto_meta := suma_monto_meta + valores_fondo[count];
            count := count + 1;
        END LOOP;

        UPDATE grupo SET "sumatoriaMontoMeta" = suma_monto_meta WHERE id = id_grupo;

    RETURN '{"message":"Se realizo correctamente la actualizacion del monto meta", correcto: true, "sumaMontoMeta": ' || suma_monto_meta || '}';
    ROLLBACK;
    END;
    $$ LANGUAGE 'plpgsql';

---------- ACTUALIZAR GRUPO ----------

    CREATE OR REPLACE FUNCTION actualizar_grupo(ids_grupo INTEGER[], montos NUMERIC[]) RETURNS void AS $$
    DECLARE
    grupos public.grupo[];
    count INTEGER;
    num INTEGER;
    -- suma monto para el grupo mas antiguo
    smm DOUBLE PRECISION;
    BEGIN
    count:=1;
    num:=cardinality(ids_grupo);
    raise notice 'ids_grupo: %', num;
    while (count <= num)
        LOOP
            grupos[count] := (SELECT g FROM grupo g WHERE g."nombreGrupo"=ids_grupo[count]);
            IF (num > 1) THEN
                smm := 0;
                IF (count = 1) THEN
                    smm := grupos[count]."sumatoriaMontoMeta" + montos[count];
                    UPDATE grupo SET "sisActualizado" = now(), "sumatoriaMontoMeta" = smm WHERE "nombreGrupo" = ids_grupo[count];
                ELSE
                    smm := grupos[count]."sumatoriaMontoMeta" - montos[count];
                    UPDATE grupo SET "sisActualizado" = now(), "sumatoriaMontoMeta" = smm WHERE "nombreGrupo" = ids_grupo[count];
                END IF;
            ELSE
                smm := grupos[count]."sumatoriaMontoMeta" - montos[count];
                UPDATE grupo SET "sisActualizado" = now(), "sumatoriaMontoMeta" = smm WHERE "nombreGrupo" = ids_grupo[count];
            END IF;
            count:=count+1;
        END LOOP;
    END;
    $$ LANGUAGE 'plpgsql';

------------ CUOTAS_REFINANCIAMIENTO ------------

    create or replace function cuotas_refinanciamiento(cuota_par public.cuota, plan_par public.plan, valor_cuota_par NUMERIC, abono_capital_par NUMERIC, tasa_administrativa_par NUMERIC, impuesto_par NUMERIC, resto_inicio_par NUMERIC, numero_cuotas_inicio_par NUMERIC, resto_final_par NUMERIC, pms_par INTEGER, id_historico_par INTEGER) returns void as $$
    Declare
    cg public.configuracion_general;
    hpc public.historico_plan_contrato;
    cont INTEGER;
    mes_siguiente DATE;
    esta_pagado CHAR VARYING;
    valor_cuota NUMERIC;
    valor_pagado_cuota NUMERIC;
    valor_tasa_administrativa NUMERIC;
    valor_impuesto NUMERIC;
    abono_capital NUMERIC;
    cuotas_final INTEGER;
    begin
    cont:=1;
    cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
    hpc := (SELECT h FROM historico_plan_contrato h WHERE id = id_historico_par);
    cuotas_final := pms_par - hpc."numCuotasAbajoHaciaArriba";

    while(cont <= pms_par)
        loop
            mes_siguiente := (select (cuota_par."fechaCobro"::date + (concat(cont ,'month'))::interval));
            esta_pagado := 'N';
            valor_pagado_cuota := 0;

            if(cont <= numero_cuotas_inicio_par OR cont > cuotas_final) then
                valor_cuota := cuota_par."valorCuota";
                valor_tasa_administrativa := cuota_par."valorTasaAdministrativa";
                valor_impuesto := cuota_par."valorImpuesto";
                abono_capital := cuota_par."abonoCapital";
                valor_pagado_cuota := cuota_par."valorCuota";
                esta_pagado := 'S';
            elseif(cont = (numero_cuotas_inicio_par + 1)) then
                valor_pagado_cuota := resto_inicio_par;
                valor_cuota := valor_cuota_par;
                valor_tasa_administrativa := tasa_administrativa_par;
                valor_impuesto := impuesto_par;
                abono_capital := abono_capital_par;
            elseif(cont = (cuotas_final - 1)) then
                valor_pagado_cuota := resto_final_par;
                valor_cuota := valor_cuota_par;
                valor_tasa_administrativa := tasa_administrativa_par;
                valor_impuesto := impuesto_par;
                abono_capital := abono_capital_par;
            else
                valor_cuota := valor_cuota_par;
                valor_tasa_administrativa := tasa_administrativa_par;
                valor_impuesto := impuesto_par;
                abono_capital := abono_capital_par;
            end if;

            insert into cuota (
                "sisActualizado", "sisCreado", "sisHabilitado",
                "idHistoricoPlanContrato", "fechaCobro", "numeroCuota",
                "valorCuota", "valorPagadoCuota", "valorTasaAdministrativa",
                "valorImpuesto", "abonoCapital", "estaPagado",
                "estaMora", "dispositivo", "rastreo",
                "dispositivoEstaPagado", "rastreo_esta_pagado"
            ) values (
                now(),now(),'A',
                id_historico_par, mes_siguiente, cont,
                valor_cuota, valor_pagado_cuota, valor_tasa_administrativa,
                valor_impuesto, abono_capital, esta_pagado,
                'N', 0, 0,
                'N', 'N'
            );
            cont:=cont+1;
        end loop;
    end;
    $$ language 'plpgsql';

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

--------- CREATE PLAN ---------

    CREATE OR REPLACE FUNCTION crear_plan(jsonpar CHAR VARYING) RETURNS TEXT AS $$
    DECLARE
    count INTEGER;
    num INTEGER;
    array_plan jsonb;
    modelo CHAR VARYING;
    precio NUMERIC;
    cuota_mes12 NUMERIC;
    cuota_mes24 NUMERIC;
    cuota_mes36 NUMERIC;
    cuota_mes48 NUMERIC;
    cuota_mes60 NUMERIC;
    cuota_mes72 NUMERIC;
    cuota_mes84 NUMERIC;
    inscripcion NUMERIC;
    sis_habilitado CHAR VARYING;
    BEGIN
    array_plan :=(jsonpar::jsonb->>'planes');
    count := 0;
    num := jsonb_array_length(array_plan);
    IF num <> 0 THEN
        WHILE (count < num)
        LOOP
            modelo := ((array_plan->count)->>'modelo');
            precio := ((array_plan->count)->>'precio');
            cuota_mes12 := ((array_plan->count)->>'cuotaMes12');
            cuota_mes24 := ((array_plan->count)->>'cuotaMes24');
            cuota_mes36 := ((array_plan->count)->>'cuotaMes36');
            cuota_mes48 := ((array_plan->count)->>'cuotaMes48');
            cuota_mes60 := ((array_plan->count)->>'cuotaMes60');
            cuota_mes72 := ((array_plan->count)->>'cuotaMes72');
            cuota_mes84 := ((array_plan->count)->>'cuotaMes84');
            inscripcion := ((array_plan->count)->>'inscripcion');
            sis_habilitado := ((array_plan->count)->>'sisHabilitado');

            INSERT INTO plan ("sisActualizado", "sisCreado","cuotaMes12", "cuotaMes24", "cuotaMes36", "cuotaMes48", "cuotaMes60", "cuotaMes72", "cuotaMes84", precio, modelo, inscripcion, "sisHabilitado")
            VALUES (now(), now(), cuota_mes12, cuota_mes24, cuota_mes36, cuota_mes48, cuota_mes60, cuota_mes72, cuota_mes84, precio, modelo, inscripcion, sis_habilitado);
            count:=count+1;
        END LOOP;
    END IF;
    RETURN '{"correcto":true, "message":"Se creo correctamente el plan"}';
    END;
    $$ LANGUAGE plpgsql;

----------- OBTENER CONTRATOS -----------

    CREATE OR REPLACE FUNCTION obtener_contratos(search_par CHAR VARYING, skip_par INTEGER, take_par INTEGER, sort_by_par CHAR VARYING, sort_direction_par CHAR VARYING, parametros_par CHAR VARYING, valores_par CHAR VARYING) RETURNS TEXT AS $$
    DECLARE
        contratos jsonb;
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

    SELECT public.add_filtros('c', true, new_params, new_values) INTO filtros;

    query := '
    SELECT to_jsonb(array_agg(j)) FROM (
        SELECT distinct(c.*),
            ( SELECT array_agg(j22) AS "licitacionCollection" FROM 
                ( SELECT l1.* FROM licitacion l1 WHERE l1."idContrato" = c.id )
            j22),
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
                ( SELECT array_agg(json_cuo) AS "cuotaCollection" FROM 
                    ( SELECT cuo.* FROM cuota cuo WHERE cuo."idHistoricoPlanContrato"=hpc.id ORDER BY cuo."numeroCuota" ASC)
                json_cuo),
                ( SELECT array_agg(r) AS "refinanciamientoCollection" FROM refinanciamiento r WHERE r."idHistoricoPlanContrato"=hpc.id)
                FROM historico_plan_contrato hpc
                WHERE hpc."idContrato"=c.id
                )
            j3)
        FROM contrato c
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
        OR lower(u1."documentoIdentidad") LIKE lower(''%'||$1||'%'')
        OR lower(CAST(cl1.id AS CHAR VARYING)) LIKE lower(''%'||$1||'%''))
        '|| filtros ||'
        ORDER BY c.'||quote_ident($4)||' '||$5||'
        LIMIT $2 OFFSET ($1 * $2)
    ) j';

    query2 := '
        SELECT count(distinct(c))
        FROM contrato c
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
        OR lower(u1."documentoIdentidad") LIKE lower(''%'||$1||'%'')
        OR lower(CAST(cl1.id AS CHAR VARYING)) LIKE lower(''%'||$1||'%''))
        '|| filtros ||'';

    EXECUTE query USING skip_par, take_par INTO contratos;

    EXECUTE query2 INTO count;

    SELECT jsonb_build_array(contratos, count) INTO contratos;

    SELECT jsonb_pretty(contratos) INTO result;

    RETURN result;

    END;
    $$ LANGUAGE plpgsql;

--------- CUOTAS MORA ---------

    CREATE OR REPLACE FUNCTION cuotas_mora() RETURNS TEXT AS $$
    DECLARE
        count INTEGER;
        cuotas public.cuota[];
        num INTEGER;
        fecha_vencimiento DATE;
        total_cuotas_mora INTEGER;
        total_cuotas_mora_actual INTEGER;
    BEGIN
    count := 1;
    total_cuotas_mora := 0;
    total_cuotas_mora_actual := 0;
    cuotas := (SELECT array_agg(c) FROM cuota c where c."estaPagado"='N' AND c."estaMora"='N');
    IF (cuotas IS NULL) THEN
        RETURN '{"correcto":true,"message":"no se encontraron cuotas en mora"}';
    END IF;
    num := array_length(cuotas, 1);
        WHILe (count <= num)
        LOOP
            fecha_vencimiento := (SELECT (cuotas[count]."fechaCobro" + 10));
            IF fecha_vencimiento < CURRENT_DATE THEN
                UPDATE cuota SET "fechaMora" = fecha_vencimiento WHERE "id" = cuotas[count].id;
                UPDATE cuota SET "estaMora" = 'S' WHERE id = cuotas[count].id;
                total_cuotas_mora := (SELECT "totalCuotasMora" FROM historico_plan_contrato WHERE id = cuotas[count]."idHistoricoPlanContrato") + 1;
                total_cuotas_mora_actual := (SELECT "totalCuotasMoraActual" FROM historico_plan_contrato WHERE id = cuotas[count]."idHistoricoPlanContrato")+ 1;
                UPDATE historico_plan_contrato SET "totalCuotasMora"= total_cuotas_mora WHERE id=cuotas[count]."idHistoricoPlanContrato";
                UPDATE historico_plan_contrato SET "totalCuotasMoraActual"= total_cuotas_mora_actual WHERE id=cuotas[count]."idHistoricoPlanContrato";
            END IF;
            count:=count+1;
        END LOOP;
    RETURN '{"correcto":true,"message":"se calculo correctamente los cuotas en mora"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;

--------- CREAR TABLA CUOTAS ---------

    create or replace function crear_tabla_cuotas(fecha_cobro_par date, valor_cuota_par NUMERIC, valor_pagado_cuota_par NUMERIC, valor_tasa_administrativa_par NUMERIC, valor_impuesto_par NUMERIC, abono_capital_par NUMERIC, esta_mora_par CHAR VARYING, plazo_par integer, cuotas_pagadas_par integer, id_historico_plan_contrato_par integer) returns void as $$
    Declare
    cont int4;
    mes_siguiente date;
    mes_siguiente0 date;
    esta_pagado CHAR VARYING;
    inicio_pago integer;
    valor_pagado_cuota NUMERIC;
    begin
    cont:=1;
    inicio_pago:=plazo_par - cuotas_pagadas_par;

    while(cont<=plazo_par)
        loop
        mes_siguiente:=(select (fecha_cobro_par::date + (concat(cont ,'month'))::interval));
        esta_pagado:='N';
        valor_pagado_cuota:=0;

            if(cont > inicio_pago) then
                esta_pagado := 'S';
                valor_pagado_cuota := valor_cuota_par;
            end if;
            if(cont = inicio_pago) then
                valor_pagado_cuota := valor_pagado_cuota_par;
            end if;
            insert into cuota (
                "sisActualizado", "sisCreado", "sisHabilitado",
                "idHistoricoPlanContrato", "fechaCobro", "numeroCuota",
                "valorCuota", "valorPagadoCuota", "valorTasaAdministrativa",
                "valorImpuesto", "abonoCapital", "estaPagado",
                "estaMora", "dispositivo", "rastreo",
                "dispositivoEstaPagado", "rastreoEstaPagado", "valorPagadoDispositivo", "valorPagadoRastreo"
            ) values (
                now(),now(), 'A',
                id_historico_plan_contrato_par, mes_siguiente, cont,
                valor_cuota_par, valor_pagado_cuota, valor_tasa_administrativa_par, valor_impuesto_par, abono_capital_par, esta_pagado,
                esta_mora_par,  0, 0,
                'N', 'N', 0, 0
            );
        cont:=cont+1;
        end loop;
    end;
    $$ language 'plpgsql';

---------- ADD_FILTROS ----------

    CREATE OR REPLACE FUNCTION add_filtros(tabla_par CHAR VARYING, and_par BOOLEAN, parametros_par TEXT[], valores_par TEXT[]) RETURNS TEXT AS $$
    DECLARE
        result TEXT;
        count INTEGER;
        num INTEGER;
        exist INTEGER;
    BEGIN
    count := 1;
    num := array_length(parametros_par, 1);
    exist := char_length(parametros_par[num]);
    result := '';
    IF exist <> 0 THEN
    result := case when and_par = true then ' AND ( ' else ' WHERE ( ' end;
        WHILE (count <= num)
        LOOP
            IF (count = 1) THEN
                result := result || ' ('|| tabla_par || '.' || quote_ident(parametros_par[count]) || '=' || quote_literal(valores_par[count]) || ') ';
            ELSE
                result := result || ' AND ('|| tabla_par || '.' || quote_ident(parametros_par[count]) || '=' || quote_literal(valores_par[count]) || ') ';
            END IF;
            count:=count+1;
        END LOOP;
        result := result || ') ';
    END IF;

    RETURN result;
    END;
    $$ LANGUAGE plpgsql;

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

------- ACTUALIZAR IVA ----------

    CREATE OR REPLACE FUNCTION actualizar_iva(iva_par NUMERIC, id_par INTEGER) RETURNS TEXT AS $$
    DECLARE
    iva NUMERIC;
    valor_impuesto NUMERIC;
    nueva_cuota NUMERIC;
    cuotas public.cuota[];
    count INTEGER;
    num INTEGER;
    BEGIN
    count := 1;
    UPDATE configuracion_general SET "ivaPorcentaje"=iva_par WHERE id=id_par;
    iva := (SELECT conf."ivaPorcentaje" FROM configuracion_general conf WHERE conf.id=id_par);
    cuotas := (SELECT array_agg(cuo) FROM cuota cuo WHERE cuo."estaPagado"='N');
    num := array_length(cuotas, 1);
        WHILE (count <= num)
            LOOP
                valor_impuesto := (cuotas[count]."valorTasaAdministrativa" * (iva / 100));
                nueva_cuota := (cuotas[count]."valorCuota" + (valor_impuesto - cuotas[count]."valorImpuesto"));
                UPDATE cuota SET "valorImpuesto"=valor_impuesto, "valorCuota"=nueva_cuota  WHERE id=cuotas[count].id;
                count := count+1;
            END LOOP;
    RETURN '{"correcto":true, "message":"Se actualizo el iva correctamente"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;

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

------- ACTUALIZAR MONTO CONTRATO -------

    CREATE OR REPLACE FUNCTION actualizar_monto_contrato(jsonpar CHAR VARYING) RETURNS CHARACTER VARYING AS $$
    DECLARE
    contrato public.contrato;
    plan public.plan;
    plan_anterior public.plan;
    cg public.configuracion_general;
    hpc public.historico_plan_contrato;
    cuota public.cuota;
    id_hpc INTEGER;
    id_contrato INTEGER;
    id_plan INTEGER;
    id_plan_anterior INTEGER;
    nc double precision;
    pms INTEGER;
    observacion character varying;
    fecha_inicio date;
    fic date;
    dscto_recargo NUMERIC;
    nueva_cuota NUMERIC;
    valor_nueva_incripcion NUMERIC;
    tasa_cambio_contrato NUMERIC;
    total_pagado_primer_plan NUMERIC;
    abono_capital NUMERIC;
    iva NUMERIC;
    tasa_administrativa NUMERIC;
    impuesto NUMERIC;
    numero_cuotas_pasar INTEGER;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_monto_cobrado NUMERIC;
    id_historico INTEGER;
    capital1 NUMERIC;
    capital2 NUMERIC;
    saldo_pagado_tasa NUMERIC;
    tasa_administrativa1 NUMERIC;
    tasa_administrativa2 NUMERIC;
    diferencia NUMERIC;
    valor_absoluto NUMERIC;
    valor_pagado_cuota NUMERIC;
    valor_recargo NUMERIC;
    fecha_fin DATE;
    abono_restante NUMERIC;
    adicional_inscripcion NUMERIC;
    primera_cuota_pagada CHAR VARYING;
    tasa_administrativa_restante NUMERIC;
    num_cuotas_abajo_hacia_arriba INTEGER;
    BEGIN

    id_hpc := (jsonpar::jsonb->>'idHistoricoPlanContrato')::INTEGER;
    id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
    id_plan := (jsonpar::jsonb->>'idPlan')::INTEGER;
    id_plan_anterior := (jsonpar::jsonb->>'idPlanAnterior')::INTEGER;
    nc := (jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
    pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
    observacion := (jsonpar::jsonb->>'observacion')::VARCHAR;
    fecha_inicio := (jsonpar::jsonb->>'fechaInicio')::DATE;
    fic := (jsonpar::jsonb->>'fechaInicioCobro')::DATE;
    dscto_recargo := (jsonpar::jsonb->>'dsctoRecargo')::NUMERIC;
    nueva_cuota := (jsonpar::jsonb->>'cuota')::NUMERIC;
    adicional_inscripcion:=0;
    cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
    plan := (SELECT pl FROM plan pl WHERE id=id_plan);
    plan_anterior := (SELECT pl FROM plan pl WHERE id=id_plan_anterior);
    contrato := (SELECT co FROM contrato co WHERE id=id_contrato);
    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE id=id_hpc);
    cuota := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc.id LIMIT 1);

    -- verificar que el contrato este en estado registrado o en proceso
    IF contrato.estado <> 'REG' AND contrato.estado <> 'EPR' THEN
        RETURN '{"correcto":false, "message":"El contrato no se encuentra en estado registrado o en proceso"}';
    END IF;

    -- verificar que el contrato anterior tengan la primera cuota pagada
    primera_cuota_pagada := (SELECT "estaPagado" FROM cuota WHERE "idHistoricoPlanContrato"=hpc.id AND "numeroCuota"=1);
    IF (primera_cuota_pagada = 'N') THEN
        RETURN '{"correcto":false, "message":"El contrato anterior no tiene cuotas pagadas"}';
    END IF;

    -- calcular fecha fin de contrato
    fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

    -- se desactiva y se cambia el estado de los contratos
    UPDATE contrato SET "sisHabilitado"='I', estado='CBM' WHERE id = id_contrato;

    -- calcular nueva cuota
    abono_capital := plan.precio/pms;
    iva := (cg."ivaPorcentaje"/100);
    tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
    impuesto := tasa_administrativa * iva;

    -- crear nuevo contrato
    INSERT INTO contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
        "fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
        "fechaIniciaCobro", estado, "plazoMesSeleccionado", version, "cuotaActual",
        "tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado","precioPlanSeleccionado",
        "nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo",
        "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
    ) VALUES (
        now(), now(), 'A', nc, fecha_inicio, fecha_fin, 0, 0,
        observacion, fic, 'REG', pms, (contrato.version + 1), nueva_cuota,
        contrato."tipoDocumentoIdentidad", contrato."documentoIdentidad", plan.modelo, plan.precio,
        contrato."nombresCliente", contrato."apellidosCliente", contrato."nombreGrupo", dscto_recargo,
        contrato."idClienteEnGrupo", contrato."idVendedor", 0 , 0
    ) RETURNING id INTO id_contrato;

    -- comprobar si la inscripcion del plan actual es mayor que la del plan anterior
    if (plan.precio > plan_anterior.precio) then
    adicional_inscripcion := plan.inscripcion - plan_anterior.inscripcion;
    end if;

    -- Valor nueva incripcion y total cobro inscripcion
    tasa_cambio_contrato := cg."tasaCambioContrato"/100;
    valor_nueva_incripcion := (plan.precio * tasa_cambio_contrato) + adicional_inscripcion;

    -- Total Pagado primer plan
    total_pagado_primer_plan := hpc."totalMontoCobrado" - cuota."valorCuota";

    -- calcular cuotas a pasar
    numero_cuotas_pasar := (SELECT floor(total_pagado_primer_plan / nueva_cuota));

    -- generar tabla de cuotas
    valor_pagado_cuota := total_pagado_primer_plan - (numero_cuotas_pasar * nueva_cuota);
    total_monto_cobrado := (nueva_cuota * numero_cuotas_pasar) + valor_pagado_cuota;

    abono_restante := 0;
    tasa_administrativa_restante := 0;
    abonos_capital_actual := numero_cuotas_pasar * abono_capital;
    total_tasa_administrativa_cobrada := numero_cuotas_pasar * tasa_administrativa;
    IF(valor_pagado_cuota > 0) THEN
        tasa_administrativa_restante := tasa_administrativa + impuesto;
        abono_restante := valor_pagado_cuota - tasa_administrativa_restante;
        abonos_capital_actual := abonos_capital_actual + abono_restante;
        total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + tasa_administrativa;
    END IF;

    saldo_capital := plan.precio - abonos_capital_actual;

    -- crear nuevo contrato
    if(dscto_recargo <> 0) then
        dscto_recargo := cg."cuotaAdministrativa" * (desc_recargo/100);
        valor_recargo := cd."cuotaAdministrativa" - dscto_recargo;
    else
        valor_recargo := cg."cuotaAdministrativa";
    end if;

    -- Insertar nuevo historico plan contrato
    INSERT INTO historico_plan_contrato (
        "sisActualizado", "sisCreado",
        "sisHabilitado", "totalInscripcionPlan",
        "valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
        "capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
        "valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
        "totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
        "totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba",
        "valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
    ) VALUES (
        now(), now(),
        'A', plan.inscripcion,
        0, valor_nueva_incripcion, plan.precio,
        0, abonos_capital_actual, saldo_capital,
        tasa_administrativa, total_tasa_administrativa_cobrada, numero_cuotas_pasar,
        0, 0, total_monto_cobrado, 0, 0,
        valor_recargo, id_contrato, id_plan, numero_cuotas_pasar,
        0, 'N', 0, 0
    ) RETURNING id INTO id_historico;

    -- comprobaciones del calculo correcto
    capital1 := cuota."abonoCapital" * (hpc."totalCuotasCobradas" - 1);
    capital2 := abonos_capital_actual;
    saldo_pagado_tasa := (SELECT round((SELECT abs(capital1 - capital2)), 3));
    tasa_administrativa1 := (cuota."valorTasaAdministrativa" * (hpc."totalCuotasCobradas" - 1)) + (cuota."valorImpuesto" * (hpc."totalCuotasCobradas" - 1));
    tasa_administrativa2 := (tasa_administrativa * numero_cuotas_pasar) + (impuesto * numero_cuotas_pasar) + tasa_administrativa_restante;
    diferencia := tasa_administrativa1 - tasa_administrativa2;
    valor_absoluto := (SELECT round((SELECT abs(diferencia)), 3));

    if (saldo_pagado_tasa <> valor_absoluto) then
        RETURN '{"correcto":false, "message":"Error en los calculo de comprobacion de la diferencia de la tase administrativa y el capital"}';
    end if;

    PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, valor_pagado_cuota,
        tasa_administrativa, impuesto, abono_capital,
        'N', pms, numero_cuotas_pasar, id_historico);
    
    PERFORM public.registrar_id_contratos(contrato.id);

    RETURN '{"correcto":true, "message":"Se actualizo el monto del contrato correctamente"}';
    ROLLBACK;
    END;
    $$ LANGUAGE 'plpgsql';

------- ACTUALIZAR PLAN CONTRATO -------

    create or replace function actualizar_plan_contrato(jsonpar CHAR VARYING) returns CHARACTER VARYING as $$
    Declare
    contrato public.contrato;
    plan public.plan;
    plan_anterior public.plan;
    cg public.configuracion_general;
    hpc public.historico_plan_contrato;
    cuota public.cuota;
    valor_nueva_incripcion NUMERIC;
    tasa_cambio_contrato NUMERIC;
    total_pagado_primer_plan NUMERIC;
    abono_capital NUMERIC;
    iva NUMERIC;
    tasa_administrativa NUMERIC;
    impuesto NUMERIC;
    numero_cuotas_pasar INTEGER;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_monto_cobrado NUMERIC;
    id_historico INTEGER;
    capital1 NUMERIC;
    capital2 NUMERIC;
    saldo_pagado_tasa NUMERIC;
    tasa_administrativa1 NUMERIC;
    tasa_administrativa2 NUMERIC;
    diferencia NUMERIC;
    valor_absoluto NUMERIC;
    valor_pagado_cuota NUMERIC;
    valor_recargo NUMERIC;
    fecha_fin DATE;
    abono_restante NUMERIC;
    adicional_inscripcion NUMERIC;
    primera_cuota_pagada CHAR VARYING;
    tasa_administrativa_restante NUMERIC;
    num_cuotas_abajo_hacia_arriba INTEGER;
    id_hpc INTEGER;
    id_contrato INTEGER;
    id_plan INTEGER;
    id_plan_anterior INTEGER;
    nc DOUBLE PRECISION;
    pms INTEGER;
    observacion CHAR VARYING;
    fecha_inicio DATE;
    fic DATE;
    dscto_recargo NUMERIC;
    nueva_cuota NUMERIC;
    BEGIN

    id_hpc := (jsonpar::jsonb->>'idHistoricoPlanContrato')::INTEGER;
    id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
    id_plan := (jsonpar::jsonb->>'idPlan')::INTEGER;
    id_plan_anterior := (jsonpar::jsonb->>'idPlanAnterior')::INTEGER;
    nc := (jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
    pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
    observacion := (jsonpar::jsonb->>'observacion')::VARCHAR;
    fecha_inicio := (jsonpar::jsonb->>'fechaInicio')::DATE;
    fic := (jsonpar::jsonb->>'fechaInicioCobro')::DATE;
    dscto_recargo := (jsonpar::jsonb->>'dsctoRecargo')::NUMERIC;
    nueva_cuota := (jsonpar::jsonb->>'cuota')::NUMERIC;

    adicional_inscripcion:=0;
    cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
    plan := (SELECT pl FROM plan pl WHERE id=id_plan);
    plan_anterior := (SELECT pl FROM plan pl WHERE id=id_plan_anterior);
    contrato := (SELECT co FROM contrato co WHERE id=id_contrato);
    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE id=id_hpc);
    cuota := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc.id LIMIT 1);

    -- verificar que el contrato este en estado registrado o en proceso
    IF contrato.estado <> 'REG' AND contrato.estado <> 'EPR' THEN
        RETURN '{"correcto":false, "message":"El contrato no se encuentra en estado registrado o en proceso"}';
    END IF;

    -- verificar que el contrato anterior tengan la primera cuota pagada
    primera_cuota_pagada := (SELECT "estaPagado" FROM cuota WHERE "idHistoricoPlanContrato"=hpc.id AND "numeroCuota"=1);
    IF (primera_cuota_pagada = 'N') THEN
        RETURN '{"correcto":false, "message":"El contrato anterior no tiene cuotas pagadas"}';
    END IF;

    -- calcular fecha fin de contrato
    fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

    -- se desactiva y se cambia el estado de los contratos
    UPDATE contrato SET "sisHabilitado"='I', estado='CPN' WHERE id = id_contrato;

    -- calcular nueva cuota
    abono_capital := plan.precio/pms;
    iva := (cg."ivaPorcentaje"/100);
    tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
    impuesto := tasa_administrativa * iva;

    -- crear nuevo contrato
    INSERT INTO contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
        "fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
        "fechaIniciaCobro", "estado", "plazoMesSeleccionado", version, "cuotaActual",
        "tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
        "nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo", "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
    ) VALUES (
        now(), now(), 'A', nc,
        fecha_inicio, fecha_fin, 0, 0, observacion,
        fic, 'REG', pms, (contrato.version + 1), nueva_cuota,
        contrato."tipoDocumentoIdentidad", contrato."documentoIdentidad", plan.modelo, plan.precio,
        contrato."nombresCliente", contrato."apellidosCliente", contrato."nombreGrupo", dscto_recargo, contrato."idClienteEnGrupo", contrato."idVendedor", 0 , 0
    ) RETURNING id INTO id_contrato;

    -- comprobar si la inscripcion del plan actual es mayor que la del plan anterior
    if (plan.precio > plan_anterior.precio) then
    adicional_inscripcion := plan.inscripcion - plan_anterior.inscripcion;
    end if;

    -- Valor nueva incripcion y total cobro inscripcion
    tasa_cambio_contrato := cg."tasaCambioContrato"/100;
    valor_nueva_incripcion := (plan.precio * tasa_cambio_contrato) + adicional_inscripcion;

    -- Total Pagado primer plan
    RAISE NOTICE '%', (cuota."valorCuota");
    total_pagado_primer_plan := hpc."totalMontoCobrado" - cuota."valorCuota";

    -- calcular cuotas a pasar
    numero_cuotas_pasar := (SELECT floor(total_pagado_primer_plan / nueva_cuota));

    -- generar tabla de cuotas
    valor_pagado_cuota := total_pagado_primer_plan - (numero_cuotas_pasar * nueva_cuota);
    total_monto_cobrado := (nueva_cuota * numero_cuotas_pasar) + valor_pagado_cuota;

    abono_restante := 0;
    tasa_administrativa_restante := 0;
    abonos_capital_actual := numero_cuotas_pasar * abono_capital;
    total_tasa_administrativa_cobrada := numero_cuotas_pasar * tasa_administrativa;
    IF(valor_pagado_cuota > 0) THEN
        tasa_administrativa_restante := tasa_administrativa + impuesto;
        abono_restante := valor_pagado_cuota - tasa_administrativa_restante;
        abonos_capital_actual := abonos_capital_actual + abono_restante;
        total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + tasa_administrativa;
    END IF;

    saldo_capital := plan.precio - abonos_capital_actual;

    -- crear nuevo contrato
    if(dscto_recargo <> 0) then
        dscto_recargo := cg."cuotaAdministrativa" * (desc_recargo/100);
        valor_recargo := cg."cuotaAdministrativa" - dscto_recargo;
    else
        valor_recargo := cg."cuotaAdministrativa";
    end if;

    -- Insertar nuevo historico plan contrato
    INSERT INTO historico_plan_contrato (
        "sisActualizado", "sisCreado",
        "sisHabilitado", "totalInscripcionPlan",
        "valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
        "capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
        "valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
        "totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
        "totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba",
        "valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
    ) VALUES (
        now(), now(),
        'A', plan.inscripcion,
        0, valor_nueva_incripcion, plan.precio,
        0, abonos_capital_actual, saldo_capital,
        tasa_administrativa, total_tasa_administrativa_cobrada, numero_cuotas_pasar,
        0, 0, total_monto_cobrado, 0, 0,
        valor_recargo, id_contrato, id_plan, numero_cuotas_pasar,
        0, 'N', 0, 0
    ) RETURNING id INTO id_historico;

    -- comprobaciones del calculo correcto
    capital1 := cuota."abonoCapital" * (hpc."totalCuotasCobradas" - 1);
    capital2 := abonos_capital_actual;
    saldo_pagado_tasa := (SELECT round((SELECT abs(capital1 - capital2)), 3));
    tasa_administrativa1 := (cuota."valorTasaAdministrativa" * (hpc."totalCuotasCobradas" - 1)) + (cuota."valorImpuesto" * (hpc."totalCuotasCobradas" - 1));
    tasa_administrativa2 := (tasa_administrativa * numero_cuotas_pasar) + (impuesto * numero_cuotas_pasar) + tasa_administrativa_restante;
    diferencia := tasa_administrativa1 - tasa_administrativa2;
    valor_absoluto := (SELECT round((SELECT abs(diferencia)), 3));

    if (saldo_pagado_tasa <> valor_absoluto) then
        RETURN '{"correcto":false, "message":"Error en los calculo de comprobacion de la diferencia de la tase administrativa y el capital"}';
    end if;

    PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, valor_pagado_cuota,
        tasa_administrativa, impuesto, abono_capital,
        'N', pms, numero_cuotas_pasar, id_historico);
    
    PERFORM public.registrar_id_contratos(contrato.id);

    RETURN '{"correcto":true, "message":"Se actualizo correctamente el plan del contrato"}';
    ROLLBACK;
    end;
    $$ language 'plpgsql';

------- PLAZO CONTRATO -------

    CREATE OR REPLACE FUNCTION actualizar_plazo_contrato(jsonpar CHAR VARYING) RETURNS CHARACTER VARYING AS $$
    DECLARE
    correcto BOOLEAN;
    contrato public.contrato;
    plan public.plan;
    cg public.configuracion_general;
    hpc public.historico_plan_contrato;
    cuota public.cuota;
    valor_nueva_incripcion NUMERIC;
    tasa_cambio_contrato NUMERIC;
    total_pagado_primer_plan NUMERIC;
    abono_capital NUMERIC;
    iva NUMERIC;
    tasa_administrativa NUMERIC;
    impuesto NUMERIC;
    numero_cuotas_pasar INTEGER;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_monto_cobrado NUMERIC;
    id_historico INTEGER;
    capital1 NUMERIC;
    capital2 NUMERIC;
    saldo_pagado_tasa NUMERIC;
    tasa_administrativa1 NUMERIC;
    tasa_administrativa2 NUMERIC;
    diferencia NUMERIC;
    valor_absoluto NUMERIC;
    valor_pagado_cuota NUMERIC;
    valor_recargo NUMERIC;
    fecha_fin DATE;
    abono_restante NUMERIC;
    primera_cuota_pagada CHAR VARYING;
    tasa_administrativa_restante NUMERIC;
    num_cuotas_abajo_hacia_arriba INTEGER;
    id_hpc INTEGER;
    id_contrato INTEGER;
    id_plan INTEGER;
    nc DOUBLE PRECISION;
    pms INTEGER;
    observacion CHARACTER VARYING;
    fecha_inicio date;
    fic date;
    dscto_recargo NUMERIC;
    nueva_cuota NUMERIC;
    BEGIN

    id_hpc := (jsonpar::jsonb->>'idHistoricoPlanContrato')::INTEGER;
    id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
    id_plan := (jsonpar::jsonb->>'idPlan')::INTEGER;
    nc := (jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
    pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
    observacion := (jsonpar::jsonb->>'observacion')::CHARACTER VARYING;
    fecha_inicio := (jsonpar::jsonb->>'fechaInicio')::DATE;
    fic := (jsonpar::jsonb->>'fechaInicioCobro')::DATE;
    dscto_recargo := (jsonpar::jsonb->>'dsctoRecargo')::NUMERIC;
    nueva_cuota := (jsonpar::jsonb->>'cuota')::NUMERIC;

    cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
    plan := (SELECT pl FROM plan pl WHERE id=id_plan);
    contrato := (SELECT co FROM contrato co WHERE id=id_contrato);
    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE id=id_hpc);
    cuota := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc.id LIMIT 1);

    -- verificar que el contrato este en estado registrado o en proceso
    IF contrato.estado <> 'REG' AND contrato.estado <> 'EPR' THEN
        RETURN '{"correcto":false, "message":"El contrato no se encuentra en estado registrado o en proceso"}';
    END IF;

    -- verificar que el contrato anterior tengan la primera cuota pagada
    primera_cuota_pagada := (SELECT "estaPagado" FROM cuota WHERE "idHistoricoPlanContrato"=hpc.id AND "numeroCuota"=1);
    IF (primera_cuota_pagada = 'N') THEN
        RETURN '{"correcto":false, "message":"El contrato anterior no tiene cuotas pagadas"}';
    END IF;

    -- calcular fecha fin de contrato
    fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

    -- se desactiva y se cambia el estado de los contratos
    UPDATE contrato SET "sisHabilitado"='I', estado='CPZ' WHERE id = id_contrato;

    -- calcular nueva cuota
    abono_capital := plan.precio/pms;
    iva := (cg."ivaPorcentaje"/100);
    tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
    impuesto := tasa_administrativa * iva;

    -- crear nuevo contrato
    INSERT INTO contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
        "fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
        "fechaIniciaCobro", "estado", "plazoMesSeleccionado", version, "cuotaActual",
        "tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
        "nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo", "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
    ) VALUES (
        now(), now(), 'A', nc, fecha_inicio, fecha_fin, 0, 0,
        observacion, fic, 'REG', pms, (contrato.version + 1), nueva_cuota,
        contrato."tipoDocumentoIdentidad", contrato."documentoIdentidad", plan.modelo, plan.precio,
        contrato."nombresCliente", contrato."apellidosCliente", contrato."nombreGrupo", dscto_recargo, contrato."idClienteEnGrupo", contrato."idVendedor", 0 , 0
    ) RETURNING id INTO id_contrato;

    -- Valor nueva incripcion y total cobro inscripcion
    tasa_cambio_contrato := cg."tasaCambioContrato"/100;
    valor_nueva_incripcion := (plan.precio * tasa_cambio_contrato);

    -- Total Pagado primer plan
    total_pagado_primer_plan := hpc."totalMontoCobrado" - cuota."valorCuota";

    -- calcular cuotas a pasar
    numero_cuotas_pasar := (SELECT floor(total_pagado_primer_plan / nueva_cuota));

    -- generar tabla de cuotas
    valor_pagado_cuota := total_pagado_primer_plan - (numero_cuotas_pasar * nueva_cuota);
    total_monto_cobrado := (nueva_cuota * numero_cuotas_pasar) + valor_pagado_cuota;

    abono_restante := 0;
    tasa_administrativa_restante := 0;
    abonos_capital_actual := numero_cuotas_pasar * abono_capital;
    total_tasa_administrativa_cobrada := numero_cuotas_pasar * tasa_administrativa;
    IF(valor_pagado_cuota > 0) THEN
        tasa_administrativa_restante := tasa_administrativa + impuesto;
        abono_restante := valor_pagado_cuota - tasa_administrativa_restante;
        abonos_capital_actual := abonos_capital_actual + abono_restante;
        total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + tasa_administrativa;
    END IF;

    saldo_capital := plan.precio - abonos_capital_actual;

    -- crear nuevo contrato
    if(dscto_recargo <> 0) then
        dscto_recargo := cg."cuotaAdministrativa" * (desc_recargo/100);
        valor_recargo := cg."cuotaAdministrativa" - dscto_recargo;
    else
        valor_recargo := cg."cuotaAdministrativa";
    end if;

    -- Insertar nuevo historico plan contrato
    INSERT INTO historico_plan_contrato (
        "sisActualizado", "sisCreado",
        "sisHabilitado", "totalInscripcionPlan",
        "valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
        "capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
        "valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
        "totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
        "totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba",
        "valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
    ) VALUES (
        now(), now(),
        'A', plan.inscripcion,
        0, valor_nueva_incripcion, plan.precio,
        0, abonos_capital_actual, saldo_capital,
        tasa_administrativa, total_tasa_administrativa_cobrada, numero_cuotas_pasar,
        0, 0, total_monto_cobrado, 0, 0,
        valor_recargo, id_contrato, id_plan, numero_cuotas_pasar,
        0, 'N', 0, 0
    ) RETURNING id INTO id_historico;

    -- comprobar abono restante
    capital1 := cuota."abonoCapital" * (hpc."totalCuotasCobradas" - 1);
    capital2 := abonos_capital_actual;
    saldo_pagado_tasa := (SELECT round((SELECT abs(capital1 - capital2)), 3));
    tasa_administrativa1 := (cuota."valorTasaAdministrativa" * (hpc."totalCuotasCobradas" - 1)) + (cuota."valorImpuesto" * (hpc."totalCuotasCobradas" - 1));
    tasa_administrativa2 := (tasa_administrativa * numero_cuotas_pasar) + (impuesto * numero_cuotas_pasar) + tasa_administrativa_restante;
    diferencia := tasa_administrativa1 - tasa_administrativa2;
    valor_absoluto := (SELECT round((SELECT abs(diferencia)), 3));

    if (saldo_pagado_tasa <> valor_absoluto) then
        RETURN '{"correcto":false, "message":"Error en los calculo de comprobacion de la diferencia de la tase administrativa y el capital"}';
    end if;

    PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, valor_pagado_cuota,
        tasa_administrativa, impuesto, abono_capital,
        'N', pms, numero_cuotas_pasar, id_historico);
    
    PERFORM public.registrar_id_contratos(contrato.id);

    RETURN '{"correcto":true, "message":"Se actualizo correctamente "}';
    ROLLBACK;
    END;
    $$ LANGUAGE 'plpgsql';

--------- CREAR CONTRATO ----------

    CREATE OR replace FUNCTION crear_contrato(jsonpar CHAR VARYING) returns CHARACTER VARYING as $$
    DECLARE
    usuario public.usuario;
    plan public.plan;
    gp public.grupo;
    cg public.configuracion_general;
    id_cliente INTEGER;
    id_vendedor INTEGER;
    id_plan INTEGER;
    nc DOUBLE PRECISION;
    fecha_inicio DATE;
    dscto_ins NUMERIC;
    dscto_pc NUMERIC;
    observacion CHARACTER VARYING;
    fic DATE;
    pms INTEGER;
    nueva_cuota NUMERIC;
    id_grupo INTEGER;
    ng INTEGER;
    id_cg INTEGER;
    existe_grupo INTEGER;
    grupo_lleno INTEGER;
    id_contrato_anterior INTEGER;
    id_contrato INTEGER;
    id_hpc INTEGER;
    fecha_fin DATE;
    abono_capital NUMERIC;
    iva NUMERIC;
    tasa_administrativa NUMERIC;
    impuesto NUMERIC;
    total_cobro_inscripcion NUMERIC;
    existe_cliente_en_grupo INTEGER;
    BEGIN

    id_contrato_anterior := (jsonpar::jsonb->>'idContrato')::INTEGER;
    id_cliente :=(jsonpar::jsonb->>'idCliente')::INTEGER;
    id_vendedor :=(jsonpar::jsonb->>'idVendedor')::INTEGER;
    id_plan :=(jsonpar::jsonb->>'idPlan')::INTEGER;
    nc :=(jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
    fecha_inicio :=(jsonpar::jsonb->>'fechaInicio')::DATE;
    dscto_ins := (jsonpar::jsonb->>'dsctoInscripcion')::NUMERIC;
    dscto_pc :=(jsonpar::jsonb->> 'dsctoPrimeraCuota')::NUMERIC;
    observacion :=(jsonpar::jsonb->>'observacion')::CHARACTER VARYING;
    fic :=(jsonpar::jsonb->>'fechaInicioCobro')::DATE;
    pms :=(jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
    nueva_cuota :=(jsonpar::jsonb->>'cuota')::NUMERIC;


    usuario := (SELECT u FROM cliente c INNER JOIN usuario u ON c."idUsuario"=u.id WHERE c.id=id_cliente);
    plan := (SELECT pl FROM plan pl WHERE id=id_plan);
    cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);

    -- verificar que exista un grupo para el contrato del cliente y que aun no este lleno
    existe_grupo := (SELECT count(*) FROM grupo WHERE "totalContratosUsados" < 144);
    IF(existe_grupo = 0) THEN
        INSERT INTO grupo ("sisActualizado", "sisCreado", "sisHabilitado", "nombreGrupo", "sumatoriaMontoMeta", "totalContratosPermitidos", "totalContratosUsados")
        values (now(), now(), 'A', 1, 0, 144, 1) RETURNING id, "nombreGrupo" INTO id_grupo, ng;
    ELSE
        -- verificar si en el grupo ya existe un contrato para el cliente
        gp := (SELECT g FROM grupo g WHERE g."totalContratosUsados" < 144 ORDER BY g."sisCreado" ASC LIMIT 1);
        existe_cliente_en_grupo := (SELECT count(*) FROM cliente_en_grupo ceg WHERE ceg."idCliente" = id_cliente AND ceg."idGrupo" = gp.id);

        IF (existe_cliente_en_grupo = 0) THEN
            UPDATE grupo SET "sisActualizado"=now(), "totalContratosUsados"=(gp."totalContratosUsados" + 1) WHERE id=gp.id;
            id_grupo := gp.id;
            ng := gp."nombreGrupo";
        ELSE
            INSERT INTO grupo ("sisActualizado", "sisCreado", "sisHabilitado", "nombreGrupo", "sumatoriaMontoMeta", "totalContratosPermitidos", "totalContratosUsados")
            values (now(), now(), 'A', (gp."nombreGrupo" + 1), 0, 144, 1) RETURNING id, "nombreGrupo" INTO id_grupo, ng;
        END IF;
    END IF;

    -- crear cliente en grupo
    INSERT INTO cliente_en_grupo ("sisActualizado", "sisCreado", "sisHabilitado", "idCliente", "idGrupo") values (now(), now(), 'A', id_cliente, id_grupo) RETURNING id INTO id_cg;

    -- calcular fecha fin de contrato
    fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

    -- calcular nueva_cuota
    abono_capital := plan.precio/pms;
    iva := (cg."ivaPorcentaje"/100);
    tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
    impuesto := tasa_administrativa * iva;

    -- crear nuevo contrato
    INSERT INTO contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
        "fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
        "fechaIniciaCobro", "estado", "plazoMesSeleccionado", version, "cuotaActual",
        "tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
        "nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo", "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
    ) VALUES (
        now(), now(), 'A', nc, fecha_inicio, fecha_fin, dscto_ins, dscto_pc,
        observacion, fic, 'REG', pms, 1, nueva_cuota,
        usuario."tipoDocumentoIdentidad", usuario."documentoIdentidad", plan.modelo, plan.precio,
        usuario.nombres, usuario.apellidos, ng, 0, id_cg, id_vendedor, 0 , 0
    ) RETURNING id INTO id_contrato;

    -- aplicar descuento de inscripcion y descuento de primera nueva_cuota
    total_cobro_inscripcion := plan.inscripcion - (plan.inscripcion * (dscto_ins/100));

    -- Insertar nuevo historico plan contrato
    INSERT INTO historico_plan_contrato (
        "sisActualizado", "sisCreado", "sisHabilitado","totalInscripcionPlan",
        "valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
        "capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
        "valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
        "totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
        "totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba", "valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
    ) VALUES (
        now(),now(),1, plan.inscripcion,
        dscto_ins, total_cobro_inscripcion, plan.precio,
        0, 0, plan.precio,
        tasa_administrativa, 0, 0,
        0, 0, 0, dscto_pc,
        0,0, id_contrato, id_plan,
        0, 0, 'N', 0, 0
    ) RETURNING id INTO id_hpc;

    PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, 0,
        tasa_administrativa, impuesto, abono_capital,
        'N', pms, 0, id_hpc);

    -- actualizar los id de los contratos anteriores
    if(id_contrato_anterior is not null) then
        PERFORM public.registrar_id_contratos(id_contrato_anterior);
    end if;

    RETURN '{"correcto":true, "message":"Se creo el contrato correctamente"}';
    ROLLBACK;
    END;
    $$ LANGUAGE 'plpgsql';

---------- DESISTIR CONTRATO ----------

    create or replace function desistir_contrato(jsonpar CHAR VARYING) returns CHARACTER VARYING as $$
    Declare
    contrato public.contrato;
    plan public.plan;
    plan_anterior public.plan;
    cg public.configuracion_general;
    hpc public.historico_plan_contrato;
    cuota public.cuota;
    valor_nueva_incripcion NUMERIC;
    tasa_cambio_contrato NUMERIC;
    total_pagado_primer_plan NUMERIC;
    abono_capital NUMERIC;
    iva NUMERIC;
    tasa_administrativa NUMERIC;
    impuesto NUMERIC;
    numero_cuotas_pasar INTEGER;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_monto_cobrado NUMERIC;
    id_historico INTEGER;
    capital1 NUMERIC;
    capital2 NUMERIC;
    saldo_pagado_tasa NUMERIC;
    tasa_administrativa1 NUMERIC;
    tasa_administrativa2 NUMERIC;
    diferencia NUMERIC;
    valor_absoluto NUMERIC;
    valor_pagado_cuota NUMERIC;
    fecha_fin DATE;
    abono_restante NUMERIC;
    primera_cuota_pagada CHAR VARYING;
    tasa_administrativa_restante NUMERIC;
    num_cuotas_abajo_hacia_arriba INTEGER;
    -------- json values -------
    id_hpc INTEGER;
    id_contrato INTEGER;
    id_plan INTEGER;
    id_plan_anterior INTEGER;
    id_vendedor INTEGER;
    nc DOUBLE PRECISION;
    pms INTEGER;
    observacion CHARACTER VARYING;
    fecha_inicio DATE;
    fic DATE;
    porcentaje_tasa BOOLEAN;
    nueva_cuota NUMERIC;
    BEGIN

    id_hpc := (jsonpar::jsonb->>'idHistoricoPlanContrato')::INTEGER;
    id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
    id_plan := (jsonpar::jsonb->>'idPlan')::INTEGER;
    id_plan_anterior := (jsonpar::jsonb->>'idPlanAnterior')::INTEGER;
    id_vendedor := (jsonpar::jsonb->>'idVendedor')::INTEGER;
    nc := (jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
    pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
    observacion := (jsonpar::jsonb->>'observacion')::VARCHAR;
    fecha_inicio := (jsonpar::jsonb->>'fechaInicio')::DATE;
    fic := (jsonpar::jsonb->>'fechaInicioCobro')::DATE;
    porcentaje_tasa := (jsonpar::jsonb->>'porcentajeTasa')::BOOLEAN;
    nueva_cuota := (jsonpar::jsonb->>'cuota')::NUMERIC;

    cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
    plan := (SELECT pl FROM plan pl WHERE id=id_plan);
    plan_anterior := (SELECT pl FROM plan pl WHERE id=id_plan_anterior);
    contrato := (SELECT co FROM contrato co WHERE id=id_contrato);
    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE id=id_hpc);
    cuota := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc.id LIMIT 1);

    -- verificar que el contrato este en estado registrado o en proceso
    IF contrato.estado <> 'REG' AND contrato.estado <> 'EPR' THEN
        RETURN '{"correcto":false, "message":"El contrato no se encuentra en estado registrado o en proceso"}';
    END IF;

    -- verificar que el contrato anterior tengan la primera cuota pagada
    primera_cuota_pagada := (SELECT "estaPagado" FROM cuota WHERE "idHistoricoPlanContrato"=hpc.id AND "numeroCuota"=1);
    IF (primera_cuota_pagada = 'N') THEN
        RETURN '{"correcto":false, "message":"El contrato anterior no tiene cuotas pagadas"}';
    END IF;

    -- calcular fecha fin de contrato
    fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

    -- se desactiva y se cambia el estado de los contratos
    UPDATE contrato SET "sisHabilitado"='I', estado='DES' WHERE id = id_contrato;

    -- calcular nueva cuota
    abono_capital := plan.precio/pms;
    iva := (cg."ivaPorcentaje"/100);
    tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
    impuesto := tasa_administrativa * iva;

    -- crear nuevo contrato
    INSERT INTO contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
        "fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
        "fechaIniciaCobro", "estado", "plazoMesSeleccionado", version, "cuotaActual",
        "tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
        "nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo", "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
    ) VALUES (
        now(), now(), 'A', nc, fecha_inicio, fecha_fin, 0, 0,
        observacion, fic, 'REG', pms, (contrato.version + 1), nueva_cuota,
        contrato."tipoDocumentoIdentidad", contrato."documentoIdentidad", plan.modelo, plan.precio,
        contrato."nombresCliente", contrato."apellidosCliente", contrato."nombreGrupo", 0, contrato."idClienteEnGrupo", id_vendedor, 0 , 0
    ) RETURNING id INTO id_contrato;

    -- Valor nueva incripcion y total cobro inscripcion
    tasa_cambio_contrato := cg."tasaCambioContrato"/100;
    valor_nueva_incripcion := (plan.precio * tasa_cambio_contrato);
    IF(porcentaje_tasa = true) THEN
        valor_nueva_incripcion := plan.inscripcion;
    END IF;

    -- Total Pagado primer plan
    total_pagado_primer_plan := hpc."totalMontoCobrado" - cuota."valorCuota";

    -- calcular cuotas a pasar
    numero_cuotas_pasar := (SELECT floor(total_pagado_primer_plan / nueva_cuota));

    -- generar tabla de cuotas
    valor_pagado_cuota := total_pagado_primer_plan - (numero_cuotas_pasar * nueva_cuota);
    total_monto_cobrado := (nueva_cuota * numero_cuotas_pasar) + valor_pagado_cuota;

    abono_restante := 0;
    tasa_administrativa_restante := 0;
    abonos_capital_actual := numero_cuotas_pasar * abono_capital;
    total_tasa_administrativa_cobrada := numero_cuotas_pasar * tasa_administrativa;
    IF(valor_pagado_cuota > 0) THEN
        tasa_administrativa_restante := tasa_administrativa + impuesto;
        abono_restante := valor_pagado_cuota - tasa_administrativa_restante;
        abonos_capital_actual := abonos_capital_actual + abono_restante;
        total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + tasa_administrativa;
    END IF;

    saldo_capital := plan.precio - abonos_capital_actual;

    -- Insertar nuevo historico plan contrato
    INSERT INTO historico_plan_contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "totalInscripcionPlan",
        "valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
        "capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
        "valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
        "totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
        "totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba",
        "valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
    ) VALUES (
        now(), now(), 'A', plan.inscripcion,
        0, valor_nueva_incripcion, plan.precio,
        0, abonos_capital_actual, saldo_capital,
        tasa_administrativa, total_tasa_administrativa_cobrada, numero_cuotas_pasar,
        0, 0, total_monto_cobrado, 0, 0,
        0, id_contrato, id_plan, numero_cuotas_pasar,
        0, 'N', 0, 0
    ) RETURNING id INTO id_historico;

    -- comprobaciones del calculo correcto
    capital1 := cuota."abonoCapital" * (hpc."totalCuotasCobradas" - 1);
    capital2 := abonos_capital_actual;
    saldo_pagado_tasa := (SELECT round((SELECT abs(capital1 - capital2)), 3));
    tasa_administrativa1 := (cuota."valorTasaAdministrativa" * (hpc."totalCuotasCobradas" - 1)) + (cuota."valorImpuesto" * (hpc."totalCuotasCobradas" - 1));
    tasa_administrativa2 := (tasa_administrativa * numero_cuotas_pasar) + (impuesto * numero_cuotas_pasar) + tasa_administrativa_restante;
    diferencia := tasa_administrativa1 - tasa_administrativa2;
    valor_absoluto := (SELECT round((SELECT abs(diferencia)), 3));

    if (saldo_pagado_tasa <> valor_absoluto) then
        RETURN '{"correcto":false, "message":"Error en los calculo de comprobacion de la diferencia de la tase administrativa y el capital"}';
    end if;

    PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, valor_pagado_cuota,
        tasa_administrativa, impuesto, abono_capital,
        'N', pms, numero_cuotas_pasar, id_historico);

    PERFORM public.registrar_id_contratos(contrato.id);

    RETURN '{"correcto":true, "message":"Se desistio correctamente del contrato"}';
    ROLLBACK;
    end;
    $$ language 'plpgsql';

----------- REACTIVACION CONTRATO -----------

    create or replace function reactivacion_contrato(jsonpar CHAR VARYING) returns CHARACTER VARYING as $$
    Declare
    contrato public.contrato;
    plan public.plan;
    cg public.configuracion_general;
    hpc public.historico_plan_contrato;
    cuota public.cuota;
    valor_nueva_incripcion NUMERIC;
    tasa_cambio_contrato NUMERIC;
    total_pagado_primer_plan NUMERIC;
    abono_capital NUMERIC;
    iva NUMERIC;
    tasa_administrativa NUMERIC;
    impuesto NUMERIC;
    numero_cuotas_pasar INTEGER;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_monto_cobrado NUMERIC;
    id_historico INTEGER;
    capital1 NUMERIC;
    capital2 NUMERIC;
    saldo_pagado_tasa NUMERIC;
    tasa_administrativa1 NUMERIC;
    tasa_administrativa2 NUMERIC;
    diferencia NUMERIC;
    valor_absoluto NUMERIC;
    valor_pagado_cuota NUMERIC;
    fecha_fin DATE;
    abono_restante NUMERIC;
    numero_cuotas_en_mora INTEGER;
    tasa_administrativa_restante NUMERIC;
    ------- json value ---------
    id_hpc INTEGER;
    id_contrato INTEGER;
    id_plan INTEGER;
    nc DOUBLE PRECISION;
    pms INTEGER;
    observacion CHARACTER VARYING;
    fecha_inicio DATE;
    fic DATE;
    id_vendedor INTEGER;
    porcentaje_tasa BOOLEAN;
    nueva_cuota NUMERIC;
    BEGIN

    id_hpc := (jsonpar::jsonb->>'idHistoricoPlanContrato')::INTEGER;
    id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
    id_plan := (jsonpar::jsonb->>'idPlan')::INTEGER;
    nc := (jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
    pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
    observacion := (jsonpar::jsonb->>'observacion')::VARCHAR;
    fecha_inicio := (jsonpar::jsonb->>'fechaInicio')::DATE;
    fic := (jsonpar::jsonb->>'fechaInicioCobro')::DATE;
    id_vendedor := (jsonpar::jsonb->>'idVendedor')::INTEGER;
    porcentaje_tasa := (jsonpar::jsonb->>'porcentajeTasa')::BOOLEAN;
    nueva_cuota := (jsonpar::jsonb->>'cuota')::NUMERIC;

    cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
    plan := (SELECT pl FROM plan pl WHERE id=id_plan);
    contrato := (SELECT co FROM contrato co WHERE id=id_contrato);
    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE id=id_hpc);
    cuota := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc.id LIMIT 1);

    -- verificar que el contrato este en estado registrado o en proceso
    IF contrato.estado <> 'REG' AND contrato.estado <> 'EPR' THEN
        RETURN '{"correcto":false, "message":"El contrato no se encuentra en estado registrado o en proceso"}';
    END IF;

    -- verificar que el contrato anterior tengan la primera cuota pagada
    numero_cuotas_en_mora := (SELECT count(*) FROM cuota WHERE "idHistoricoPlanContrato"=hpc.id AND "estaMora" = 'S');
    IF (numero_cuotas_en_mora < 1) THEN
        RETURN '{"correcto":false, "message":"El contrato debe tener minimo dos cuotas en mora"}';
    END IF;

    -- calcular fecha fin de contrato
    fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

    -- se desactiva y se cambia el estado de los contratos
    UPDATE contrato SET "sisHabilitado"='I', estado='REA' WHERE id = id_contrato;

    -- calcular nueva cuota
    abono_capital := plan.precio/pms;
    iva := (cg."ivaPorcentaje"/100);
    tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
    impuesto := tasa_administrativa * iva;

    -- crear nuevo contrato
    INSERT INTO contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
        "fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
        "fechaIniciaCobro", estado, "plazoMesSeleccionado", version, "cuotaActual",
        "tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
        "nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo", "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
    ) VALUES (
        now(), now(), 'A', nc, fecha_inicio, fecha_fin, 0, 0,
        observacion, fic, 'REG', pms, (contrato.version + 1), nueva_cuota,
        contrato."tipoDocumentoIdentidad", contrato."documentoIdentidad", plan.modelo, plan.precio,
        contrato."nombresCliente", contrato."apellidosCliente", contrato."nombreGrupo", 0, contrato."idClienteEnGrupo", id_vendedor, 0 , 0
    ) RETURNING id INTO id_contrato;

    -- Valor nueva incripcion y total cobro inscripcion
    tasa_cambio_contrato := cg."tasaCambioContrato"/100;
    valor_nueva_incripcion := (plan.precio * tasa_cambio_contrato);
    IF(porcentaje_tasa = true) THEN
        valor_nueva_incripcion := plan.inscripcion;
    END IF;

    -- Total Pagado primer plan
    total_pagado_primer_plan := hpc."totalMontoCobrado" - cuota."valorCuota";

    -- calcular cuotas a pasar
    numero_cuotas_pasar := (SELECT floor(total_pagado_primer_plan / nueva_cuota));

    -- generar tabla de cuotas
    valor_pagado_cuota := total_pagado_primer_plan - (numero_cuotas_pasar * nueva_cuota);
    total_monto_cobrado := (nueva_cuota * numero_cuotas_pasar) + valor_pagado_cuota;

    abono_restante := 0;
    tasa_administrativa_restante := 0;
    abonos_capital_actual := numero_cuotas_pasar * abono_capital;
    total_tasa_administrativa_cobrada := numero_cuotas_pasar * tasa_administrativa;
    IF(valor_pagado_cuota > 0) THEN
        tasa_administrativa_restante := tasa_administrativa + impuesto;
        abono_restante := valor_pagado_cuota - tasa_administrativa_restante;
        abonos_capital_actual := abonos_capital_actual + abono_restante;
        total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + tasa_administrativa;
    END IF;

    saldo_capital := plan.precio - abonos_capital_actual;

    -- Insertar nuevo historico plan contrato
    INSERT INTO historico_plan_contrato (
        "sisActualizado", "sisCreado",
        "sisHabilitado", "totalInscripcionPlan",
        "valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
        "capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
        "valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
        "totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
        "totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba",
        "valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
    ) VALUES (
        now(), now(),
        'A', 0,
        0, valor_nueva_incripcion, plan.precio,
        0, abonos_capital_actual, saldo_capital,
        tasa_administrativa, total_tasa_administrativa_cobrada, numero_cuotas_pasar,
        0, hpc."totalCuotasMora", total_monto_cobrado, 0, 0,
        0, id_contrato, id_plan, numero_cuotas_pasar,
        0, 'N', 0, 0
    ) RETURNING id INTO id_historico;

    -- comprobaciones del calculo correcto
    capital1 := cuota."abonoCapital" * (hpc."totalCuotasCobradas" - 1);
    capital2 := abonos_capital_actual;
    saldo_pagado_tasa := (SELECT round((SELECT abs(capital1 - capital2)), 3));
    tasa_administrativa1 := (cuota."valorTasaAdministrativa" * (hpc."totalCuotasCobradas" - 1)) + (cuota."valorImpuesto" * (hpc."totalCuotasCobradas" - 1));
    tasa_administrativa2 := (tasa_administrativa * numero_cuotas_pasar) + (impuesto * numero_cuotas_pasar) + tasa_administrativa_restante;
    diferencia := tasa_administrativa1 - tasa_administrativa2;
    valor_absoluto := (SELECT round((SELECT abs(diferencia)), 3));

    if (saldo_pagado_tasa <> valor_absoluto) then
        RETURN '{"correcto":false, "message":"Error en los calculo de comprobacion de la diferencia de la tase administrativa y el capital"}';
    end if;

    PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, valor_pagado_cuota,
        tasa_administrativa, impuesto, abono_capital,
        'N', pms, numero_cuotas_pasar, id_historico);

    PERFORM public.registrar_id_contratos(contrato.id);

    RETURN '{"correcto":true, "message":"Se reactivo correctamente el contrato"}';
    ROLLBACK;
    end;
    $$ language 'plpgsql';

----------- REFINANCIAMIENTO CONTRATO -----------

    create or replace function refinanciamiento_contrato(jsonpar CHAR VARYING) returns CHARACTER VARYING as $$
    Declare
    cuota public.cuota;
    plan public.plan;
    contrato public.contrato;
    hpc public.historico_plan_contrato;
    cg public.configuracion_general;
    numero_cuotas_pagar_refinanciamiento INTEGER;
    valor_cuotas_refinanciamiento NUMERIC;
    numero_cuotas_faltantes_refinanciamiento INTEGER;
    valor_pendiente_pago NUMERIC;
    numero_cuotas_restantes_sin_mora INTEGER;
    valor_agregarse_cuota NUMERIC;
    valor_capital_refinanciado NUMERIC;
    valor_agregarse_capital NUMERIC;
    nuevo_abono_capital NUMERIC;
    tasa_administrativa NUMERIC;
    impuesto NUMERIC;
    abonos_capital_actual NUMERIC;
    numero_cuotas_pasar INTEGER;
    id_historico INTEGER;
    resto_inicio NUMERIC;
    resto_final NUMERIC;
    numero_cuotas_inicio INTEGER;
    numero_cuotas_pagadas INTEGER;
    impuesto_tasa_administrativa NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_monto_cobrado NUMERIC;
    iva NUMERIC;
    nueva_cuota NUMERIC;
    --------- josn value ---------
    id_hpc INTEGER;
    id_contrato INTEGER;
    id_plan INTEGER;
    pms INTEGER;
    BEGIN

    id_hpc := (jsonpar::jsonb->>'idHistoricoPlanContrato')::INTEGER;
    id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
    id_plan := (jsonpar::jsonb->>'idPlan')::INTEGER;
    pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;

    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE id=id_hpc);
    plan := (SELECT pl FROM plan pl WHERE id=id_plan);
    cuota := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc.id LIMIT 1);
    contrato := (SELECT c FROM contrato c WHERE id=id_contrato);
    cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
    numero_cuotas_pagar_refinanciamiento := (SELECT round(hpc."totalCuotasMoraActual" * 0.25));
    valor_cuotas_refinanciamiento := (cuota."valorCuota" * numero_cuotas_pagar_refinanciamiento);
    numero_cuotas_faltantes_refinanciamiento := (SELECT round(hpc."totalCuotasMoraActual" - (hpc."totalCuotasMoraActual" * 0.25)));
    valor_pendiente_pago := (cuota."valorCuota" * numero_cuotas_faltantes_refinanciamiento);
    numero_cuotas_restantes_sin_mora := pms - hpc."totalCuotasMoraActual" - numero_cuotas_faltantes_refinanciamiento;
    valor_agregarse_cuota := (valor_pendiente_pago / numero_cuotas_restantes_sin_mora);
    nueva_cuota := (valor_agregarse_cuota + cuota."valorCuota");
    valor_capital_refinanciado := (cuota."abonoCapital" * numero_cuotas_faltantes_refinanciamiento);
    valor_agregarse_capital := (valor_capital_refinanciado / numero_cuotas_restantes_sin_mora);
    nuevo_abono_capital := (valor_agregarse_capital + cuota."abonoCapital");

    -- calcular tasa administrativa e impuesto
    iva := (cg."ivaPorcentaje"/100);
    tasa_administrativa := (nueva_cuota - nuevo_abono_capital)/(iva + 1);
    impuesto := tasa_administrativa * iva;

    -- crear nuevo contrato
    INSERT INTO contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
        "fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
        "fechaIniciaCobro", "estado", "plazoMesSeleccionado", version, "cuotaActual",
        "tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
        "nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo", "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
    ) VALUES (
        now(), contrato."sisCreado", 'A', contrato."numeroDeContrato",
        contrato."fechaInicio", contrato."fechaFin", contrato."dsctoInscripcion", contrato."dsctoPrimeraCuota", contrato.observacion,
        contrato."fechaIniciaCobro", 'ERP', contrato."plazoMesSeleccionado", (contrato.version + 1), contrato."cuotaActual",
        contrato."tipoDocumentoIdentidad", contrato."documentoIdentidad", plan.modelo, plan.precio,
        contrato."nombresCliente", contrato."apellidosCliente", contrato."nombreGrupo", contrato."dsctoRecargo", contrato."idClienteEnGrupo", contrato."idVendedor", 0 , 0
    ) RETURNING id INTO id_contrato;

    -- sacar los resto de las cuotas que no se pagaron
    resto_inicio := (SELECT "valorPagadoCuota" FROM cuota WHERE "idHistoricoPlanContrato" = hpc.id AND "numeroCuota" = (hpc."totalCuotasCobradas" + 1));
    resto_final := (SELECT "valorPagadoCuota" FROM cuota WHERE "idHistoricoPlanContrato" = hpc.id AND "numeroCuota" = (pms - hpc."numCuotasAbajoHaciaArriba" - 1));


    -- crear nuevo historico plan contrato
    numero_cuotas_pasar := hpc."totalCuotasCobradas" + numero_cuotas_pagar_refinanciamiento + numero_cuotas_faltantes_refinanciamiento;
    numero_cuotas_pagadas := hpc."totalCuotasCobradas" + numero_cuotas_pagar_refinanciamiento;
    abonos_capital_actual := (cuota."abonoCapital" * numero_cuotas_pagadas);
    total_tasa_administrativa_cobrada := (cuota."valorTasaAdministrativa" * numero_cuotas_pagadas);
    total_monto_cobrado := (cuota."valorCuota" * numero_cuotas_pagadas);

    -- sumar posibles cuotas sobrantes
    impuesto_tasa_administrativa := impuesto + tasa_administrativa;
    if(resto_inicio > impuesto_tasa_administrativa) then
        abonos_capital_actual := abonos_capital_actual + resto_inicio - impuesto_tasa_administrativa;
        total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + cuota."valorTasaAdministrativa";
        total_monto_cobrado := total_monto_cobrado + resto_inicio;
    end if;

    if(resto_final > impuesto_tasa_administrativa) then
        abonos_capital_actual := abonos_capital_actual + resto_final - impuesto_tasa_administrativa;
        total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + cuota."valorTasaAdministrativa";
        total_monto_cobrado := total_monto_cobrado + resto_final;
    end if;

    saldo_capital := (nueva_cuota * (numero_cuotas_restantes_sin_mora - 2)) + (nueva_cuota - resto_inicio) + (nueva_cuota - resto_final);

    -- Insertar nuevo historico plan contrato
    INSERT INTO historico_plan_contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "totalInscripcionPlan",
        "valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
        "capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
        "valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
        "totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
        "totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba",
        "valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
    ) VALUES (
        now(), hpc."sisCreado", 'A', hpc."totalInscripcionPlan",
        hpc."valorDsctoInscripcion", hpc."totalCobroInscripcion", hpc."capitalTotal",
        valor_capital_refinanciado, abonos_capital_actual, saldo_capital,
        hpc."valorTasaAdministrativa", total_tasa_administrativa_cobrada, numero_cuotas_pagadas,
        0, hpc."totalCuotasMora", total_monto_cobrado, 0,
        0, 0, id_contrato, plan.id, hpc."numCuotasAbajoHaciaArriba",
        0, 'N', 0, 0
    ) RETURNING id INTO id_historico;

    PERFORM public.cuotas_refinanciamiento(cuota, plan, nueva_cuota, nuevo_abono_capital, tasa_administrativa, impuesto, resto_inicio, numero_cuotas_pasar, resto_final, pms, id_historico);

    INSERT INTO public.refinanciamiento(
        "sisActualizado", "sisCreado", "sisHabilitado",
        "cuotasRestantesSinMora", "fechaRefinanciamiento", "totalCuotas",
        "totalCuotasFaltantesRefinanciamiento", "totalCuotasMora", "totalCuotasPagadas",
        "totalCuotasPagadasRefinanciamiento", "valorAgregarseCuota", "valorCuota",
        "valorPendientePago", "idHistoricoPlanContrato")
    VALUES (
        now(), now(), 'A',
        numero_cuotas_restantes_sin_mora, now(), pms,
        numero_cuotas_faltantes_refinanciamiento, hpc."totalCuotasMora", hpc."totalCuotasCobradas",
        numero_cuotas_pagar_refinanciamiento, valor_agregarse_cuota, nueva_cuota,
        valor_pendiente_pago, id_historico
    );

    PERFORM public.registrar_id_contratos(contrato.id);

    RETURN '{"correcto":true, "message":"Se refinanció el contrato correctamente"}';
    ROLLBACK;
    end;
    $$ language 'plpgsql';

------------- UNIFICACION CONTRATO -------------

    CREATE OR REPLACE FUNCTION unificacion_contrato(jsonpar CHAR VARYING) RETURNS CHARACTER VARYING AS $$
    DECLARE
    contrato1 public.contrato;
    contrato2 public.contrato;
    nuevo_plan public.plan;
    plan1 public.plan;
    plan2 public.plan;
    cg public.configuracion_general;
    hpc1 public.historico_plan_contrato;
    hpc2 public.historico_plan_contrato;
    cuota1 public.cuota;
    cuota2 public.cuota;
    valor_nueva_incripcion NUMERIC;
    tasa_cambio_contrato NUMERIC;
    total_pagado_primer_plan NUMERIC;
    total_pagado_segundo_plan NUMERIC;
    abono_capital NUMERIC;
    iva NUMERIC;
    tasa_administrativa NUMERIC;
    impuesto NUMERIC;
    numero_cuotas_pasar INTEGER;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_monto_cobrado NUMERIC;
    id_historico INTEGER;
    capital1 NUMERIC;
    capital2 NUMERIC;
    capital3 NUMERIC;
    saldo_pagado_tasa NUMERIC;
    tasa_administrativa1 NUMERIC;
    tasa_administrativa2 NUMERIC;
    tasa_administrativa3 NUMERIC;
    diferencia NUMERIC;
    valor_absoluto NUMERIC;
    valor_pagado_cuota NUMERIC;
    valor_recargo NUMERIC;
    id_nuevo_contrato INTEGER;
    fecha_fin DATE;
    abono_restante NUMERIC;
    adicional_inscripcion NUMERIC;
    inscripciones_pagadas_planes NUMERIC;
    total_precios_planes NUMERIC;
    total_pagado_planes NUMERIC;
    fecha_contrato_mayor BOOLEAN;
    nombre_grupo INTEGER;
    id_cliente_en_grupo INTEGER;
    version_contrato INTEGER;
    primera_cuota_pagada CHAR VARYING;
    monto_grupo_mayor NUMERIC;
    tasa_administrativa_restante NUMERIC;
    grupos INTEGER[];
    montos NUMERIC[];
    num_cuotas_abajo_hacia_arriba INTEGER;
    ---------- json values -----------
    id_hpc1 INTEGER;
    id_hpc2 INTEGER;
    id_contrato1 INTEGER;
    id_contrato2 INTEGER;
    id_plan1 INTEGER;
    id_plan2 INTEGER;
    id_nuevo_plan INTEGER;
    nc double precision;
    pms INTEGER;
    observacion CHARACTER VARYING;
    fecha_inicio date;
    fic date;
    dscto_recargo NUMERIC;
    id_vendedor INTEGER;
    nueva_cuota NUMERIC;
    BEGIN

    id_hpc1 := (jsonpar::jsonb->>'idHistoricoPlanContrato1')::INTEGER;
    id_hpc2 := (jsonpar::jsonb->>'idHistoricoPlanContrato2')::INTEGER;
    id_contrato1 := (jsonpar::jsonb->>'idContrato1')::INTEGER;
    id_contrato2 := (jsonpar::jsonb->>'idContrato2')::INTEGER;
    id_plan1 := (jsonpar::jsonb->>'idPlan1')::INTEGER;
    id_plan2 := (jsonpar::jsonb->>'idPlan2')::INTEGER;
    id_nuevo_plan := (jsonpar::jsonb->>'idNuevoPlan')::INTEGER;
    nc := (jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
    pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
    observacion := (jsonpar::jsonb->>'observacion')::VARCHAR;
    fecha_inicio := (jsonpar::jsonb->>'fechaInicio')::DATE;
    fic := (jsonpar::jsonb->>'fechaInicioCobro')::DATE;
    dscto_recargo := (jsonpar::jsonb->>'dsctoRecargo')::NUMERIC;
    id_vendedor := (jsonpar::jsonb->>'idVendedor')::INTEGER;
    nueva_cuota := (jsonpar::jsonb->>'cuota')::NUMERIC;

    adicional_inscripcion:=0;
    cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
    plan1 := (SELECT pl FROM plan pl WHERE id= id_plan1);
    plan2 := (SELECT pl FROM plan pl WHERE id=id_plan2);
    nuevo_plan := (SELECT pl FROM plan pl WHERE id=id_nuevo_plan);
    contrato1 := (SELECT co FROM contrato co WHERE id=id_contrato1);
    contrato2 := (SELECT co FROM contrato co WHERE id=id_contrato2);
    hpc1 := (SELECT h FROM historico_plan_contrato h WHERE id=id_hpc1);
    hpc2 := (SELECT h FROM historico_plan_contrato h WHERE id=id_hpc2);
    cuota1 := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc1.id LIMIT 1);
    cuota2 := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc2.id LIMIT 1);

    -- verificar que el contrato este en estado registrado o en proceso
    IF contrato1.estado <> 'REG' AND contrato1.estado <> 'EPR' THEN
        RETURN '{"correcto":false, "message":"El contrato 1 no se encuentra en estado registrado o en proceso"}';
    END IF;

    -- verificar que el contrato este en estado registrado o en proceso
    IF contrato2.estado <> 'REG' AND contrato2.estado <> 'EPR' THEN
        RETURN '{"correcto":false, "message":"El contrato 2 no se encuentra en estado registrado o en proceso"}';
    END IF;

    -- verificar que el contrato 1 y el contrato 2 tengan la primera cuota pagada
    IF (hpc1."totalCuotasCobradas" = 0) THEN
        RETURN '{"correcto":false, "message":"El contrato 1 no tiene cuotas pagadas"}';
    END IF;
    IF (hpc2."totalCuotasCobradas" = 0) THEN
        RETURN '{"correcto":false, "message":"El contrato 2 no tiene cuotas pagadas"}';
    END IF;

    -- calcular fecha fin de contrato
    fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

    -- contrato mas antiguo
    fecha_contrato_mayor := (select (contrato1."fechaInicio"::date >= contrato2."fechaInicio"::date));
    if(fecha_contrato_mayor) then
        nombre_grupo := contrato2."nombreGrupo";
        id_cliente_en_grupo := contrato2."idClienteEnGrupo";
        version_contrato := contrato2.version;
        monto_grupo_mayor := cuota2."abonoCapital" * (hpc2."totalCuotasCobradas" - 1);
        grupos := ARRAY[nombre_grupo, contrato1."nombreGrupo"];
        montos := ARRAY[monto_grupo_mayor, monto_grupo_mayor];
    ELSE
        nombre_grupo := contrato1."nombreGrupo";
        id_cliente_en_grupo := contrato1."idClienteEnGrupo";
        version_contrato := contrato1.version;
        monto_grupo_mayor := cuota1."abonoCapital" * (hpc1."totalCuotasCobradas" - 1);
        grupos := ARRAY[nombre_grupo, contrato2."nombreGrupo"];
        montos := ARRAY[monto_grupo_mayor, monto_grupo_mayor];
    end if;

    -- se desactiva y se cambia el estado de los contratos
    UPDATE contrato SET "sisHabilitado"='I', estado='UNI' WHERE id = id_contrato1;
    UPDATE contrato SET "sisHabilitado"='I', estado='UNI' WHERE id = id_contrato2;

    -- calcular el valor de la nueva cuota
    abono_capital := nuevo_plan.precio/pms;
    iva := (cg."ivaPorcentaje"/100);
    tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
    impuesto := tasa_administrativa * iva;

    -- crear nuevo contrato
    INSERT INTO contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
        "fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
        "fechaIniciaCobro", "estado", "plazoMesSeleccionado", version, "cuotaActual",
        "tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
        "nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo", "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
    ) VALUES (
        now(), now(), 'A', nc, fecha_inicio, fecha_fin, 0, 0,
        observacion, fic, 'REG', pms, (version_contrato + 1), nueva_cuota,
        contrato1."tipoDocumentoIdentidad", contrato1."documentoIdentidad", nuevo_plan.modelo, nuevo_plan.precio,
        contrato1."nombresCliente", contrato1."apellidosCliente", nombre_grupo, dscto_recargo, id_cliente_en_grupo, id_vendedor, 0
    ) RETURNING id INTO id_nuevo_contrato;

    -- totla precio de los 2 planes anteriores
    total_precios_planes := plan1.precio + plan2.precio;

    -- comprobar si la inscripcion del plan actual es mayor que la del plan anterior
    if (nuevo_plan.precio > total_precios_planes) then
    -- Valor pagados por inscripciones de los 2 planes
    inscripciones_pagadas_planes := plan1.inscripcion + plan2.inscripcion;
    adicional_inscripcion := nuevo_plan.inscripcion - inscripciones_pagadas_planes;
    end if;

    -- Valor nueva incripcion y total cobro inscripcion
    tasa_cambio_contrato := cg."tasaCambioContrato"/100;
    valor_nueva_incripcion := (nuevo_plan.precio * tasa_cambio_contrato) + adicional_inscripcion;

    -- Total pagado de cada uno de los planes
    total_pagado_primer_plan := hpc1."totalMontoCobrado" - cuota1."valorCuota";
    total_pagado_segundo_plan := hpc2."totalMontoCobrado" - cuota2."valorCuota";
    total_pagado_planes := total_pagado_primer_plan + total_pagado_segundo_plan;

    -- calcular cuotas a pasar
    numero_cuotas_pasar := (SELECT floor(total_pagado_planes / nueva_cuota));

    -- generar tabla de cuotas
    valor_pagado_cuota := total_pagado_planes - (numero_cuotas_pasar * nueva_cuota);
    total_monto_cobrado := (nueva_cuota * numero_cuotas_pasar) + valor_pagado_cuota;

    abono_restante := 0;
    tasa_administrativa_restante := 0;
    abonos_capital_actual := numero_cuotas_pasar * abono_capital;
    total_tasa_administrativa_cobrada := numero_cuotas_pasar * tasa_administrativa;
    IF(valor_pagado_cuota > 0) THEN
        tasa_administrativa_restante := tasa_administrativa + impuesto;
        abono_restante := valor_pagado_cuota - tasa_administrativa_restante;
        abonos_capital_actual := abonos_capital_actual + abono_restante;
        total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + tasa_administrativa;
    END IF;

    saldo_capital := nuevo_plan.precio - abonos_capital_actual;

    -- calculos del descuento por recargo
    if(dscto_recargo <> 0) then
        dscto_recargo := cg."cuotaAdministrativa" * (desc_recargo/100);
        valor_recargo := cg."cuotaAdministrativa" - dscto_recargo;
    else
        valor_recargo := cg."cuotaAdministrativa";
    end if;

    -- Insertar nuevo historico plan contrato
    INSERT INTO historico_plan_contrato (
        "sisActualizado", "sisCreado",
        "sisHabilitado", "totalInscripcionPlan",
        "valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
        "capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
        "valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
        "totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
        "totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba",
        "valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
    ) VALUES (
        now(), now(),
        'A', nuevo_plan.inscripcion,
        0, valor_nueva_incripcion, nuevo_plan.precio,
        0, abonos_capital_actual, saldo_capital,
        tasa_administrativa, total_tasa_administrativa_cobrada, numero_cuotas_pasar,
        0, 0, total_monto_cobrado,0, 0,
        valor_recargo, id_nuevo_contrato, id_nuevo_plan, numero_cuotas_pasar,
        0, 'N', 0, 0
    ) RETURNING id INTO id_historico;

    -- comprobaciones del calculo correcto
    capital1 := cuota1."abonoCapital" * (hpc1."totalCuotasCobradas" - 1);
    capital2 := cuota2."abonoCapital" * (hpc2."totalCuotasCobradas" - 1);
    capital3 := abonos_capital_actual;
    saldo_pagado_tasa := (SELECT abs((capital1 + capital2) - capital3));
    tasa_administrativa1 := (cuota1."valorTasaAdministrativa" * (hpc1."totalCuotasCobradas" - 1)) + (cuota1."valorImpuesto" * (hpc1."totalCuotasCobradas" - 1));
    tasa_administrativa2 := (cuota2."valorTasaAdministrativa" * (hpc2."totalCuotasCobradas" - 1)) + (cuota2."valorImpuesto" * (hpc2."totalCuotasCobradas" - 1));
    tasa_administrativa3 := (tasa_administrativa * numero_cuotas_pasar) + (impuesto * numero_cuotas_pasar) + tasa_administrativa_restante;
    diferencia := tasa_administrativa1 + tasa_administrativa2 - tasa_administrativa3;
    valor_absoluto := (SELECT round((SELECT abs(diferencia)), 3));

    if (saldo_pagado_tasa <> valor_absoluto) then
        RETURN '{"correcto":false, "message":"Error en los calculo de comprobacion de la diferencia de la tase administrativa y el capital"}';
    end if;

    PERFORM public.actualizar_grupo(grupos, montos);

    PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, valor_pagado_cuota,
        tasa_administrativa, impuesto, abono_capital,
        'N', pms, numero_cuotas_pasar, id_historico);

    PERFORM public.registrar_id_contratos(contrato1.id);
    PERFORM public.registrar_id_contratos(contrato2.id);

    RETURN '{"correcto":true, "message":"Se unificaron los contratos correctamente"}';
    ROLLBACK;
    END;
    $$ LANGUAGE 'plpgsql';

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

---------- CESION DE DERECHOS CONTRATO ----------

    create or replace function cesion_derechos_contrato(jsonpar CHARACTER VARYING) returns CHARACTER VARYING as $$
    Declare
    contrato public.contrato;
    plan public.plan;
    cg public.configuracion_general;
    hpc public.historico_plan_contrato;
    cuota public.cuota;
    usuario public.usuario;
    valor_nueva_incripcion NUMERIC;
    tasa_cambio_contrato NUMERIC;
    total_pagado_primer_plan NUMERIC;
    abono_capital NUMERIC;
    iva NUMERIC;
    tasa_administrativa NUMERIC;
    impuesto NUMERIC;
    numero_cuotas_pasar INTEGER;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_monto_cobrado NUMERIC;
    id_historico INTEGER;
    capital1 NUMERIC;
    capital2 NUMERIC;
    saldo_pagado_tasa NUMERIC;
    tasa_administrativa1 NUMERIC;
    tasa_administrativa2 NUMERIC;
    diferencia NUMERIC;
    valor_absoluto NUMERIC;
    valor_pagado_cuota NUMERIC;
    valor_recargo NUMERIC;
    fecha_fin DATE;
    abono_restante NUMERIC;
    primera_cuota_pagada CHAR VARYING;
    tasa_administrativa_restante NUMERIC;
    num_cuotas_abajo_hacia_arriba INTEGER;
    ------ json values ------
    id_cliente INTEGER;
    id_hpc INTEGER;
    id_contrato INTEGER;
    id_plan INTEGER;
    nc DOUBLE PRECISION;
    pms INTEGER;
    observacion CHARACTER VARYING;
    fecha_inicio DATE;
    fic DATE;
    valor_a_devolver NUMERIC;
    nueva_cuota NUMERIC;
    Begin

    id_cliente = (jsonpar::jsonb->>'idCliente')::INTEGER;
    id_hpc := (jsonpar::jsonb->>'idHistoricoPlanContrato')::INTEGER;
    id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
    id_plan := (jsonpar::jsonb->>'idPlan')::INTEGER;
    nc := (jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
    pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
    observacion := (jsonpar::jsonb->>'observacion')::VARCHAR;
    fecha_inicio := (jsonpar::jsonb->>'fechaInicio')::DATE;
    fic := (jsonpar::jsonb->>'fechaInicioCobro')::DATE;
    valor_a_devolver := (jsonpar::jsonb->>'valorADevolver')::NUMERIC;
    nueva_cuota := (jsonpar::jsonb->>'cuota')::NUMERIC;

    usuario := (SELECT u FROM cliente c INNER JOIN usuario u ON u.id = c."idUsuario" WHERE c.id = id_cliente);
    cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
    plan := (SELECT pl FROM plan pl WHERE id=id_plan);
    contrato := (SELECT co FROM contrato co WHERE id=id_contrato);
    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE id=id_hpc);
    cuota := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc.id LIMIT 1);

    -- verificar que el contrato este en estado registrado o en proceso
    IF contrato.estado <> 'REG' AND contrato.estado <> 'EPR' THEN
        RETURN '{"correcto":false, "message":"El contrato no se encuentra en estado registrado o en proceso"}';
    END IF;

    -- verificar que el contrato anterior tengan la primera cuota pagada
    primera_cuota_pagada := (SELECT "estaPagado" FROM cuota WHERE "idHistoricoPlanContrato"=hpc.id AND "numeroCuota"=1);
    IF (primera_cuota_pagada = 'N') THEN
        RETURN '{"correcto":false, "message":"El contrato anterior no tiene cuotas pagadas"}';
    END IF;

    -- calcular fecha fin de contrato
    fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

    -- se desactiva y se cambia el estado de los contratos
    UPDATE contrato SET "sisHabilitado"='I', estado='CSD' WHERE id = id_contrato;
    UPDATE historico_plan_contrato SET "valorADevolver" = valor_a_devolver WHERE id = id_hpc;

    -- calcular nueva cuota
    abono_capital := plan.precio/pms;
    iva := (cg."ivaPorcentaje"/100);
    tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
    impuesto := tasa_administrativa * iva;

    -- crear nuevo contrato
    INSERT INTO contrato (
        "sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
        "fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
        "fechaIniciaCobro", estado, "plazoMesSeleccionado", version, "cuotaActual",
        "tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
        "nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo",
        "idClienteEnGrupo", "idVendedor"
    ) VALUES (
        now(), now(), 'A', nc, fecha_inicio, fecha_fin, 0, 0,
        observacion, fic, 'REG', pms, (contrato.version + 1), nueva_cuota,
        contrato."tipoDocumentoIdentidad", contrato."documentoIdentidad", plan.modelo, plan.precio,
        usuario.nombres, usuario.apellidos, contrato."nombreGrupo", 0,
        contrato."idClienteEnGrupo", contrato."idVendedor" , 0
    ) RETURNING id INTO id_contrato;

    -- Valor nueva incripcion y total cobro inscripcion
    tasa_cambio_contrato := cg."tasaCambioContrato"/100;
    valor_nueva_incripcion := (plan.precio * tasa_cambio_contrato);

    -- Total Pagado primer plan
    total_pagado_primer_plan := hpc."totalMontoCobrado" - cuota."valorCuota";

    -- calcular cuotas a pasar
    numero_cuotas_pasar := (SELECT floor(total_pagado_primer_plan / nueva_cuota));

    -- generar tabla de cuotas
    valor_pagado_cuota := total_pagado_primer_plan - (numero_cuotas_pasar * nueva_cuota);
    total_monto_cobrado := (nueva_cuota * numero_cuotas_pasar) + valor_pagado_cuota;

    abono_restante := 0;
    tasa_administrativa_restante := 0;
    abonos_capital_actual := numero_cuotas_pasar * abono_capital;
    total_tasa_administrativa_cobrada := numero_cuotas_pasar * tasa_administrativa;
    IF(valor_pagado_cuota > 0) THEN
        tasa_administrativa_restante := tasa_administrativa + impuesto;
        abono_restante := valor_pagado_cuota - tasa_administrativa_restante;
        abonos_capital_actual := abonos_capital_actual + abono_restante;
        total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + tasa_administrativa;
    END IF;

    saldo_capital := plan.precio - abonos_capital_actual;

    -- Insertar nuevo historico plan contrato
    INSERT INTO historico_plan_contrato (
        "sisActualizado", "sisCreado",
        "sisHabilitado", "totalInscripcionPlan",
        "valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
        "capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
        "valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
        "totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
        "totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba",
        "valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
    ) VALUES (
        now(), now(),
        1, plan.inscripcion,
        0, valor_nueva_incripcion, plan.precio,
        0, abonos_capital_actual, saldo_capital,
        tasa_administrativa, total_tasa_administrativa_cobrada, numero_cuotas_pasar,
        0, 0, total_monto_cobrado, 0, 0,
        0, id_contrato, id_plan, num_cuotas_abajo_hacia_arriba,
        valor_a_devolver, 'N', 0, 0
    ) RETURNING id INTO id_historico;

    -- comprobaciones del calculo correcto
    capital1 := cuota."abonoCapital" * (hpc."totalCuotasCobradas" - 1);
    capital2 := abonos_capital_actual;
    saldo_pagado_tasa := (SELECT round((SELECT abs(capital1 - capital2)), 3));
    tasa_administrativa1 := (cuota."valorTasaAdministrativa" * (hpc."totalCuotasCobradas" - 1)) + (cuota."valorImpuesto" * (hpc."totalCuotasCobradas" - 1));
    tasa_administrativa2 := (tasa_administrativa * numero_cuotas_pasar) + (impuesto * numero_cuotas_pasar) + tasa_administrativa_restante;
    diferencia := tasa_administrativa1 - tasa_administrativa2;
    valor_absoluto := (SELECT round((SELECT abs(diferencia)), 3));

    if (saldo_pagado_tasa <> valor_absoluto) then
        RETURN '{"correcto":false, "message":"Error en los calculo de comprobacion de la diferencia de la tase administrativa y el capital"}';
    end if;

    PERFORM public.actualizar_grupo(ARRAY[contrato."nombreGrupo"], ARRAY[saldo_pagado_tasa]);

    PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, valor_pagado_cuota,
        tasa_administrativa, impuesto, abono_capital,
        'N', pms, numero_cuotas_pasar, id_historico);
    
    PERFORM public.registrar_id_contratos(contrato.id);

    RETURN '{"correcto":true, "message":"Se realizo correctamente la cesion de derechos"}';
    ROLLBACK;
    end;
    $$ language 'plpgsql';

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

------ COBROS CONTRATO ------

    CREATE OR REPLACE FUNCTION cobros_contrato(jsonpar CHAR VARYING) RETURNS CHARACTER VARYING AS $$
    DECLARE
    count INTEGER;
    count2 INTEGER;
    count3 INTEGER;
    num INTEGER;
    num2 INTEGER;
    num3 INTEGER;
    id_cobro INTEGER;
    c public.contrato;
    hpc public.historico_plan_contrato;
    cuo public.cuota;
    monto_pagado_cuota NUMERIC;
    dispositivo_pagado NUMERIC;
    estado_pago_dispositivo CHAR(1);
    rastreo_pagado NUMERIC;
    estado_pago_rastreo CHAR(1);

    ----- historico plan contrato -----
    total_cobro_inscripcion NUMERIC;
    valor_pagado_inscripcion NUMERIC;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_cuotas_cobradas NUMERIC;
    total_monto_cobrado NUMERIC;
    total_cobro_primera_cuota NUMERIC;
    cargos_adjudicacion NUMERIC;
    inscripcion_esta_pagada CHAR(1);
    total_dispositivo_cobrado NUMERIC;
    valor_a_pagar_inscripcion NUMERIC;

    ----- otras variables -----
    cuota_a_cobrar INTEGER;
    valor_cuota NUMERIC;
    monto_pagado_dispositivo NUMERIC;
    monto_pagado_rastreo NUMERIC;

    ----- variables para actualizar grupo y fondo contrato -----
    id_grupo INTEGER;

    ----- tabla cobro -----
    valor_a_cobrar NUMERIC;
    id_contrato INTEGER;
    array_pagos jsonb;
    array_detalle_cobros jsonb;

    ---- tabla pagos ----
    tipo_documento CHAR VARYING;
    observaciones CHAR VARYING;
    numero_documento CHAR VARYING;
    valor NUMERIC;
    fecha_deposito DATE;
    detalle_pagos jsonb;
    id_cuenta_bancaria_empresa INTEGER;

    ----- tabla detalle de pagos
    valor_detalle NUMERIC;
    abono_capital CHAR VARYING;
    cuota_administrativa CHAR VARYING;
    oferta CHAR VARYING;
    cargo_adjudicacion CHAR VARYING;
    numero_cuota_pagos INTEGER;
    tipo CHAR VARYING;
    id_item_cobro_pago INTEGER;
    id_pago INTEGER;

    ----- tabla detalle de cobros
    numero_cuota INTEGER;
    a_cobrar NUMERIC;
    tipo_cobro CHAR VARYING;
    detalle_cobros TEXT;

    BEGIN
    ------- sacar valores de jsonpar -------
    valor_a_cobrar := (jsonpar::jsonb->>'valorACobrar')::NUMERIC;
    id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
    array_pagos := (jsonpar::jsonb->>'pagos');
    array_detalle_cobros := (jsonpar::jsonb->>'detalleCobros');
    detalle_cobros := (SELECT jsonb_pretty(array_detalle_cobros));
    ------- creacion del cobro -------
    INSERT INTO cobro (
        "sisActualizado","sisCreado", "sisHabilitado",
        "valorACobrar", "detalleCobros", "idContrato"
    ) VALUES (
        now(), now(), 'S',
        valor_a_cobrar, detalle_cobros, id_contrato
    ) RETURNING id INTO id_cobro;

    count := 0;
    num := jsonb_array_length(array_pagos);

    ----- recorrer pagos -----
    IF (num <> 0) THEN
        WHILE (count < num)
        LOOP
            ----- sacar valores de array_pagos -----
            tipo_documento := ((array_pagos->count)->>'tipoDocumento');
            observaciones := ((array_pagos->count)->>'observaciones');
            numero_documento := ((array_pagos->count)->>'numeroDocumento');
            valor := ((array_pagos->count)->>'valor')::NUMERIC;
            fecha_deposito := ((array_pagos->count)->>'fechaDeposito')::DATE;
            detalle_pagos := ((array_pagos->count)->>'detallePago')::jsonb;
            id_cuenta_bancaria_empresa := ((array_pagos->count)->>'idCuentaBancariaEmpresa')::INTEGER;
            INSERT INTO pago (
                "sisActualizado","sisCreado", "sisHabilitado",
                "tipoDocumento", "observaciones", "numeroDocumento",
                "valor", "fechaDeposito", "idCobro", "idCuentaBancariaEmpresa"
            ) VALUES (
                now(), now(), 'S',
                tipo_documento, observaciones, numero_documento,
                valor, fecha_deposito, id_cobro, id_cuenta_bancaria_empresa
            ) RETURNING id INTO id_pago;

            ----- recorrer detalle de pagos -----
            count2 := 0;
            num2 := jsonb_array_length(detalle_pagos);
            IF(num2 <> 0)THEN
                WHILE (count2 < num2)
                LOOP
                    ----- sacar valores de detalle_pagos -----
                    valor_detalle := ((detalle_pagos->count2)->>'valor')::NUMERIC;
                    abono_capital := ((detalle_pagos->count2)->>'abonoCapital');
                    cuota_administrativa := ((detalle_pagos->count2)->>'cuotaAdministrativa');
                    oferta := ((detalle_pagos->count2)->>'oferta');
                    cargo_adjudicacion := ((detalle_pagos->count2)->>'cargoAdjudicacion');
                    numero_cuota_pagos := ((detalle_pagos->count2)->>'numeroCuota')::INTEGER;
                    tipo := ((detalle_pagos->count2)->>'tipo');
                    id_item_cobro_pago := ((detalle_pagos->count2)->>'idItemCobroPago')::INTEGER;
                    INSERT INTO detalle_pago (
                        "sisActualizado","sisCreado", "sisHabilitado",
                        "valor", "abonoCapital", "cuotaAdministrativa",
                        "oferta", "cargoAdjudicacion", "numeroCuota",
                        "tipo", "idItemCobroPago", "idPago"
                    ) VALUES (
                        now(), now(), 'S',
                        valor_detalle, abono_capital, cuota_administrativa,
                        oferta, cargo_adjudicacion, numero_cuota_pagos,
                        tipo, id_item_cobro_pago, id_pago
                    );
                    count2 := count2 + 1;
                END LOOP;
            END IF;
            count:=count+1;
        END LOOP;
    END IF;

    ----- sacar datos de tablas relacionadas -----
    c := (SELECT c1 FROM contrato c1 WHERE c1.id = id_contrato);
    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE hpc1."idContrato" = c.id ORDER BY hpc1."sisCreado" DESC LIMIT 1);

    ----- sacar datos para actualizar del historico de plan contrato -----
    total_cobro_inscripcion := hpc."totalCobroInscripcion";
    valor_pagado_inscripcion := hpc."valorPagadoInscripcion";
    abonos_capital_actual := hpc."abonosCapitalActual";
    saldo_capital := hpc."saldoCapital";
    total_tasa_administrativa_cobrada := hpc."totalTasaAdministrativaCobrada";
    total_cuotas_cobradas := hpc."totalCuotasCobradas";
    total_monto_cobrado := hpc."totalMontoCobrado";
    total_cobro_primera_cuota := hpc."totalCobroPrimeraCuota";
    cargos_adjudicacion := hpc."cargosAdjudicacion";
    inscripcion_esta_pagada := hpc."inscripcionEstaPagada";

    ----- valores iniciales para actualizar el historico de plan contrato -----
    valor_a_pagar_inscripcion := hpc."totalInscripcionPlan" - hpc."valorDsctoInscripcion";
    monto_pagado_cuota := 0;
    dispositivo_pagado := 0;
    rastreo_pagado := 0;
    monto_pagado_dispositivo := 0;
    monto_pagado_rastreo := 0;

    ----- recorrer detalle de cobros -----
    count3 := 0;
    num3 := jsonb_array_length(array_detalle_cobros);
    IF (num3 <> 0) THEN
        WHILE (count3 < num3)
        LOOP
            ----- sacar valores de array_detalle_cobros -----
            numero_cuota := ((array_detalle_cobros->count3)->>'noCuota')::INTEGER;
            a_cobrar := ((array_detalle_cobros->count3)->>'aCobrar')::NUMERIC;
            tipo_cobro := ((array_detalle_cobros->count3)->>'tipo');
            ----- actualizar cuotas pagadas -----
            cuo := (SELECT cuo1 FROM cuota cuo1 WHERE cuo1."numeroCuota" = numero_cuota AND cuo1."idHistoricoPlanContrato" = hpc.id);
            total_dispositivo_cobrado := (SELECT "totalDispositivoCobrado" FROM historico_plan_contrato WHERE id = hpc.id);
            valor_cuota := cuo."valorCuota" + cuo.dispositivo + cuo.rastreo;
            estado_pago_dispositivo := 'N';
            estado_pago_rastreo := 'N';
            IF (tipo_cobro = 'I') THEN
                inscripcion_esta_pagada := CASE WHEN a_cobrar = valor_a_pagar_inscripcion THEN 'S' ELSE 'N' END;
                total_cobro_inscripcion := total_cobro_inscripcion + a_cobrar;
                valor_pagado_inscripcion := valor_pagado_inscripcion + a_cobrar;
                IF(inscripcion_esta_pagada = 'S')THEN
                    UPDATE contrato SET "cuotaACobrar" = numero_cuota + 1 WHERE id = id_contrato;
                END IF;
            END IF;
            IF (tipo_cobro = 'AC') THEN
                monto_pagado_cuota := monto_pagado_cuota + a_cobrar;
                abonos_capital_actual := abonos_capital_actual + a_cobrar;
                saldo_capital := saldo_capital - a_cobrar;
                UPDATE cuota SET "valorPagadoCuota"= (cuo."valorPagadoCuota" + a_cobrar) WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            ELSEIF (tipo_cobro = 'CA') THEN
                monto_pagado_cuota := monto_pagado_cuota + a_cobrar;
                total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + a_cobrar;
                UPDATE cuota SET "valorPagadoCuota"= (cuo."valorPagadoCuota" + a_cobrar) WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'D') THEN
                monto_pagado_cuota := monto_pagado_cuota + a_cobrar;
                monto_pagado_dispositivo := cuo."valorPagadoDispositivo" + a_cobrar;
                IF(monto_pagado_dispositivo = cuo.dispositivo) THEN
                    total_dispositivo_cobrado := total_dispositivo_cobrado + 1;
                    estado_pago_dispositivo := 'S';
                    monto_pagado_dispositivo := 0;
                END IF;
                UPDATE cuota SET "dispositivoEstaPagado" = estado_pago_dispositivo, "valorPagadoDispositivo"= monto_pagado_dispositivo WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'R') THEN
                monto_pagado_cuota := monto_pagado_cuota + a_cobrar;
                monto_pagado_rastreo := cuo."valorPagadoRastreo" + a_cobrar;
                IF(monto_pagado_rastreo = cuo.rastreo) THEN
                    estado_pago_rastreo := 'S';
                    monto_pagado_rastreo := 0;
                END IF;
                UPDATE cuota SET "rastreoEstaPagado" = estado_pago_rastreo, "valorPagadoDispositivo"= monto_pagado_rastreo WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'CAD') THEN
                cargos_adjudicacion := cargos_adjudicacion + a_cobrar;
                UPDATE historico_plan_contrato SET "cargosAdjudicacion" = cargos_adjudicacion WHERE id = hpc.id;
            END IF;
            IF (monto_pagado_cuota = valor_cuota AND numero_cuota <> 1) THEN
                UPDATE cuota SET "estaPagado" = 'S' WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
                UPDATE contrato SET "cuotaACobrar" = numero_cuota + 1 WHERE id = id_contrato;
                total_cuotas_cobradas := numero_cuota;
                cuota_a_cobrar := numero_cuota + 1;
                total_monto_cobrado := total_monto_cobrado + monto_pagado_cuota;
                monto_pagado_cuota := 0;
            ELSEIF (monto_pagado_cuota < (valor_cuota - hpc."valorDsctoPrimeraCuota") AND numero_cuota = 1) THEN
                total_cobro_primera_cuota := monto_pagado_cuota + total_cobro_primera_cuota;
            ELSEIF (monto_pagado_cuota = (valor_cuota - hpc."valorDsctoPrimeraCuota") AND numero_cuota = 1) THEN
                UPDATE cuota SET "estaPagado" = 'S' WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
                UPDATE contrato SET "cuotaACobrar" = numero_cuota + 1 WHERE id = id_contrato;
                total_cobro_primera_cuota := monto_pagado_cuota;
                monto_pagado_cuota := 0;
            END IF;

            ----- actualizar historico plan contrato -----   
            UPDATE historico_plan_contrato SET 
            "totalCobroInscripcion" = total_cobro_inscripcion, "valorPagadoInscripcion" = valor_pagado_inscripcion, 
            "abonosCapitalActual" = abonos_capital_actual, "saldoCapital" = saldo_capital, 
            "totalTasaAdministrativaCobrada" = total_tasa_administrativa_cobrada, "totalCuotasCobradas" = total_cuotas_cobradas, 
            "totalMontoCobrado" = total_monto_cobrado, "totalCobroPrimeraCuota" = total_cobro_primera_cuota, 
            "cargosAdjudicacion" = cargos_adjudicacion, "inscripcionEstaPagada" = inscripcion_esta_pagada,
            "totalDispositivoCobrado" = total_dispositivo_cobrado 
            WHERE id = hpc.id;
            count3 := count3 + 1;
        END LOOP;
    END IF;

    ------- actializar fondo del contrato -------
    PERFORM actualizar_fondo_contrato(id_contrato);

    ------- actualizar monto meta del grupo -------
    id_grupo := (SELECT cg."idGrupo" FROM cliente_en_grupo cg INNER JOIN contrato c1 ON c1."idClienteEnGrupo" = cg."id" WHERE c1.id = id_contrato);
    PERFORM actualizar_monto_meta(id_grupo);

    RETURN '{"correcto":true, "message":"El cobro se realizo correctamente"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;

    CREATE OR REPLACE FUNCTION anulacion_cobro(jsonbpar CHAR VARYING) RETURNS TEXT AS $$
    DECLARE
    id_cobro INTEGER;
    cob public.cobro;
    con public.contrato;
    hpc public.historico_plan_contrato;
    cuo public.cuota;
    cuo22 public.cuota;
    array_detalle_cobros jsonb;

    ----- historico plan contrato -----
    total_cobro_inscripcion NUMERIC;
    valor_pagado_inscripcion NUMERIC;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_cuotas_cobradas NUMERIC;
    total_monto_cobrado NUMERIC;
    total_cobro_primera_cuota NUMERIC;
    cargos_adjudicacion NUMERIC;
    inscripcion_esta_pagada CHAR(1);
    total_dispositivo_cobrado NUMERIC;

    ----- detalle de cobros -----
    numero_cuota INTEGER;
    a_cobrar NUMERIC;
    tipo_cobro CHAR VARYING;
    dispositivo_pagado NUMERIC;
    valor_primera_cuota NUMERIC;
    tasa_administrativa NUMERIC;

    ----- otras variables -----
    monto_pagado_cuota NUMERIC;
    valor_cuota NUMERIC;
    estado_pago_cuota CHAR(1);
    count INTEGER;
    num INTEGER;
    BEGIN
    id_cobro := (jsonbpar::jsonb->>'id')::INTEGER;
    cob := (SELECT co FROM cobro co WHERE id_cobro = id_cobro);
    con := (SELECT c1 FROM cobro INNER JOIN contrato c1 ON c1.id=cobro."idContrato" WHERE cobro.id=jsonbpar.id_cobro);
    hpc := (SELECT h1 FROM historico_plan_contrato h1 WHERE h1."idContrato"=con.id ORDER BY h1.id DESC LIMIT 1);
    array_detalle_cobros := (cob."detalleCobros")::jsonb;

    ----- sacar datos para actualizar del historico de plan contrato -----
    total_cobro_inscripcion := hpc."totalCobroInscripcion";
    valor_pagado_inscripcion := hpc."valorPagadoInscripcion";
    abonos_capital_actual := hpc."abonosCapitalActual";
    saldo_capital := hpc."saldoCapital";
    total_tasa_administrativa_cobrada := hpc."totalTasaAdministrativaCobrada";
    total_cuotas_cobradas := hpc."totalCuotasCobradas";
    total_monto_cobrado := hpc."totalMontoCobrado";
    total_cobro_primera_cuota := hpc."totalCobroPrimeraCuota";
    cargos_adjudicacion := hpc."cargosAdjudicacion";
    inscripcion_esta_pagada := hpc."inscripcionEstaPagada";
    total_dispositivo_cobrado := hpc."totalDispositivoCobrado";

    ----- recorrer detalle de cobros -----
    monto_pagado_cuota := 0;
    count := 0;
    num := jsonb_array_length(array_detalle_cobros) - 1;
    IF(num >= 0) THEN
        WHILE (num >= count)
        LOOP
            ----- sacar valores de array_detalle_cobros -----
            numero_cuota := ((array_detalle_cobros->num)->>'noCuota')::INTEGER;
            a_cobrar := ((array_detalle_cobros->num)->>'aCobrar')::NUMERIC;
            tipo_cobro := ((array_detalle_cobros->num)->>'tipoCobro')::CHAR VARYING;
            ----- actualizar cuotas a devolver -----
            cuo := (SELECT cuo1 FROM cuota cuo1 WHERE cuo1."numeroCuota" = numero_cuota AND cuo1."idHistoricoPlanContrato" = hpc.id);
            IF(tipo_cobro = 'I') THEN
                inscripcion_esta_pagada := 'N';
                total_cobro_inscripcion := total_cobro_inscripcion - a_cobrar;
                valor_pagado_inscripcion := valor_pagado_inscripcion - a_cobrar;
                UPDATE historico_plan_contrato SET 
                "inscripcionEstaPagada" = inscripcion_esta_pagada,
                "totalCobroInscripcion" = total_cobro_inscripcion, 
                "valorPagadoInscripcion" = valor_pagado_inscripcion
                WHERE id = hpc.id;
            END IF;
            IF (tipo_cobro = 'AC') THEN
                monto_pagado_cuota := cuo."valorPagadoCuota" - a_cobrar;
                abonos_capital_actual := abonos_capital_actual - a_cobrar;
                saldo_capital := saldo_capital + a_cobrar;
                UPDATE cuota SET "valorPagadoCuota"= monto_pagado_cuota WHERE id = cuo.id;
            ELSEIF (tipo_cobro = 'CA') THEN
                monto_pagado_cuota := cuo."valorPagadoCuota" - a_cobrar;
                total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada - a_cobrar;
                UPDATE cuota SET "valorPagadoCuota"= monto_pagado_cuota WHERE id = cuo.id;
            END IF;
            IF (tipo_cobro = 'D') THEN
                monto_pagado_cuota := cuo."valorPagadoDispositivo" - a_cobrar;
                UPDATE cuota SET "valorPagadoDispositivo"= monto_pagado_cuota WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'R') THEN
                monto_pagado_cuota := cuo."valorPagadoRastreo" - a_cobrar;
                UPDATE cuota SET "valorPagadoRastreo"= monto_pagado_cuota WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'CAD') THEN
                cargos_adjudicacion := cargos_adjudicacion - a_cobrar;
                UPDATE historico_plan_contrato SET "cargosAdjudicacion" = cargos_adjudicacion WHERE id = hpc.id;
            END IF;
            num := num - 1;
        END LOOP;
    PERFORM public.cuadrar_cuentas('{"idContrato":'||con.id||',"lastCuota":'||numero_cuota||'}');
    END IF;
    RETURN '{"correcto":true, "message":"La anulacion del cobro se realizo correctamente"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;

------ ANULACION DE COBRO ------

    CREATE OR REPLACE FUNCTION anulacion_cobro(jsonbpar CHAR VARYING) RETURNS TEXT AS $$
    DECLARE
    id_cobro INTEGER;
    cob public.cobro;
    con public.contrato;
    hpc public.historico_plan_contrato;
    cuo public.cuota;
    cuo22 public.cuota;
    array_detalle_cobros jsonb;

    ----- historico plan contrato -----
    total_cobro_inscripcion NUMERIC;
    valor_pagado_inscripcion NUMERIC;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_cuotas_cobradas NUMERIC;
    total_monto_cobrado NUMERIC;
    total_cobro_primera_cuota NUMERIC;
    cargos_adjudicacion NUMERIC;
    inscripcion_esta_pagada CHAR(1);
    total_dispositivo_cobrado NUMERIC;

    ----- detalle de cobros -----
    numero_cuota INTEGER;
    a_cobrar NUMERIC;
    tipo_cobro CHAR VARYING;
    dispositivo_pagado NUMERIC;
    valor_primera_cuota NUMERIC;
    tasa_administrativa NUMERIC;

    ----- otras variables -----
    monto_pagado_cuota NUMERIC;
    valor_cuota NUMERIC;
    estado_pago_cuota CHAR(1);
    count INTEGER;
    num INTEGER;
    BEGIN
    id_cobro := (jsonbpar::jsonb->>'id')::INTEGER;
    cob := (SELECT co FROM cobro co WHERE id_cobro = id_cobro);
    con := (SELECT c1 FROM cobro INNER JOIN contrato c1 ON c1.id=cobro."idContrato" WHERE cobro.id=jsonbpar.id_cobro);
    hpc := (SELECT h1 FROM historico_plan_contrato h1 WHERE h1."idContrato"=con.id ORDER BY h1.id DESC LIMIT 1);
    array_detalle_cobros := (cob."detalleCobros")::jsonb;

    ----- sacar datos para actualizar del historico de plan contrato -----
    total_cobro_inscripcion := hpc."totalCobroInscripcion";
    valor_pagado_inscripcion := hpc."valorPagadoInscripcion";
    abonos_capital_actual := hpc."abonosCapitalActual";
    saldo_capital := hpc."saldoCapital";
    total_tasa_administrativa_cobrada := hpc."totalTasaAdministrativaCobrada";
    total_cuotas_cobradas := hpc."totalCuotasCobradas";
    total_monto_cobrado := hpc."totalMontoCobrado";
    total_cobro_primera_cuota := hpc."totalCobroPrimeraCuota";
    cargos_adjudicacion := hpc."cargosAdjudicacion";
    inscripcion_esta_pagada := hpc."inscripcionEstaPagada";
    total_dispositivo_cobrado := hpc."totalDispositivoCobrado";

    ----- recorrer detalle de cobros -----
    monto_pagado_cuota := 0;
    count := 0;
    num := jsonb_array_length(array_detalle_cobros) - 1;
    IF(num >= 0) THEN
        WHILE (num >= count)
        LOOP
            ----- sacar valores de array_detalle_cobros -----
            numero_cuota := ((array_detalle_cobros->num)->>'noCuota')::INTEGER;
            a_cobrar := ((array_detalle_cobros->num)->>'aCobrar')::NUMERIC;
            tipo_cobro := ((array_detalle_cobros->num)->>'tipoCobro')::CHAR VARYING;
            ----- actualizar cuotas a devolver -----
            cuo := (SELECT cuo1 FROM cuota cuo1 WHERE cuo1."numeroCuota" = numero_cuota AND cuo1."idHistoricoPlanContrato" = hpc.id);
            IF(tipo_cobro = 'I') THEN
                inscripcion_esta_pagada := 'N';
                total_cobro_inscripcion := total_cobro_inscripcion - a_cobrar;
                valor_pagado_inscripcion := valor_pagado_inscripcion - a_cobrar;
                UPDATE historico_plan_contrato SET 
                "inscripcionEstaPagada" = inscripcion_esta_pagada,
                "totalCobroInscripcion" = total_cobro_inscripcion, 
                "valorPagadoInscripcion" = valor_pagado_inscripcion
                WHERE id = hpc.id;
            END IF;
            IF (tipo_cobro = 'AC') THEN
                monto_pagado_cuota := cuo."valorPagadoCuota" - a_cobrar;
                abonos_capital_actual := abonos_capital_actual - a_cobrar;
                saldo_capital := saldo_capital + a_cobrar;
                UPDATE cuota SET "valorPagadoCuota"= monto_pagado_cuota WHERE id = cuo.id;
            ELSEIF (tipo_cobro = 'CA') THEN
                monto_pagado_cuota := cuo."valorPagadoCuota" - a_cobrar;
                total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada - a_cobrar;
                UPDATE cuota SET "valorPagadoCuota"= monto_pagado_cuota WHERE id = cuo.id;
            END IF;
            IF (tipo_cobro = 'D') THEN
                monto_pagado_cuota := cuo."valorPagadoDispositivo" - a_cobrar;
                UPDATE cuota SET "valorPagadoDispositivo"= monto_pagado_cuota WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'R') THEN
                monto_pagado_cuota := cuo."valorPagadoRastreo" - a_cobrar;
                UPDATE cuota SET "valorPagadoRastreo"= monto_pagado_cuota WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
            END IF;
            IF (tipo_cobro = 'CAD') THEN
                cargos_adjudicacion := cargos_adjudicacion - a_cobrar;
                UPDATE historico_plan_contrato SET "cargosAdjudicacion" = cargos_adjudicacion WHERE id = hpc.id;
            END IF;
            num := num - 1;
        END LOOP;
    PERFORM public.cuadrar_cuentas('{"idContrato":'||con.id||',"lastCuota":'||numero_cuota||'}');
    END IF;
    RETURN '{"correcto":true, "message":"La anulacion del cobro se realizo correctamente"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;

------- CUADRAR CUENTAS  -------

    CREATE OR REPLACE FUNCTION cuadrar_cuentas(jsonbpar CHAR VARYING) RETURNS TEXT AS $$
    DECLARE
    id_contrato INTEGER;
    last_cuota INTEGER;
    cuo public.cuota;
    hpc public.historico_plan_contrato;
    ------ historico plan contrato ------
    id_hpc INTEGER;
    abonos_capital_actual NUMERIC;
    saldo_capital NUMERIC;
    total_tasa_administrativa_cobrada NUMERIC;
    total_cuotas_cobradas NUMERIC;
    total_monto_cobrado NUMERIC;
    total_cobro_primera_cuota NUMERIC;
    total_dispositivo_cobrado INTEGER;

    ------ otras variables ------
    suma_abono_capital NUMERIC;
    suma_tasa_administrativa NUMERIC;
    suma_cuotas NUMERIC;
    numero_cuota_pagos INTEGER;
    sobrante_cuota NUMERIC;
    BEGIN
    id_contrato := (jsonbpar::jsonb->>'idContrato')::INTEGER;
    last_cuota := (jsonbpar::jsonb->>'lastCuota')::INTEGER;
    id_hpc := (SELECT h1.id FROM historico_plan_contrato h1 WHERE h1."idContrato" = id_contrato ORDER BY h1.id DESC LIMIT 1);
    UPDATE cuota SET "estadoPago"='N' 
    WHERE "idHistoricoPlanContrato" = id_hpc 
    AND "valorCuota" > "valorPagadoCuota" 
    AND dispositivo > "valorPagadoDispositivo" 
    AND rastreo > "valorPagadoRastreo";
    UPDATE cuota SET "dispositivoEstaPagado" = 'N' 
    WHERE "idHistoricoPlanContrato" = id_hpc 
    AND dispositivo > "valorPagadoDispositivo";
    UPDATE cuota SET "rastreoEstaPagado" = 'N' 
    WHERE "idHistoricoPlanContrato" = id_hpc
    AND rastreo > "valorPagadoRastreo";
    hpc := (SELECT h1 FROM historico_plan_contrato h1 WHERE h1.id = id_hpc);
    cuo := (SELECT c2 FROM cuota c2 WHERE c2."idHistoricoPlanContrato" = id_hpc AND c2."numeroCuota" = last_cuota);
    suma_cuotas := (SELECT SUM(c1."valorPagadoCuota") FROM cuota c1 WHERE c1."idHistoricoPlanContrato" = id_hpc);
    numero_cuota_pagos := (SELECT floor(suma_cuota / cuo."valorCuota"));
    sobrante_cuota := (suma_cuotas - (numero_cuota_pagos * cuo."valorCuota"));
    IF(sobrante_cuota >= cuo."valorTasaAdministrativa") THEN
        total_tasa_administrativa_cobrada := ((numero_cuota_pagos + 1) * cuo."valorTasaAdministrativa");
        abonos_capital_actual := (numero_cuota_pagos * cuo."abonoCapital") + sobrante_cuota;
    ELSE
        total_tasa_administrativa_cobrada := (numero_cuota_pagos * cuo."valorTasaAdministrativa") + sobrante_cuota;
        abonos_capital_actual := (numero_cuota_pagos * cuo."abonoCapital");
    END IF;
    saldo_capital := hpc."capitalTotal" - abonos_capital_actual;
    total_cuotas_cobradas := numero_cuota_pagos;
    total_monto_cobrado := suma_cuotas;
    total_cobro_primera_cuota := (SELECT c3."valorPagadoCuota" FROM cuota c3 WHERE c3."idHistoricoPlanContrato" = id_hpc AND c3."numeroCuota" = 1);
    total_dispositivo_cobrado := (SELECT count(*) FROM cuota c4 WHERE c4."idHistoricoPlanContrato" = id_hpc AND c4."dispositivoEstaPagado" = 'S');
    UPDATE contrato SET "cuotaACobrar" = last_cuota WHERE id = id_contrato;
    UPDATE historico_plan_contrato SET "abonoCapitalActual" = abonos_capital_actual, "saldoCapital" = saldo_capital, "totalTasaAdministrativaCobrada" = total_tasa_administrativa_cobrada, "totalCuotasCobradas" = total_cuotas_cobradas, "totalMontoCobrado" = total_monto_cobrado, "totalCobroPrimeraCuota" = total_cobro_primera_cuota, "totalDispositivoCobrado" = total_dispositivo_cobrado WHERE id = id_hpc;
    RETURN '{"correcto":true, "message":"El cuadre de cuentas es correcto"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;

----- siguiente
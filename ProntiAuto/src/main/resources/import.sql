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

create or replace function cobros_contrato(jsonpar character varying) returns character varying
    language plpgsql
as
$$
DECLARE
count INTEGER;
	countCobro integer;
    count2 INTEGER;
    count3 INTEGER;
    num INTEGER;
    num2 INTEGER;
	numCobro INTEGER;
    num3 INTEGER;
    id_cobro INTEGER;
    c public.contrato;
	peri public.periodo_contable;
    hpc public.historico_plan_contrato;
    cuo public.cuota;
    monto_pagado_cuota NUMERIC;
    dispositivo_pagado NUMERIC;
    estado_pago_dispositivo CHAR(1);
    rastreo_pagado NUMERIC;
    estado_pago_rastreo CHAR(1);
	w_ivaPorcentaje numeric;

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
    nombreColumna character varying(255);
    w_mesPeriodo character varying(3);
    w_ivaPorcentale numeric(12,2);
	w_tasaCargoAdjudicacion numeric;

    ----- variables para actualizar grupo y fondo contrato -----
    id_grupo INTEGER;

    ----- tabla cobro -----
    valor_a_cobrar NUMERIC;
    id_contrato INTEGER;
    array_pagos jsonb;
    array_detalle_cobros jsonb;
	detalle_descripcion text;

    ---- tabla pagos ----
    tipo_documento CHAR VARYING;
    observaciones CHAR VARYING;
    numero_documento CHAR VARYING;
    valor NUMERIC;
    fecha_deposito DATE;
    detalle_pagos jsonb;
    id_cuenta_bancaria_empresa INTEGER;
	banco_cuenta_bancaria CHAR VARYING;

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
	--a_cobrar := (jsonpar::jsonb->>'aCobrar')::NUMERIC;
    id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
    array_pagos := (jsonpar::jsonb->>'pagos');
    array_detalle_cobros := (jsonpar::jsonb->>'detalleCobros');
    --detalle_cobros := (jsonpar::jsonb->>'descripcion');
	--(SELECT jsonb_pretty(array_detalle_cobros));
    ------- creacion del cobro -------
	--recorre cobros
	countCobro := 0;
	numCobro := jsonb_array_length(array_detalle_cobros);
	--RAISE NOTICE 'descripcion  %' , numCobro ;
	IF (numCobro <> 0) THEN

	-----------WHILE (countCobro < numCobro)
	-----------		LOOP
				detalle_descripcion := ((array_detalle_cobros->countCobro)->>'descripcion');
				--RAISE NOTICE 'descripcion  %' , detalle_descripcion ;
				--REALIZAMOS EL INSERT
INSERT INTO cobro (
    "sisActualizado","sisCreado", "sisHabilitado",
    "valorACobrar", "detalleCobros", "idContrato")
VALUES (
           now(), now(), 'S',
           valor_a_cobrar, detalle_descripcion, id_contrato
       ) RETURNING id INTO id_cobro;

-----		countCobro:=countCobro+1;
------		end loop;


end if;



count := 0;
    num := jsonb_array_length(array_pagos);
	--select * from contrato
	---### sacar valores del contrato para generar asientos contables
	   c := (SELECT c1 FROM contrato c1 WHERE c1.id = id_contrato);
	   RAISE NOTICE 'DDDDDDDDDD  %' , c."nombresCliente" ;
       peri := (SELECT p FROM periodo_contable p where p."esPeriodoActual"='A');
begin
select "ivaPorcentaje" ,"tasaCargoAdjudicacion"
into w_ivaPorcentaje, w_tasaCargoAdjudicacion
from configuracion_general;
end;
	---###

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
			banco_cuenta_bancaria := ((array_pagos->count)->>'bancoCuentaBancaria');
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
    "valor",
    "oferta", "cargoAdjudicacion", "numeroCuota",
    "tipo", "idItemCobroPago", "idPago"
) VALUES (
             now(), now(), 'S',
             valor_detalle,
             oferta, cargo_adjudicacion, numero_cuota_pagos,
             tipo, id_item_cobro_pago, id_pago
         );
count2 := count2 + 1;

					raise notice  'value of valor : %', valor_detalle;
					raise notice  'value of tipo : %', tipo;
END LOOP;
END IF;
---###### Generacion de asientos contables
 	perform public.asiento_cobros(id_contrato, id_cobro, c."estado" , peri.id, peri."anio", peri."fechaInicio",
				      c."nombresCliente"::text, c."cuotaACobrar", fecha_deposito, id_pago, w_ivaPorcentaje::numeric(12,2), banco_cuenta_bancaria);

count:=count+1;
END LOOP;
END IF;

    ----- sacar datos de tablas relacionadas -----
    c := (SELECT c1 FROM contrato c1 WHERE c1.id = id_contrato);
    hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE hpc1."idContrato" = c."id" ORDER BY hpc1."sisCreado" DESC LIMIT 1);

    ----- sacar datos para actualizar del historico de plan contrato -----
    total_cobro_inscripcion := hpc."totalCobroInscripcion";
    valor_pagado_inscripcion := hpc."valorPagadoInscripcion";
    abonos_capital_actual := hpc."abonosCapitalActual";
    saldo_capital := hpc."saldoCapital";
    total_tasa_administrativa_cobrada := hpc."totalTasaAdministrativaCobrada";
    total_cuotas_cobradas := hpc."totalCuotasCobradas";
    total_monto_cobrado := coalesce(hpc."totalMontoCobrado",0);
    total_cobro_primera_cuota := coalesce(hpc."totalCobroPrimeraCuota",0);
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
            valor_cuota := coalesce(cuo."valorCuota",0) + coalesce(cuo.dispositivo,0) + coalesce(cuo.rastreo,0);
            estado_pago_dispositivo := 'N';
            estado_pago_rastreo := 'N';
			raise notice  'value of valor_cuota : %', valor_cuota;
			raise notice  'value of valor_a_pagar_inscripcion : %', valor_a_pagar_inscripcion;
			raise notice  'value of a_cobrar : %', a_cobrar;

			raise notice  'value of cuo."valorPagadoCuota ORIGINAL" : %', cuo."valorPagadoCuota";
            IF (tipo_cobro = 'I') THEN
                inscripcion_esta_pagada := CASE WHEN a_cobrar = valor_a_pagar_inscripcion THEN 'S' ELSE 'N' END;
                total_cobro_inscripcion := total_cobro_inscripcion + a_cobrar;
                valor_pagado_inscripcion := valor_pagado_inscripcion + a_cobrar;
			--	RETURN '{"correcto":false, "message":" entro I"}',total_cobro_inscripcion::text;
                IF(inscripcion_esta_pagada = 'S')THEN
UPDATE contrato SET "cuotaACobrar" = numero_cuota + 1 WHERE id = id_contrato;
END IF;
END IF;

            IF (tipo_cobro = 'CA') THEN
                monto_pagado_cuota := round((coalesce(monto_pagado_cuota,0) + coalesce(a_cobrar,0)),2);
                total_tasa_administrativa_cobrada := coalesce(total_tasa_administrativa_cobrada,0) + coalesce(a_cobrar,0);
				raise notice  'value of monto_pagado_cuota : %', monto_pagado_cuota;
				raise notice  'value of cuo."valorPagadoCuota CA" : %', cuo."valorPagadoCuota";
				 raise notice  'value of a_cobrar : %', a_cobrar;
				-- RETURN '{"correcto":false, "message":" entro CA"}',monto_pagado_cuota::text;

UPDATE cuota SET "valorPagadoCuota"= (cuo."valorPagadoCuota" + a_cobrar) WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
END IF;


			IF (tipo_cobro = 'AC') THEN
                monto_pagado_cuota := round((coalesce(monto_pagado_cuota,0) + coalesce(a_cobrar,0)),2);
                abonos_capital_actual := coalesce(abonos_capital_actual,0) + coalesce(a_cobrar,0);
                saldo_capital := coalesce(saldo_capital,0) - coalesce(a_cobrar,0);
				raise notice  'value of monto_pagado_cuota : %', monto_pagado_cuota;
				raise notice  'value of cuo."valorPagadoCuota AC" : %', cuo."valorPagadoCuota";
				 raise notice  'value of a_cobrar : %', a_cobrar;
				-- RETURN '{"correcto":false, "message":" entro AC"}',monto_pagado_cuota::text;
UPDATE cuota SET "valorPagadoCuota"= (cuo."valorPagadoCuota" + a_cobrar) WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
END IF;

            IF (tipo_cobro = 'D') THEN
                monto_pagado_cuota := coalesce(monto_pagado_cuota,0) + coalesce(a_cobrar,0);
                monto_pagado_dispositivo := coalesce(cuo."valorPagadoDispositivo",0) + coalesce(a_cobrar,0);
				raise notice  'value of monto_pagado_cuota : %', monto_pagado_cuota;
				-- RETURN '{"correcto":false, "message":" entro D"}',monto_pagado_cuota::text;
                IF(monto_pagado_dispositivo = cuo.dispositivo) THEN
                    total_dispositivo_cobrado := coalesce(total_dispositivo_cobrado,0) + 1;
                    estado_pago_dispositivo := 'S';
                    monto_pagado_dispositivo := 0;
END IF;
UPDATE cuota SET "dispositivoEstaPagado" = estado_pago_dispositivo, "valorPagadoDispositivo"= monto_pagado_dispositivo WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
END IF;
            IF (tipo_cobro = 'R') THEN
                monto_pagado_cuota := coalesce(monto_pagado_cuota,0) + coalesce(a_cobrar,0);
                monto_pagado_rastreo := coalesce(cuo."valorPagadoRastreo",0) + coalesce(a_cobrar,0);
				raise notice  'value of monto_pagado_cuota : %', monto_pagado_cuota;
				-- RETURN '{"correcto":false, "message":" entro R"}',monto_pagado_cuota::text;
                IF(monto_pagado_rastreo = cuo.rastreo) THEN
                    estado_pago_rastreo := 'S';
                    monto_pagado_rastreo := 0;
END IF;
UPDATE cuota SET "rastreoEstaPagado" = estado_pago_rastreo, "valorPagadoDispositivo"= monto_pagado_rastreo WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
END IF;
            IF (tipo_cobro = 'CAD') THEN
                cargos_adjudicacion := cargos_adjudicacion + a_cobrar;
              --  UPDATE historico_plan_contrato SET "cargosAdjudicacion" = cargos_adjudicacion WHERE id = hpc.id;
				IF (cargos_adjudicacion >= ((hpc."capitalTotal" * w_tasaCargoAdjudicacion)/100)) then
update contrato set estado_contrato='PAB' where id=id_contrato; ---  estado preadjudicado buscando
END IF;
			-- EJECUTAMOS EL ASIENTO POR COBRO DE ADJUDICACION
				perform public.asiento_cobros_externo(id_contrato, id_cobro, c.estado , peri.id, peri."anio", peri."fechaInicio",
				      c."nombresCliente", numero_cuota, "fechaDeposito", id_pago, w_ivaPorcentaje, a_cobrar, tipo_cobro, banco_cuenta_bancaria);


END IF;
			raise notice  'value of monto_pagado_cuota : %', monto_pagado_cuota;
			raise notice  'value of valor_cuota : %', valor_cuota;
			raise notice  'value of numero_cuota : %', numero_cuota;
			raise notice  'value of hpc."valorDsctoPrimeraCuota" : %', hpc."valorDsctoPrimeraCuota";
			raise notice  'value of total_monto_cobrado : %', total_monto_cobrado;
            IF (monto_pagado_cuota = COALESCE(valor_cuota,0) AND numero_cuota <> 1) THEN


UPDATE cuota SET "estaPagado" = 'S' WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
UPDATE contrato SET "cuotaACobrar" = coalesce(numero_cuota,0) + 1 WHERE id = id_contrato;
raise notice  'value of id_contrato : %', id_contrato;
				if hpc."numCuotasAbajoHaciaArriba"=0 then
				 total_cuotas_cobradas := coalesce(numero_cuota,0);
else
				  total_cuotas_cobradas := coalesce(total_cuotas_cobradas,0);
end if;

                cuota_a_cobrar := coalesce(numero_cuota,0) + 1;
                total_monto_cobrado := coalesce(total_monto_cobrado,0) + coalesce(monto_pagado_cuota,0);
                monto_pagado_cuota := 0;
				raise notice  'value of total_monto_cobrado : %', total_monto_cobrado;
				raise notice  'value of total_cuotas_cobradas : %', total_cuotas_cobradas;
				raise notice  'value of valor_cuota : %', valor_cuota;
				raise notice  'value of numero_cuota : %', numero_cuota;
				raise notice  'value of cuota_a_cobrar : %', cuota_a_cobrar;
			--	RETURN '{"correcto":false, "message":" entro 1"}',monto_pagado_cuota::text ;
           ELSEIF (coalesce(monto_pagado_cuota,0) < (valor_cuota - coalesce(hpc."valorDsctoPrimeraCuota",0)) AND numero_cuota = 1) THEN
			-- RETURN '{"correcto":false, "message":" entro 2"}',monto_pagado_cuota::text ;

                total_cobro_primera_cuota := coalesce(total_cobro_primera_cuota,0) + coalesce(monto_pagado_cuota,0);
				total_monto_cobrado := coalesce(total_monto_cobrado,0) + coalesce(total_cobro_primera_cuota,0);
				if hpc."numCuotasAbajoHaciaArriba"=0 then
				 total_cuotas_cobradas := coalesce(numero_cuota,0);
else
				  total_cuotas_cobradas := coalesce(total_cuotas_cobradas,0);
end if;

            ELSEIF (coalesce(monto_pagado_cuota,0) = (c."cuotaActual" - coalesce(hpc."valorDsctoPrimeraCuota",0)) AND numero_cuota = 1) THEN
		  		raise notice  'value of monto_pagado_cuota : %', monto_pagado_cuota;
				raise notice  'value of total_cuotas_cobradas : %', total_cuotas_cobradas;
				raise notice  'value of valor_cuota : %', c."cuotaActual" ;
				raise notice  'value of RESTA : %', (c."cuotaActual"  - coalesce(hpc."valorDsctoPrimeraCuota",0));
			  --RETURN '{"correcto":false, "message":" entro 3"}',monto_pagado_cuota::text ;

UPDATE cuota SET "estaPagado" = 'S' WHERE "numeroCuota" = numero_cuota AND "idHistoricoPlanContrato" = hpc.id;
UPDATE contrato SET "cuotaACobrar" = coalesce(numero_cuota,0) + 1 WHERE id = id_contrato;
total_cobro_primera_cuota := coalesce(monto_pagado_cuota,0);
				if hpc."numCuotasAbajoHaciaArriba"=0 then
				total_monto_cobrado :=  coalesce(total_cobro_primera_cuota,0); --- - coalesce(hpc."valorTasaAdministrativa",0);
				total_cuotas_cobradas := coalesce(numero_cuota,0);
else
				total_monto_cobrado := coalesce(total_monto_cobrado,0) + coalesce(total_cobro_primera_cuota,0)
										- coalesce(hpc."valorTasaAdministrativa",0) - round((coalesce(hpc."valorTasaAdministrativa",0)* (w_ivaPorcentaje/100)),2);
				 total_cuotas_cobradas := coalesce(total_cuotas_cobradas,0) + 1;
end if;
				--total_monto_cobrado := coalesce(total_monto_cobrado,0) + coalesce(total_cobro_primera_cuota,0);
                 monto_pagado_cuota := 0;

				/* ELSE  borrar despues WH
				 raise notice  'value of monto_pagado_cuota : %', monto_pagado_cuota;
				raise notice  'value of total_cuotas_cobradas : %', total_cuotas_cobradas;
				raise notice  'value of valor_cuota : %', valor_cuota;
				raise notice  'value of RESTA : %', (valor_cuota - coalesce(hpc."valorDsctoPrimeraCuota",0));
			  RETURN '{"correcto":false, "message":" entro 3"}',monto_pagado_cuota::text ;
				 */
END IF;

            ----- actualizar historico plan contrato -----
UPDATE historico_plan_contrato
SET
    "valorPagadoInscripcion" = valor_pagado_inscripcion,
    "abonosCapitalActual" = abonos_capital_actual,
    "saldoCapital" = saldo_capital,
    "totalTasaAdministrativaCobrada" = total_tasa_administrativa_cobrada,
    "totalCuotasCobradas" = total_cuotas_cobradas,
    "totalMontoCobrado" = total_monto_cobrado,
    "totalCobroPrimeraCuota" = total_cobro_primera_cuota,
    "cargosAdjudicacion" = cargos_adjudicacion,
    "inscripcionEstaPagada" = inscripcion_esta_pagada,
    "totalDispositivoCobrado" = total_dispositivo_cobrado
WHERE id = hpc.id;

count3 := count3 + 1;
END LOOP;
        ----##### Registro de factura
        perform public.registrar_factura(id_pago, id_contrato, jsonpar);
END IF;

    ------- actializar fondo del contrato -------
  ---  PERFORM actualizar_fondo_contrato(id_contrato);

    ------- actualizar monto meta del grupo -------
    id_grupo := (SELECT cg."idGrupo" FROM cliente_en_grupo cg INNER JOIN contrato c1 ON c1."idClienteEnGrupo" = cg."id" WHERE c1.id = id_contrato);
 ---   PERFORM actualizar_monto_meta(id_grupo);

RETURN '{"correcto":true, "message":"El cobro se realizo correctamente"}';
ROLLBACK;
END;

$$;

------ ACTUALIZA SALDO COBROS---
create or replace function actualiza_saldo_ctacble(w_cuentacontable integer, nombrecolumna text, valorcreditodebito numeric) returns void
    language plpgsql
as
$$
declare
target text;
begin
execute 'update public.cuenta_contable set '
    || nombreColumna
    || ' = '
    || nombreColumna
    || '+'
    || valorCreditoDebito
    || ' where id ='
    || w_CuentaContable;

end
$$;

------ ASIENTO COBROS ---------

create or replace function asiento_cobros(id_contrato integer, id_cobro integer, estado_contrato character varying, idperiodo integer, anio integer, fechainicio date, nombrescliente character varying, cuotaacobrar integer, fecha_deposito date, id_pago integer, w_ivaporcentaje numeric, banco_cuenta_bancaria character varying) returns void
    language plpgsql
as
$$
declare
w_mesPeriodo character varying(3);
nombreColumna character varying(255);
w_valorInscri numeric(12,2);
w_valorFactu numeric(12,2);
w_valorAC numeric(12,2);
w_valorCA numeric(12,2);
w_valorDIS numeric(12,2);
w_idItemCobroPagoDIS integer;
w_valorRAS numeric(12,2);
w_idItemCobroPagoRAS integer;
w_idItemCobroPago integer;
w_idItemCobroPagoAC integer;
w_idItemCobroPagoCA integer;
w_codigoCuentaContableIns character varying(30);
w_codigoCuentaContableNAA character varying(30);
w_sw_ca integer;
w_tipo character varying(2);

begin
----
--###  asientos
----
w_mesPeriodo :=to_char(fechaInicio,'MON') ;
set lc_time to 'es_CL';
nombreColumna := '"'||obtener_mes(to_char(fechaInicio, 'MM' )::INTEGER);

 if estado_contrato ='REG' then

begin
select valor, tipo into w_valorInscri, w_tipo from detalle_pago
where tipo='I' and "idPago"=id_pago;
end;
			if w_valorInscri > 0 then
begin
select "codigoCuentaContable" into w_codigoCuentaContableIns
from item_cobro_pago
where "nombre_cuenta"='INGRESO DE INSCRIPCION AUTOS';
end;
				perform asiento_cobrosreg(id_contrato, id_cobro, estado_contrato, idperiodo, anio, fechaInicio,
								  nombrescliente,cuotaacobrar, fecha_deposito, id_pago, nombreColumna, w_mesPeriodo,
								w_ivaporcentaje, w_valorInscri, w_idItemCobroPago, w_codigoCuentaContableIns,
								banco_cuenta_bancaria, w_valorAC, w_valorCA, w_tipo);
end if;
begin
select sum(valor) into w_valorFactu from detalle_pago
where tipo in ('AC','CA') and  "idPago"=id_pago;
end;
			if w_valorFactu > 0 then
begin
select "codigoCuentaContable" into w_codigoCuentaContableIns
from item_cobro_pago
where "nombre_cuenta"='INGRESOS POR ADMINISTRACION AUTOS';
end;

				-- Abono capital
				w_valorAC :=0;
begin
select valor, "idItemCobroPago" into w_valorAC, w_idItemCobroPagoAC from detalle_pago
where tipo='AC' and "idPago"=id_pago;
end;
				-- Cuota Administrativa
				w_valorCA:=0;
begin
select valor, "idItemCobroPago" into w_valorCA, w_idItemCobroPagoCA from detalle_pago
where tipo='CA' and "idPago"=id_pago;
end;

				w_tipo :='CA';
				perform asiento_cobrosreg(id_contrato, id_cobro, estado_contrato, idperiodo, anio, fechaInicio,
								  nombrescliente,cuotaacobrar, fecha_deposito, id_pago, nombreColumna, w_mesPeriodo,
								w_ivaporcentaje, w_valorFactu, w_idItemCobroPago, w_codigoCuentaContableIns,
								banco_cuenta_bancaria, w_valorAC, w_valorCA, w_tipo  );
end if;
			--  aqui se cambia el estado del contrato 'EN PROCESO'
begin
update contrato set "estado"='EPR' where "id"=id_contrato;
end;

else

			-- Abono capital
			w_valorAC :=0;
begin
select sum(valor), max("idItemCobroPago") into w_valorAC, w_idItemCobroPagoAC from detalle_pago
where tipo='AC' and "idPago"=id_pago;
end;
			-- Cuota Administrativa
			w_valorCA:=0;
begin
select sum(valor), max("idItemCobroPago") into w_valorCA, w_idItemCobroPagoCA from detalle_pago
where tipo='CA' and "idPago"=id_pago;
end;
			-- Dispositivo
			w_valorDIS :=0;
begin
select sum(valor), max("idItemCobroPago") into w_valorDIS, w_idItemCobroPagoDIS from detalle_pago
where tipo='D' and "idPago"=id_pago;
end;
			-- Rastreo
			w_valorRAS:=0;
begin
select sum(valor), max("idItemCobroPago") into w_valorRAS, w_idItemCobroPagoRAS from detalle_pago
where tipo='R' and "idPago"=id_pago;
end;

			perform asiento_cobrosadj(id_contrato, id_cobro, estado_contrato, idperiodo, anio, fechainicio,
						      nombrescliente, cuotaacobrar, fecha_deposito, id_pago, nombreColumna, w_mesPeriodo,
							w_ivaporcentaje, w_valorAC, w_idItemCobroPagoAC, w_valorCA, w_idItemCobroPagoCA,
							w_valorDIS, w_idItemCobroPagoDIS, w_valorRAS, w_idItemCobroPagoRAS, banco_cuenta_bancaria );



end if;

return;
END;
$$;
------ ASIENTO COBROS ADJ ---

create or replace function asiento_cobrosadj(id_contrato integer, id_cobro integer, estado_contrato character varying, w_idperiodo integer, w_anio integer, w_fechainicio date, w_nombrescliente character varying, w_cuotaacobrar integer, w_fechadeposito date, id_pago integer, nombrecolumna character varying, w_mesperiodo character varying, w_ivaporcentaje numeric, w_valorac numeric, w_iditemcobropago integer, w_valorca numeric, w_iditemcobropagoca integer, w_valordis numeric, w_iditemcobropagodis integer, w_valorras numeric, w_iditemcobropagoras integer, banco_cuenta_bancaria character varying) returns void
    language plpgsql
as
$$
declare
w_cont integer;
id_AsientoContableCabecera integer;
w_idItemCobroPago integer;
w_cuentaContable integer;
w_valorTasaAdministrativa numeric(12,2);
w_valorTasaAdministrativaRAS numeric(12,2);
w_valorTasaAdministrativaDIS numeric(12,2);

w_idCuentaContable integer;
w_idCuentaContableCxC integer;
w_idCuentaContableIva integer;
w_idCuentaContableInscri integer;
w_idCuentaContableBco  integer;
w_idCuentaContableIAA integer;
w_idCuentaContableSSD  integer;
w_idCuentaContableSSR  integer;
w_nombreCuentaContable character varying(255);
w_actualSaldo numeric(12,2);
w_valor_iva numeric(12,2);
w_valor_ivaRAS numeric(12,2);
w_valor_ivaDIS numeric(12,2);
w_diferencia numeric(12,2);
id_transaccionAsientoContable integer;
w_TipoTransaccion character varying(1);

begin
----
--###  asiento factura inscripcion
----
w_valorTasaAdministrativa:= w_valorca/(1+(w_ivaPorcentaje/100));
w_valor_iva := (w_valorTasaAdministrativa*w_ivaPorcentaje)/100;

if estado_contrato = ('ADJ') THEN
w_valorTasaAdministrativaRAS:= w_valorRAS/(1+(w_ivaPorcentaje/100));
w_valor_ivaRAS := (w_valorTasaAdministrativaRAS*w_ivaPorcentaje)/100;

w_valorTasaAdministrativaDIS:= w_valorDIS/(1+(w_ivaPorcentaje/100));
w_valor_ivaDIS := (w_valorTasaAdministrativaDIS*w_ivaPorcentaje)/100;
else
w_valorRAS:=0;
w_valorDIS:=0;
w_valor_ivaRAS :=0;
w_valor_ivaDIS :=0;
w_valorTasaAdministrativaDIS:=0;
w_valorTasaAdministrativaRAS:=0;

end if;

w_cont := 1;
w_TipoTransaccion:='D';

begin
select id,  "actualSaldo"
into w_idCuentaContableCxC , w_actualSaldo  ----   como se afecta el actualsaldo,  a que cuenta esta relacionado
from cuenta_contable
where 	"idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable" in (
        select "codigoCuentaContable"
        from item_cobro_pago
        where "nombre_cuenta"='CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES');
end;

begin
select id
into w_idCuentaContableIva
from cuenta_contable
where 	"idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable" in (
        select "codigoCuentaContable"
        from item_cobro_pago
        where "nombre_cuenta"='IVA POR PAGAR');
end;

begin
select id
into w_idCuentaContableIAA
from cuenta_contable
where 	"idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable" in (
        select "codigoCuentaContable"
        from item_cobro_pago
        where "nombre_cuenta"='INGRESOS POR ADMINISTRACION AUTOS');
end;


WHILE (w_cont  <= 2) LOOP
-- cabecera del asiento contable por facuracion
begin
INSERT INTO public.asiento_contable_cabecera(
    "sisActualizado", "sisCreado", "sisHabilitado", anio, "asientoCerrado", beneficiario,
    descripcion, fecha, "mesPeriodo",  "tipoAsientoContable", "tipoTransaccion",
    "totalCredito", "totalDebito", "totalSaldoActualFecha","idCuentaContable")
VALUES (
           now(),now(), 'A', w_anio,'N', w_nombresCliente ,
           'Pago cuota Nro.'||w_cuotaACobrar, w_fechaDeposito, w_mesPeriodo, 'E',w_TipoTransaccion,
           0,0,0,w_idCuentaContableCxC) ---cuenta contable CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES
    RETURNING id INTO id_AsientoContableCabecera;
end;

if w_cont =1 then

	--detalle del asiento contable por facuracion
	if estado_contrato = ('EPR') THEN
	 if ( w_valorCA > 0) then

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES',
        0, (w_valorCA ),  id_AsientoContableCabecera, w_idCuentaContableCxC )   --- 2 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
		perform public.actualiza_saldo_ctacble(	w_idCuentaContableCxC, nombreColumna||'Debito"', (coalesce(w_valorCA,0) ));
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'IVA POR PAGAR',
        (w_valor_iva), 0, id_AsientoContableCabecera, w_idCuentaContableIva)  --- 3 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
		perform public.actualiza_saldo_ctacble(	w_idCuentaContableIva, nombreColumna||'Credito"', coalesce(w_valor_iva,0));
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'INGRESOS POR ADMINISTRACION AUTOS',
        w_valorTasaAdministrativa, 0, id_AsientoContableCabecera,w_idCuentaContableIAA )   --- 3 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
		perform public.actualiza_saldo_ctacble(w_idCuentaContableIAA, nombreColumna||'Credito"', coalesce(w_valorTasaAdministrativa,0));
end;
end if;


begin
update public.asiento_contable_cabecera
set 	"totalDebito"= (w_valorCA ),
       "totalCredito" = (w_valor_iva + w_valorTasaAdministrativa ),
       "totalSaldoActualFecha" = w_actualSaldo, ----- esto preguntar
       "codigoReferencialAsientoContable"='000'||id_AsientoContableCabecera  --- esto tambien preguntar
where id = id_AsientoContableCabecera;
end;


		elsif estado_contrato = ('ADJ') THEN
			if ( w_valorCA > 0)  or  (w_valorDIS > 0)  or (w_valorRAS > 0) then
		-------------------
begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES',
        0, (w_valorCA + w_valorRAS + w_valorDIS),  id_AsientoContableCabecera, w_idCuentaContableCxC )   --- 2 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
				perform public.actualiza_saldo_ctacble(	w_idCuentaContableCxC, nombreColumna||'Debito"', (coalesce(w_valorCA,0) + coalesce(w_valorRAS,0) + coalesce(w_valorDIS,0)));
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'IVA POR PAGAR',
        (w_valor_iva+w_valor_ivaRAS+w_valor_ivaDIS), 0, id_AsientoContableCabecera, w_idCuentaContableIva)  --- 3 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
				perform public.actualiza_saldo_ctacble(	w_idCuentaContableIva, nombreColumna||'Credito"', (coalesce(w_valor_iva,0)+ coalesce(w_valor_ivaRAS,0)+coalesce(w_valor_ivaDIS,0)));
end;

				if w_valorCA > 0 then ----
begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'INGRESOS POR ADMINISTRACION AUTOS',
        w_valorTasaAdministrativa, 0, id_AsientoContableCabecera,w_idCuentaContableIAA )   --- 3 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
				perform public.actualiza_saldo_ctacble(w_idCuentaContableIAA, nombreColumna||'Credito"', coalesce(w_valorTasaAdministrativa,0));
end;
end if;
		----------------
				if w_valorDIS > 0 then ---  si existe Dispositivo
begin
select id
into w_idCuentaContableSSD
from cuenta_contable
where 	"idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable" in (
        select "codigoCuentaContable"
        from item_cobro_pago
        where "nombre_cuenta"='SERVICIO DE SEGURIDAD INTERNO DISPOSITIVO');
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'SERVICIO DE SEGURIDAD INTERNO DISPOSITIVO',
        w_valorTasaAdministrativaDIS, 0, id_AsientoContableCabecera, w_idCuentaContableSSD )   --- 6 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
					perform public.actualiza_saldo_ctacble(	w_idCuentaContableSSD, nombreColumna||'Credito"', coalesce(w_valorTasaAdministrativaDIS,0));
end;
end if;

				if w_valorRAS > 0 then ---  si existe Rastreo
begin
select id
into w_idCuentaContableSSR
from cuenta_contable
where 	"idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable" in (
        select "codigoCuentaContable"
        from item_cobro_pago
        where "nombre_cuenta"='SISTEMA DE SEGURIDAD INTERNO MONITOREO');
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'SISTEMA DE SEGURIDAD INTERNO MONITOREO',
        w_valorTasaAdministrativaRAS, 0, id_AsientoContableCabecera,w_idCuentaContableSSR)   --- 7 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
						perform public.actualiza_saldo_ctacble(w_idCuentaContableSSR, nombreColumna||'Credito"', coalesce(w_valorTasaAdministrativaRAS,0));
end;
end if;
				----------------
				-----------------


begin
update public.asiento_contable_cabecera
set 	"totalDebito"= (w_valorCA  + w_valorAC+ w_valorRAS + w_valorDIS),
       "totalCredito" = (w_valorCA + w_valorAC + w_valorRAS + w_valorDIS),
       "totalSaldoActualFecha" = w_actualSaldo, ----- esto preguntar
       "codigoReferencialAsientoContable"='000'||id_AsientoContableCabecera  --- esto tambien preguntar
where id = id_AsientoContableCabecera;
end;
end if;
end if;

else

		--detalle del asiento contable cuota
begin
select id
into w_idCuentaContableBco         ----   como se afecta el actualsaldo,  a que cuenta esta relacionado
from cuenta_contable
where 	"idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable" in (
        select "codigoCuentaContable"
        from item_cobro_pago
        where lower("nombre_cuenta") like concat('%', banco_cuenta_bancaria, '%'));
end;


begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'BANCO '||banco_cuenta_bancaria,
        0, (w_valorCA +w_valorAC + w_valorRAS + w_valorDIS), id_AsientoContableCabecera, w_idCuentaContableBco)
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
		perform public.actualiza_saldo_ctacble(	w_idCuentaContableBco, nombreColumna||'Debito"', (coalesce(w_valorAC,0)+ coalesce(w_valorCA,0)));
end;

		if ( w_valorCA > 0)  or  (w_valorDIS > 0)  or (w_valorRAS > 0) then
begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES',
        (w_valorCA + w_valorRAS + w_valorDIS ), 0,  id_AsientoContableCabecera, w_idCuentaContableCxC)   --- 2 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;


begin
			perform public.actualiza_saldo_ctacble(	w_idCuentaContableCxC, nombreColumna||'Credito"', (coalesce(w_valorCA,0)+ coalesce(w_valorRAS,0) + coalesce(w_valorDIS,0) ));
end;
end if;
-----------------------
-----------------------
		if estado_contrato = ('EPR') THEN


begin
select id, nombre
into w_idCuentaContable, w_nombreCuentaContable  ----   como se afecta el actualsaldo,  a que cuenta esta relacionado
from cuenta_contable
where "codigoCuentaContable"
          in (select "codigoCuentaContable"
              from item_cobro_pago
              where nombre_cuenta='NO ADJUDICADO AUTOS'); -- este id saco del select anterior
end;

		elsif estado_contrato='ADJ' then

begin
select id, nombre
into w_idCuentaContable, w_nombreCuentaContable  ----   como se afecta el actualsaldo,  a que cuenta esta relacionado
from cuenta_contable
where "codigoCuentaContable"
          in (select "codigoCuentaContable"
              from item_cobro_pago
              where nombre_cuenta='ADJUDICADO AUTOS');
end;

end if;
begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A',w_nombreCuentaContable,
        w_valorAC, 0, id_AsientoContableCabecera, w_idCuentaContable) --w_idCuentaContable
    RETURNING id INTO id_transaccionAsientoContable;
end;
begin
			perform public.actualiza_saldo_ctacble(	w_idCuentaContable, nombreColumna||'Credito"', coalesce(w_valorAC,0));
end;
				--- actualizamos la cabecera del asiento
begin
update public.asiento_contable_cabecera
set 	"totalDebito"=(w_valorCA +coalesce(w_valorAC,0) + w_valorRAS + w_valorDIS),
       "totalCredito" = (w_valorCA +coalesce(w_valorAC,0) + w_valorRAS + w_valorDIS),
       "totalSaldoActualFecha" = w_actualSaldo, ----- esto preguntar
       "codigoReferencialAsientoContable"='000'||id_AsientoContableCabecera  --- esto tambien preguntar
where id = id_AsientoContableCabecera;
end;

end if;

w_cont := w_cont +1;
w_TipoTransaccion := 'I';
end loop;

return;
END;
$$;
----- ASIENTO COBRO REG ----

create or replace function asiento_cobrosreg(id_contrato integer, id_cobro integer, estado_contrato character varying, w_idperiodo integer, w_anio integer, w_fechainicio date, w_nombrescliente character varying, w_cuotaacobrar integer, w_fechadeposito date, id_pago integer, nombrecolumna character varying, w_mesperiodo character varying, w_ivaporcentaje numeric, w_valor numeric, w_iditemcobropago integer, w_codigocuentacontableins character varying, banco_cuenta_bancaria character varying, w_valorac numeric, w_valorca numeric, w_tipo character varying) returns void
    language plpgsql
as
$$
declare
w_cont integer;
id_AsientoContableCabecera integer;

w_idCuentaContable integer;
w_idCuentaContableCxC integer;
w_idCuentaContableIva integer;
w_idCuentaContableInscri integer;
w_idCuentaContableBco  integer;
w_idCuentaContableNoAdjudi integer;
w_nombreCuentaContable character varying(255);

w_actualSaldo numeric;
w_valor_iva numeric;
w_diferencia numeric;
id_transaccionAsientoContable integer;
w_TipoTransaccion character varying(1);

begin
---- :=
--###  asiento factura inscripcion
if w_valorCA > 0 then
w_valor := w_valorCA;
end if;
w_valor_iva := (w_valor /(1+ (w_ivaPorcentaje/100)))*(w_ivaPorcentaje/100);
w_diferencia:= w_valor - w_valor_iva;

w_cont := 1;
w_TipoTransaccion:='D';

begin
select id,  "actualSaldo"
into w_idCuentaContableCxC , w_actualSaldo  ----   como se afecta el actualsaldo,  a que cuenta esta relacionado
from cuenta_contable
where 	"idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable" in (
        select "codigoCuentaContable"
        from item_cobro_pago
        where "nombre_cuenta"='CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES');


end;

WHILE (w_cont  <= 2) LOOP
-- cabecera del asiento contable por facuracion

begin
INSERT INTO public.asiento_contable_cabecera(
    "sisActualizado", "sisCreado", "sisHabilitado", anio, "asientoCerrado", beneficiario,
    descripcion, fecha, "mesPeriodo",  "tipoAsientoContable", "tipoTransaccion",
    "totalCredito", "totalDebito", "totalSaldoActualFecha","idCuentaContable")
VALUES (
           now(),now(), 'A', w_anio,'N', w_nombresCliente ,
           'Pago cuota Nro.'||w_cuotaACobrar, w_fechaDeposito, w_mesPeriodo, 'E',w_TipoTransaccion,
           0,0,0, w_idCuentaContableCxC)
    RETURNING id INTO id_AsientoContableCabecera;
end;

if w_cont =1 then
  if ((w_tipo ='I') or (w_valorCA > 0)) then
--detalle del asiento contable por facturacion
begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES',
        0, w_valor,  id_AsientoContableCabecera, w_idCuentaContableCxC)   --- 2 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;
begin
	perform public.actualiza_saldo_ctacble(	w_idCuentaContableCxC, nombreColumna||'Debito"', w_valor);
end;

begin
select id
into w_idCuentaContableIva ----   como se afecta el actualsaldo,  a que cuenta esta relacionado
from cuenta_contable
where 	"idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable" in (
        select "codigoCuentaContable"
        from item_cobro_pago
        where "nombre_cuenta"='IVA POR PAGAR');
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'IVA POR PAGAR',
        w_valor_iva, 0, id_AsientoContableCabecera, w_idCuentaContableIva)   --- 3 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
	perform public.actualiza_saldo_ctacble(	w_idCuentaContableIva, nombreColumna||'Credito"', w_valor_iva);
end;

---recuperamos datos de la cuenta contable

begin
select id, nombre
into w_idCuentaContableInscri, w_nombreCuentaContable ----   como se afecta el actualsaldo,  a que cuenta esta relacionado
from cuenta_contable
where 	"idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable" = w_codigoCuentaContableIns;
end;
	raise notice  '###########';
	raise notice  'w_idperiodo : %', w_idperiodo;
	raise notice  'w_codigoCuentaContableIns : %', w_codigoCuentaContableIns;
	raise notice  'w_idCuentaContableInscri : %', w_idCuentaContableInscri;
	raise notice  '###########';

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A',w_nombreCuentaContable,
        w_diferencia, 0, id_AsientoContableCabecera, w_idCuentaContableInscri)
    RETURNING id INTO id_transaccionAsientoContable;
end;
begin
	perform public.actualiza_saldo_ctacble(	w_idCuentaContableInscri, nombreColumna||'Debito"', w_diferencia);
end;
end if; --- solo si el valorCA es mayor a cero
else

begin
select id
into w_idCuentaContableBco         ----   como se afecta el actualsaldo,  a que cuenta esta relacionado
from cuenta_contable
where 	"idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable" in (
        select "codigoCuentaContable"
        from item_cobro_pago
        where lower("nombre_cuenta") like concat('%', banco_cuenta_bancaria, '%'));

RAISE NOTICE 'Banco %', banco_cuenta_bancaria;
	RAISE NOTICE 'Cta Ctble Banco %', w_idCuentaContableBco;

end;

begin
select id
into w_idCuentaContableNoAdjudi  ----
from cuenta_contable
where "idPeriodoContable"=w_idperiodo and
    "codigoCuentaContable"  in (select "codigoCuentaContable"
                                from item_cobro_pago
                                where nombre_cuenta='NO ADJUDICADO AUTOS');
end;


	if w_valorCA > 0 then

---detalle del asiento contable por inscripcion
begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'BANCO '||banco_cuenta_bancaria,
        0, (w_valorCA + w_valorAC), id_AsientoContableCabecera, w_idCuentaContableBco)
    RETURNING id INTO id_transaccionAsientoContable;
end;


				-- RETURN '{"correcto":false, "message":" entro CA"}',w_idCuentaContableNoAdjudi::text;

begin
		perform public.actualiza_saldo_ctacble(	w_idCuentaContableBco, nombreColumna||'Debito"', w_valor);
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES',
        w_valorCA, 0,  id_AsientoContableCabecera, w_idCuentaContableCxC)   --- 2 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
		perform public.actualiza_saldo_ctacble(	w_idCuentaContableCxC, nombreColumna||'Credito"', w_valorCA);
end;

---no adjudicado autos
begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'NO ADJUDICADO AUTOS',
        w_valorAC, 0,  id_AsientoContableCabecera, w_idCuentaContableNoAdjudi)   --- 2 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

		raise notice  'value of w_idCuentaContableNoAdjudi : %', w_idCuentaContableNoAdjudi;
		raise notice  'value of w_valorAC : %', w_valorAC;
		raise notice  'value of nombreColumna : %', nombreColumna;
begin
		perform public.actualiza_saldo_ctacble(	w_idCuentaContableNoAdjudi, nombreColumna||'Credito"', w_valorAC);
end;

	elsif w_tipo='I' then

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'BANCO '||banco_cuenta_bancaria,
        0, w_valor, id_AsientoContableCabecera, 4)
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
		perform public.actualiza_saldo_ctacble(	4, nombreColumna||'Debito"', w_valor);
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES',
        w_valor, 0,  id_AsientoContableCabecera,  w_idCuentaContableCxC)   --- 2 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
		perform public.actualiza_saldo_ctacble(	w_idCuentaContableCxC, nombreColumna||'Credito"', w_valor);
end;
else

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'BANCO '||banco_cuenta_bancaria,
        0, w_valor, id_AsientoContableCabecera, w_idCuentaContableBco)
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
		perform public.actualiza_saldo_ctacble(	w_idCuentaContableBco, nombreColumna||'Debito"', w_valor);
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'NO ADJUDICADO AUTOS',
        w_valor, 0,  id_AsientoContableCabecera, w_idCuentaContableNoAdjudi)   --- 2 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
		perform public.actualiza_saldo_ctacble(	w_idCuentaContableCxC, nombreColumna||'Credito"', w_valor);
end;
end if;
end if;

	if w_valorCA > 0 and w_cont <> 1 then
begin
update public.asiento_contable_cabecera
set 	"totalDebito"=w_valorAC+ w_valorCA,
       "totalCredito" = w_valorAC+ w_valorCA,
       "totalSaldoActualFecha" = w_actualSaldo, ----- esto preguntar
       "codigoReferencialAsientoContable"='000'||id_AsientoContableCabecera  --- esto tambien preguntar
where id = id_AsientoContableCabecera;
end;
else
begin
update public.asiento_contable_cabecera
set 	"totalDebito"=w_valor,
       "totalCredito" = w_valor_iva + w_diferencia,
       "totalSaldoActualFecha" = w_actualSaldo, ----- esto preguntar
       "codigoReferencialAsientoContable"='000'||id_AsientoContableCabecera  --- esto tambien preguntar
where id = id_AsientoContableCabecera;
end;
end if;
w_cont := w_cont +1;
w_TipoTransaccion := 'I';
end loop;

return;
END;
$$;




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

----- ASIENTO COBROS EXTERNOS ------

create or replace function asiento_cobros_externo(id_contrato integer, id_cobro integer, estado_contrato character varying, idperiodo integer, anio integer, fechainicio date, nombrescliente character varying, cuotaacobrar integer, fecha_deposito date, id_pago integer, w_ivaporcentaje numeric, w_valor_adj numeric, w_tipo_cobro character varying, banco_cuenta_bancaria character varying) returns void
    language plpgsql
as
$$
declare
w_mesPeriodo character varying(3);
nombreColumna character varying(255);
w_valorInscri numeric(12,2);
w_valorFactu numeric(12,2);
w_valorAC numeric(12,2);
w_valorCA numeric(12,2);
w_valorDIS numeric(12,2);
w_idItemCobroPagoDIS integer;
w_valorRAS numeric(12,2);
w_idItemCobroPagoRAS integer;
w_idItemCobroPago integer;
w_idItemCobroPagoAC integer;
w_idItemCobroPagoCA integer;
w_codigoCuentaContableIns character varying(30);

begin
----
--###  asientos
----
w_mesPeriodo :=to_char(fechaInicio,'MON') ;
set lc_time to 'es_CL';
nombreColumna := '"'||to_char(fecha_deposito, 'TMmonth' );

 if w_tipo_cobro='CAD' then


begin
select "codigoCuentaContable" into w_codigoCuentaContableIns
from item_cobro_pago
where "nombre_cuenta"='INGRESOS POR ADJUDICACION AUTOS';
end;
			perform asiento_cobrosreg(id_contrato, id_cobro, estado_contrato, idperiodo, anio, fechaInicio,
						      nombrescliente,cuotaacobrar, fecha_deposito, id_pago, nombreColumna, w_mesPeriodo,
							w_ivaporcentaje, w_valor_adj, w_idItemCobroPago, w_codigoCuentaContableIns, banco_cuenta_bancaria);
end if;

return;
END;
$$;

---- OBTENER MES ---
create function obtener_mes(numero integer) returns character varying
    language plpgsql
as
$$
DECLARE
mes varchar(255);

BEGIN
        mes := (CASE
            when numero = 1 then 'enero'
            when numero = 2 then 'febrero'
            when numero = 3 then 'marzo'
            when numero = 4 then 'abril'
            when numero = 5 then  'mayo'
            when numero = 6 then  'junio'
            when numero = 7 then  'julio'
            when numero = 8 then  'agosto'
            when numero = 9 then  'septiembre'
            when numero = 10 then 'octubre'
            when numero = 11 then  'noviembre'
            when numero = 12 then 'diciembre'
        END);
return mes;
END
$$;

--- REGISTRAR FACTURA ---
create or replace function registrar_factura(id_pago integer, id_contrato integer, pagos character varying) returns void
    language plpgsql
as
$$
declare
contrato               public.contrato;
    empresa                public.empresa;
    codigo_establecimiento varchar(255);
    pto_emision            varchar(255);
    det_pagos              public.detalle_pago;
    item                   json;
    det_factura            jsonb;
    factura                jsonb ;
    iva                    numeric;
    --valor_iva              numeric;
    num_documento          varchar(255);
    num_factura            integer;
    codigo_factura         varchar(255);
    lleva_contabilidad         varchar(255);
    identificacion_comprador         varchar(255);
    total_subtotal               numeric;
    total_iva              numeric;
    total              numeric;
    idx numeric := 1;
    subtotal_item               numeric;
    iva_item               numeric;
    total_item               numeric;
    usuario public.usuario;
    tipo_identificacion varchar(10);


BEGIN
    contrato := (select c from contrato c where c.id = id_contrato);
    empresa := (select e from empresa e where e."sisHabilitado" = 'A');
select cod_establecimiento, punto_emision, "ivaPorcentaje"
into codigo_establecimiento, pto_emision, iva
from configuracion_general cg;
num_factura := (select nextval('factura_no_seq'));
    codigo_factura := (select trim(to_char(num_factura, '000000000')));
    identificacion_comprador := (select rpad(trim(contrato."documentoIdentidad"::text), 13, '0'));
    num_documento := concat(codigo_establecimiento, '-', pto_emision, '-', codigo_factura);
    lleva_contabilidad := case when empresa."obligadoLlevarContabilidad" = 'S' then 'SI' else 'NO' end;
    usuario := (select u from usuario u where u."documentoIdentidad" = identificacion_comprador );
    tipo_identificacion := (case
                                       when unaccent(usuario."tipoDocumentoIdentidad") ILIKE '%cedula%' then '04'
                                       when unaccent(usuario."tipoDocumentoIdentidad") ILIKE '%ruc%' then '05'
                                       else '06' end);
    factura :=
            jsonb_build_object('agenteRetencion', false, 'amCodigo', '1', 'codigoPorcentaje', '2', 'direccionComprador',
                               'PRUEBAS','direccionMatriz', 'PRUEBAS',  'establecimientoEmpresa', codigo_establecimiento, 'facCodIva', '2',
                               'facDescuento', 0.0000, 'facFecha', to_char(current_timestamp, 'YYYY-MM-DD"T"HH24:MI:SSOF:TZM'), 'facIva', 0.00, 'facMoneda', 'DOLAR',
                               'facNumero',num_factura, 'facNumeroText', codigo_factura, 'facPlazo',
                               contrato."plazoMesSeleccionado",
                               'facPorcentajeIva', '12', 'facSubsidio', 0.00, 'facSubtotal', 0.00, 'facTarifaIce', 0,
                               'facTotal', 0.0, 'facTotalBaseCero', 0.00,
                               'facTotalBaseGravada', 0.00, 'facUnidaTiempo', 'dias', 'facValorIce', 0.00, 'grabaICE',false,
                               'identificacionComprador',identificacion_comprador,
                               'llevarContabilidad', lleva_contabilidad, 'nombreComercialEmpresa',
                               empresa."nombreComercial",
                               'puntoEmisionEmpresa', pto_emision, 'razonSocialComprador',
                               concat(contrato."nombresCliente", ' ', contrato."apellidosCliente"),
                               'razonSocialEmpresa', empresa."razonSocial", 'regimenGeneral', empresa."regimenGeneral",
                               'rimpeEmprendedor', empresa."rimpeEmprendedor",
                               'rimpePolpular', empresa."rimpePopular", 'rucEmpresa', empresa."rucEmpresa",
                               'tipoIdentificacionComprador', tipo_identificacion , 'codigoFormaPago', '01','correoComprador',usuario.correo,
                               'detFacturaDao', jsonb_build_array());


FOR item in SELECT * FROM json_array_elements((pagos::json ->> 'detalleCobros')::json)
                              LOOP

    IF (item ->> 'tipo')::varchar != 'AC' THEN
            RAISE NOTICE '========================================================';
RAISE NOTICE 'id pago % ', id_pago;
            det_pagos :=
                    (select d from detalle_pago d where d."idPago" = id_pago and d.tipo = (item ->> 'tipo')::varchar limit 1);

            IF item ->> 'tipo' != 'I' then
                --valor_iva = det_pagos.valor * (iva * 0.01);
                subtotal_item := coalesce(det_pagos.valor,0) / ((iva * 0.01) + 1);
                iva_item := coalesce(det_pagos.valor,0) - coalesce(subtotal_item, 0);
else
                --valor_iva = det_pagos.valor * (iva * 0.01);
                subtotal_item := coalesce(det_pagos.valor,0);
                iva_item := coalesce(det_pagos.valor,0) * (iva * 0.01);
end if;

            /*valor_iva = det_pagos.valor * (iva * 0.01);
            subtotal := coalesce(subtotal,0) + coalesce(det_pagos.valor,0);
            total_iva := coalesce(total_iva,0) + coalesce(valor_iva, 0);*/

        -- valor_iva = det_pagos.valor * (iva * 0.01);
            total_subtotal := coalesce(total_subtotal,0) + coalesce(subtotal_item,0);
            total_iva := coalesce(total_iva,0) + coalesce(iva_item, 0);


            RAISE NOTICE 'Parsing Item tipo %, monto %  , subtotal %, iva %', item ->> 'tipo', det_pagos.valor,  subtotal_item, total_iva;
            det_factura := jsonb_build_object('detTarifa', iva, 'detIva', iva_item, 'detSubtotal', subtotal_item,
                                              'detSubtotaldescuento', subtotal_item, 'codigoProducto', trim(to_char(idx, '00000')),
                                              'descripcionProducto', item ->> 'descripcion', 'detCantidad', 1.00, 'detCantpordescuento',
                                              0.00000,
                                              'detCodIva', '2', 'detCodPorcentaje', '2', 'detValorIce', 0.00,
                                              'tieneSubsidio', false);
            RAISE NOTICE 'Detalle de factura a guardar %', det_factura;

            factura := jsonb_insert(factura, '{detFacturaDao,1}', det_factura, true);

            idx := coalesce(idx, 0) + 1;

END IF;

end loop;

    total := coalesce(total_iva,0) + coalesce(total_subtotal,0);

    RAISE NOTICE 'Valores finales subtotal %, iva %',total_subtotal , total_iva;

    factura := jsonb_set(factura,  '{facIva}', to_jsonb(total_iva::numeric));
    factura := jsonb_set(factura, '{facTotalBaseGravada}', to_jsonb(total_subtotal::numeric));
    factura := jsonb_set(factura, '{facSubtotal}', to_jsonb(total_subtotal::numeric));
    factura := jsonb_set(factura, '{facTotal}', to_jsonb(total::numeric));

    RAISE NOTICE 'Factura %', factura;
insert into factura ("sisCreado", "sisHabilitado", if_dir_establecimiento, if_fecha_emision,
                     if_identificacion_comprador, if_importe_total, if_razon_social_comprador, it_cod_doc, it_estab,
                     it_nombre_comercial, if_numero_documento, it_pto_emision, it_razon_social, it_ruc,
                     json_factura)
values (CURRENT_TIMESTAMP, 'A', empresa."direccionEmpresa", CURRENT_DATE, contrato."documentoIdentidad", total,
        concat(contrato."nombresCliente", ' ', contrato."apellidosCliente"), codigo_factura, codigo_establecimiento,
        empresa."nombreComercial", num_documento, pto_emision, empresa."razonSocial", empresa."rucEmpresa", factura);
return;
END

$$;
----- CREAR EXTENSION PARA QUITAR ACENTOS ----
CREATE EXTENSION IF NOT EXISTS unaccent;

---- ASIENTO COBROS EPR ---
create or replace function asiento_cobrosepr(id_contrato integer, id_cobro integer, estado_contrato character varying, w_idperiodo integer, w_anio integer, w_fechainicio date, w_nombrescliente character varying, w_cuotaacobrar integer, w_fechadeposito date, w_idcobro integer, nombrecolumna character varying, w_mesperiodo character varying, banco_cuenta_bancaria character varying) returns void
    language plpgsql
as
$$
declare
w_cont integer;
w_seqAsiento bigint;
id_AsientoContableCabecera integer;
w_valor numeric;
w_idItemCobroPago integer;
w_idCuentaContable integer;
w_nombreCuentaContable character varying(255);
w_actualSaldo numeric;
w_valor_iva numeric;
w_diferencia numeric;
id_transaccionAsientoContable integer;
w_TipoTransaccion character varying(1);
w_cuentaContable integer;
valorCreditoDebito numeric;
w_valorTasaAdministrativa numeric(12,2);
w_valorCapital numeric(12,2);

begin
----
--###  asiento factura inscripcion
----

set lc_time to 'es_CL';
--- valores de la cuota pagada
begin
SELECT "valorPagadoCuota","valorImpuesto", "valorTasaAdministrativa"
into w_valor, w_valor_iva, w_valorTasaAdministrativa
FROM CUOTA
where "idHistoricoPlanContrato"=id_contrato and
    "numeroCuota"=w_cuotaACobrar;
end;

w_diferencia:= w_valor - w_valor_iva - w_valorTasaAdministrativa;
w_cont := 1;
w_TipoTransaccion:='D';

WHILE (w_cont  <= 2)
                LOOP
-- cabecera del asiento contable por facuracion
begin
INSERT INTO public.asiento_contable_cabecera(
    "sisActualizado", "sisCreado", "sisHabilitado", anio, "asientoCerrado", beneficiario,
    descripcion, fecha, "mesPeriodo",  "tipoAsientoContable", "tipoTransaccion",
    "totalCredito", "totalDebito", "totalSaldoActualFecha","idCuentaContable")
VALUES (
           now(),now(), 'A', w_anio,'N', w_nombresCliente ,
           'Pago cuota Nro.'||w_cuotaACobrar, w_fechaDeposito, w_mesPeriodo, 'E',w_TipoTransaccion,
           0,0,0,2) ---cuenta contable CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES
    RETURNING id INTO id_AsientoContableCabecera;
end;

if w_cont =1 then
--detalle del asiento contable por facuracion
begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES',
        0, w_valor,  id_AsientoContableCabecera, 2)   --- 2 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin

set lc_time to 'es_CL';
nombreColumna := '"'||to_char(w_fechaDeposito, 'TMmonth' );
nombreColumna:= nombreColumna||'Credito"';
valorCreditoDebito := w_valor;
w_cuentaContable:= 2;
perform public.actualiza_saldo_ctacble(	w_cuentaContable, nombreColumna, valorCreditoDebito);
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'IVA POR PAGAR',
        w_valor_iva, 0, id_AsientoContableCabecera, 3)   --- 3 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
nombreColumna := '"'||to_char(w_fechaDeposito, 'TMmonth' );
nombreColumna:= nombreColumna||'Credito"';
valorCreditoDebito := w_valor_iva;
w_cuentaContable:= 3;
perform public.actualiza_saldo_ctacble(	w_cuentaContable, nombreColumna, valorCreditoDebito);
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'INGRESOS POR ADMINISTRACION AUTOS',
        w_valorTasaAdministrativa, 0, id_AsientoContableCabecera, 5)   --- 3 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
nombreColumna := '"'||to_char(w_fechaDeposito, 'TMmonth' );
nombreColumna:= nombreColumna||'Credito"';
valorCreditoDebito := w_valorTasaAdministrativa;
w_cuentaContable:= 5;
perform public.actualiza_saldo_ctacble(	w_cuentaContable, nombreColumna, valorCreditoDebito);
end;

---recuperarmos valor capital suscriptor

begin
select valor, "idItemCobroPago"
into w_valorCapital, w_idItemCobroPago
from detalle_pago
where tipo='AC' and
    "idPago" in (select pago.id
                 from pago, cobro
                 where pago."idCobro" = cobro.id and
                     cobro."idContrato"= id_contrato and
                     cobro.id=id_cobro); -- este cobro.id  y idContrato vienen de parametro
end;

---recuperamos datos de la cuenta contable capital suscriptor
begin
select id, nombre, "actualSaldo"
into w_idCuentaContable, w_nombreCuentaContable , w_actualSaldo  ----   como se afecta el actualsaldo,  a que cuenta esta relacionado
from cuenta_contable
where id in (select "idCuentaContable"
             from item_cobro_pago
             where id=w_idItemCobroPago); -- este id saco del select anterior
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A',w_nombreCuentaContable,
        w_valorCapital, 0, id_AsientoContableCabecera, 3) --w_idCuentaContable
    RETURNING id INTO id_transaccionAsientoContable;
end;
begin
set lc_time to 'es_CL';
nombreColumna := '"'||to_char(w_fechaDeposito, 'TMmonth' );
nombreColumna:= nombreColumna||'Credito"';
valorCreditoDebito :=  w_valorCapital;
w_cuentaContable:=  w_idCuentaContable;
perform public.actualiza_saldo_ctacble(	w_cuentaContable, nombreColumna, valorCreditoDebito);
end;

begin
update public.asiento_contable_cabecera
set 	"totalDebito"=w_valor,
       "totalCredito" = w_valor_iva + w_valorTasaAdministrativa + w_valorCapital,
       "totalSaldoActualFecha" = w_actualSaldo, ----- esto preguntar
       "codigoReferencialAsientoContable"='000'||id_AsientoContableCabecera  --- esto tambien preguntar
where id = id_AsientoContableCabecera;
end;

else

---detalle del asiento contable cuota
begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'BANCOS '|| banco_cuenta_bancaria,
        0, w_valor, id_AsientoContableCabecera, 4)   --- 4 codigo de idcuenta es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

begin
nombreColumna := '"'||to_char(w_fechaDeposito, 'TMmonth' );
nombreColumna:= nombreColumna||'Debito"';
valorCreditoDebito := w_valor;
w_cuentaContable:= 4;
perform public.actualiza_saldo_ctacble(	w_cuentaContable, nombreColumna, valorCreditoDebito);
end;

begin
INSERT INTO public.transaccion_asiento_contable(
    "sisActualizado", "sisCreado", "sisHabilitado", detalle,
    "valorCredito", "valorDebito", "idAsientoContableCabecera", "idCuentaContable")
VALUES (now(),now(),'A', 'CUENTAS Y DOCUMENTOS POR COBRAR COMERCIALES CORRIENTES',
        w_valor, 0,  id_AsientoContableCabecera, 2)   --- 2 es valor quemado para esta cuenta
    RETURNING id INTO id_transaccionAsientoContable;
end;

--- actualizamos la cabecera del asiento
begin
update public.asiento_contable_cabecera
set 	"totalDebito"=w_valor,
       "totalCredito" = w_valor,
       "totalSaldoActualFecha" = w_actualSaldo, ----- esto preguntar
       "codigoReferencialAsientoContable"='000'||id_AsientoContableCabecera  --- esto tambien preguntar
where id = id_AsientoContableCabecera;
end;

end if;

w_cont := w_cont +1;
w_TipoTransaccion := 'I';
end loop;
return;
END;
$$;

------- CALCULAR DECIMO CUARTO ---

create function calcular_decimo_cuarto(fecha_inicio date, fecha_fin date, id_trabajador integer, id_periodo integer, tipo character varying) returns text
    language plpgsql
as
$$
DECLARE
dias_laborales_anio     integer;
    sueldo_basico           numeric;
    valor_real              numeric;
    otros_ingresos          numeric;
    valor_mes1              numeric;
    valor_mes2              numeric;
    valor_mes3              numeric;
    valor_mes4              numeric;
    valor_mes5              numeric;
    valor_mes6              numeric;
    valor_mes7              numeric;
    valor_mes8              numeric;
    valor_mes9              numeric;
    valor_mes10             numeric;
    valor_mes11             numeric;
    valor_mes12             numeric;
    valor_prestamos_empresa numeric;
    total_pagar             numeric;
    total_ingresos          numeric;
    prestamos               public.prestamo;
    id_historia_laboral integer;
    periodo         public.periodo_laboral;
    anio_periodo integer;
    id_rol_pago_dic integer;
    reporte                 jsonb;
    result                  TEXT;
BEGIN
    dias_laborales_anio :=
            (select CASE WHEN (fecha_fin - fecha_inicio) > 360 then 360 else fecha_fin - fecha_inicio end);
    --periodo := (select * from periodo_laboral pl where pl.id = id_periodo);
select pl.anio into anio_periodo from periodo_laboral pl where pl.id = id_periodo;
id_rol_pago_dic := (select rp.id from periodo_laboral pl inner join rol_pago rp on pl.id = rp."idPeriodoLaboral" where pl.mes = 'DIC' and pl.anio = anio_periodo );
select cg."sueldoBasico" into sueldo_basico from configuracion_general cg limit 1;
valor_real := (sueldo_basico * dias_laborales_anio) / 360;
    otros_ingresos := valor_real;
    valor_mes1 := sueldo_basico / 12;
    valor_mes2 := sueldo_basico / 12;
    valor_mes3 := sueldo_basico / 12;
    valor_mes4 := sueldo_basico / 12;
    valor_mes5 := sueldo_basico / 12;
    valor_mes6 := sueldo_basico / 12;
    valor_mes7 := sueldo_basico / 12;
    valor_mes8 := sueldo_basico / 12;
    valor_mes9 := sueldo_basico / 12;
    valor_mes10 := sueldo_basico / 12;
    valor_mes11 := sueldo_basico / 12;
    valor_mes12 := sueldo_basico / 12;
    total_ingresos := (valor_mes1 + valor_mes2+ valor_mes3 + valor_mes4 + valor_mes5 + valor_mes6 + valor_mes7 + valor_mes8 + valor_mes9 + valor_mes10 + valor_mes11 + valor_mes12);

    --id_historia_laboral := (select hl.id from historial_laboral hl where hl."idTrabajador" = id_trabajador and hl."sisHabilitado" = 'A');
    valor_prestamos_empresa := (select coalesce(ap."valorCuota", 0)
                                from prestamo p
                                         inner join abono_prestamo ap on p.id = ap."idPrestamo"
                                where p."idTrabajador" = id_trabajador
                                  and p."modalidadDescuento" = 'DCT'
                                  and p.estado = 'PNT'
                                order by p."fechaPrestamo" desc
                                limit 1);

    total_pagar := total_ingresos - valor_prestamos_empresa;

    reporte := jsonb_build_object('diasLaboralesAnio', dias_laborales_anio, 'valorReal', valor_real, 'valorNominal',
                                  sueldo_basico, 'otrosIngresos', valor_real,
                                  'valorMes1', valor_mes1, 'valorMes2', valor_mes2, 'valorMes3', valor_mes3,
                                  'valorMes4', valor_mes4, 'valorMes5', valor_mes5, 'valorMes6', valor_mes6,
                                  'valorMes7', valor_mes7, 'valorMes8', valor_mes8, 'valorMes9', valor_mes9,
                                  'valorMes10', valor_mes10, 'valor_mes11', valor_mes10, 'valorMes11', valor_mes11,
                                  'valorMes12', valor_mes12, 'totalIngresos', total_ingresos, 'totalEgresos',
                                  valor_prestamos_empresa, 'totalPagar', total_pagar, 'prestamosEmpresas',
                                  valor_prestamos_empresa);

    RAISE NOTICE 'reporte %', reporte;
    RAISE NOTICE 'VALOR A GUARDAR total a pagar %, mes 12 % , en el rol de pago con id %', total_pagar, valor_mes12, id_rol_pago_dic;

    if tipo = 'A' then
update rol_pago rl
set "ProvDecimoCuarto" = valor_mes12, "acumuladoDecimoTercero"= total_pagar
where rl.id = id_rol_pago_dic;
else
update rol_pago rl
set "pagoDecimoCuartoMes" = total_pagar, "ProvDecimoCuarto" = valor_mes12
where rl.id = id_rol_pago_dic;
end if;


SELECT jsonb_pretty(reporte) INTO result;
RETURN result;
END;

$$;
 --- OBTENER NUMERO DE CARGAS ----
 create or replace function obtener_numero_cargas(id_trabajador integer) returns integer
     language plpgsql
as
$$
DECLARE
result         TEXT;
    registro_carga record;
    contador       integer := 0;
    total_anios    integer;
BEGIN

for registro_carga in (select * from carga_familiar cf where cf."idTrabajador" = id_trabajador)
        loop

            if upper(registro_carga.parentesco) = 'CONYUGUE' then
                contador := contador + 1;
else
                if upper(registro_carga.parentesco) = 'HIJO' THEN
                    total_anios := (select current_date - registro_carga."fechaNacimiento");
                    if total_anios < 18 then
                        contador := contador + 1;
else
                        if registro_carga.aplicautilidad = 'S' then
                            contador := contador + 1;
end if;
end if;
end if;

end if;
end loop;

return contador;
END;

$$;

--- CALCULAR UTILIDAD ---

create or replace function calcular_utilidades(fecha_inicio date, fecha_fin date, id_periodo integer, id_pago_uno integer) returns text
    language plpgsql
as
$$
DECLARE
result                         TEXT;
    reporte jsonb := jsonb_build_object('utilidades', jsonb_build_array());
    reporte_item jsonb;
    utilidad                       numeric;
    valor_4                        numeric;
    valor_5                        numeric;
    valor_nominal                  numeric;
    valor_real                     numeric;
    registro_trabajador            record;
    total_dias_trabajados_anio     integer;
    total_dias_trabajadores        integer := 8273;
    total_dias_cargas              integer := 3495;
    fecha_ingreso                  date;
    total_cargas                   integer;
    otros_ingresos                 numeric := 0;
    dias_cargas                    numeric := 0;
    valor_mes1                     numeric := 0;
    valor_mes2                     numeric := 0;
    valor_mes3                     numeric := 0;
    valor_mes4                     numeric := 0;
    valor_mes5                     numeric := 0;
    valor_mes6                     numeric := 0;
    valor_mes7                     numeric := 0;
    valor_mes8                     numeric := 0;
    valor_mes9                     numeric := 0;
    valor_mes10                    numeric := 0;
    valor_mes11                    numeric := 0;
    valor_mes12                    numeric := 0;
    total_ingresos                 numeric := 0;
    total_pagar                    numeric := 0;
    valor_prestamos_empresa        numeric := 0;
    trabajadores_calcular_utilidad jsonb   := jsonb_build_object('trabajadores', jsonb_build_array());
    trabajador_aux                 jsonb;
    item                 jsonb;
    id_trabajador integer;
    anio_periodo integer;
    id_periodo_contable integer;
BEGIN

    anio_periodo := (select pl.anio from periodo_laboral pl where pl.id = id_periodo);
    id_periodo_contable := (select pc.id from periodo_contable pc where pc.anio = anio_periodo);

    IF id_periodo_contable is null then
        RAISE EXCEPTION 'No hay informacion del periodo contable';
end if;

    -- Se obtiene la suma de los saldos de las cuentas que inican con 4y 5
select sum(cc."actualSaldo") into valor_4 from cuenta_contable cc where cast(cc.identificador as text) like '4%' and cc."idPeriodoContable" = id_periodo_contable;
select sum(cc."actualSaldo") into valor_5 from cuenta_contable cc where cast(cc.identificador as text) like '5%' and cc."idPeriodoContable" = id_periodo_contable;

-- Se calcula la utilidad , valor nominar y anio
utilidad := valor_4 - valor_5;
    valor_nominal := (utilidad * 10) / 100;


    RAISE NOTICE 'Valores inciales utilidad %, valor nominal %', utilidad, valor_nominal;

    --Se obtiene el calculo de los dias trabajados por todos los trabajadores
for registro_trabajador in (select * from trabajador t where t."sisHabilitado" = 'A')
        loop
            fecha_ingreso := (select hl."fechaIngreso"
                              from historial_laboral hl
                                       inner join trabajador t on t.id = hl."idTrabajador"
                              where hl."idTrabajador" = registro_trabajador.id
                                and hl."sisHabilitado" = 'A');
            if fecha_ingreso is not null then
                total_dias_trabajados_anio := (select case
                                                          when (current_date - fecha_ingreso) > 360 then 360
                                                          else current_date - fecha_ingreso end);
                total_dias_trabajadores := total_dias_trabajadores + total_dias_trabajados_anio;
                trabajador_aux := jsonb_build_object('id', registro_trabajador.id, 'fechaIngreso', fecha_ingreso, 'totalDiasTrabajados', total_dias_trabajados_anio);
                trabajadores_calcular_utilidad :=
                        jsonb_insert(trabajadores_calcular_utilidad, '{trabajadores,1}', trabajador_aux, true);
end if;

end loop;

    RAISE NOTICE 'lista de trabajadores %', trabajadores_calcular_utilidad;

FOR item in SELECT * FROM jsonb_array_elements((trabajadores_calcular_utilidad ->> 'trabajadores')::jsonb) loop
    total_dias_trabajados_anio := (item ->> 'totalDiasTrabajados')::integer;
id_trabajador := (item ->> 'id')::integer;
        RAISE NOTICE 'id trabajador % , dias laborados %', id_trabajador, total_dias_trabajados_anio;

        valor_real := valor_nominal / total_dias_trabajadores * total_dias_trabajados_anio;
                total_cargas := (select  public.obtener_numero_cargas(id_trabajador := registro_trabajador.id));

                if total_cargas > 0 then
                    dias_cargas := total_dias_trabajados_anio * total_cargas;
                    otros_ingresos := (utilidad * 5)/100;
                    valor_mes1 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes2 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes3 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes4 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes5 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes6 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes7 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes8 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes9 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes10 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes11 := otros_ingresos/total_dias_cargas * dias_cargas;
                    valor_mes12 := otros_ingresos/total_dias_cargas * dias_cargas;
end if;

                valor_prestamos_empresa := (select coalesce((select ap."valorCuota"
                                from prestamo p
                                         inner join abono_prestamo ap on p.id = ap."idPrestamo"
                                where p."idTrabajador" = id_trabajador
                                  and p."modalidadDescuento" = 'DCT'
                                  and p.estado = 'PNT'
                                order by p."fechaPrestamo" desc
                                limit 1), 0));

                total_ingresos := valor_real + valor_mes1;
                total_pagar := total_ingresos - valor_prestamos_empresa;

                RAISE NOTICE 'Trabajador % , dias trabajados por anio %, fecha ingreso %, valor real %, cargas %', registro_trabajador.id, total_dias_trabajados_anio, fecha_ingreso, valor_real, total_cargas;

insert into pagos2 ("sisActualizado", "sisCreado", "sisHabilitado", "anioPago", anticipos, "diasLaboradosAlAnio", "fechaActual",
                    "fechaFin", "fechaInicio", multas, "otrosDescuentos", "otrosIngresos", "prestamosEmpresa", "totalEgresos",
                    "totalIngresos", "valorAPagar", "valorMes1", "valorMes10", "valorMes11", "valorMes12", "valorMes2", "valorMes3",
                    "valorMes4", "valorMes5", "valorMes6", "valorMes7", "valorMes8", "valorMes9", "valorNominal", "valorReal",
                    "idPagosUno", "idPeriodoLaboral", "idTrabajador")

values (current_timestamp, current_timestamp, 'A',anio_periodo , 0, total_dias_trabajados_anio, current_date, fecha_fin,
        fecha_inicio, 0,0, otros_ingresos, valor_prestamos_empresa, valor_prestamos_empresa, total_ingresos,
        total_pagar, valor_mes1, valor_mes10, valor_mes11, valor_mes12, valor_mes2, valor_mes3,
        valor_mes4, valor_mes5, valor_mes6, valor_mes7, valor_mes8, valor_mes9, valor_nominal, valor_real,
        id_pago_uno, id_periodo, id_trabajador);

reporte_item := jsonb_build_object('idTrabajador', id_trabajador, 'valorReal', valor_real, 'utilidadcargas',otros_ingresos, 'diasLaborados', total_dias_trabajados_anio,'utilidad',utilidad);
        reporte := jsonb_insert(reporte, '{utilidades,1}', reporte_item,true );

end loop;

    RAISE NOTICE 'reporte % ', reporte;

SELECT jsonb_pretty(reporte) INTO result;

RETURN result;
END;

$$;


---- GENRERAR BALANCE DE COMPROBACION -----

create or replace function generar_balance_comprobacion(anio_periodo integer, cuenta_inicio integer, cuenta_fin integer, id_mes_inicio integer, id_mes_fin integer) returns text
    language plpgsql
as
$$
DECLARE
consulta text;
        id_periodo integer;
        mes_inicio varchar(255);
        mes_fin varchar(255);
        mes_anterior varchar(255);
        balance jsonb;
        result text;
BEGIN


        /*for counter in mes_inicio..coalesce(mes_fin, 0) + 1 loop
	        raise notice 'counter: %', counter;

	        mes_actual:= (select public.obtener_mes(counter));
	        mes_anterior:= (select public.obtener_mes(counter));
	        /*operador:= case when filtro_suma_debito != '' then '+' else '' end;
	        filtro_suma_debito := concat(filtro_suma_debito, operador,  'coalesce("'||mes_actual||'Debito", 0)');
	        filtro_suma_credito := concat(filtro_suma_credito, operador,  'coalesce("'||mes_actual||'Credito", 0)');*/
	        RAISE NOTICE 'filtro final  %', filtro_suma_debito;

	    end loop;*/

        /*consulta := 'select nombre, '
                        ||filtro_suma_debito||' debito, '
                        ||filtro_suma_credito||' credito '
                        --||filtro_suma_credito||' credito '
                        ||' from cuenta_contable cc where cc."codigoCuentaContable" in (''1'',''2'')';

         RAISE NOTICE 'query %', consulta;*/
select id into id_periodo from periodo_contable pc where pc.anio = anio_periodo;
mes_inicio:= (select public.obtener_mes(id_mes_inicio));
        mes_fin:= (select public.obtener_mes(id_mes_fin));
        mes_anterior:= (select public.obtener_mes(case when id_mes_inicio-1 = 0 then 12 else id_mes_inicio -1 end));
        RAISE NOTICE 'meses actual %, mes anterior %, mes final %, idPeriodo %  ', mes_inicio, mes_anterior, mes_fin, id_periodo;

        consulta := 'select to_jsonb(array_agg(j)) from (select cc.nombre nombre, cc.identificador codigo,'
                        '"'||mes_anterior||'Saldo" as saldoInicial, '
                        '"'||mes_fin||'Saldo" as saldoFinal, '
                        '"'||mes_fin||'Debito" as debitos, '
                        '"'||mes_fin||'Credito" as creditos '
                        ||' from cuenta_contable cc  where cast(cc.identificador as text)  ~ (''^['||cuenta_inicio||'-'||cuenta_fin||']'')) j';

         RAISE NOTICE 'query %', consulta;

EXECUTE consulta INTO balance;

--SELECT jsonb_build_object('balance' ,balance, 'total', jsonb_array_length(balance)) INTO balance;
SELECT jsonb_build_array(balance, jsonb_array_length(balance)) INTO balance;

SELECT jsonb_pretty(balance) INTO result;

RETURN result;

end;
$$;

---- REPORTE DE ASAMBLEA ---
create or replace function generar_reporte_asamblea(fecha_inicio character varying) returns text
    language plpgsql
as
$$
DECLARE
result TEXT;
    reporte JSONB;

        new_params TEXT[];
        new_values TEXT[];
        filtros TEXT;
        consulta TEXT;
BEGIN
/*
        SELECT string_to_array(trim('{}' FROM parametros), ',') INTO new_params;
    SELECT string_to_array(trim('{}' FROM valores), ',') INTO new_values;

    SELECT public.add_filtros_join(new_params, true, new_values) INTO filtros;*/

    /*consulta := '        select to_jsonb(array_agg(j))
    into reporte
    from (select c.id,
                 c."nombresCliente",
                 c."documentoIdentidad",
                 c.estado,
                 c."precioPlanSeleccionado",
                 c."plazoMesSeleccionado",
                 c."planSeleccionado"                                                                   plan,
                 c."numeroDeContrato",
                 c."nombreGrupo",
                 c."valorFondo",
                 c."fechaInicio"                                                                     as fechaIngreso,
                 hpc."totalCuotasCobradas"                                                           as cuotasPagadas,
                 hpc."totalCuotasMora"                                                               as cuotasMora,
                 c2."valorCuota"                                                                     as valorMensual,
                 (c."precioPlanSeleccionado" - c2."valorCuota") -
                 (c."precioPlanSeleccionado" / c."plazoMesSeleccionado") * hpc."totalCuotasCobradas" as costoConsorcio
                  ,
                 l."valorOferta"                                                                     as licitacion,
                 l."porcentajeOferta",
                 ((((c."precioPlanSeleccionado"::numeric / c."plazoMesSeleccionado") * hpc."totalCuotasCobradas") +
                   l."valorOferta") * 100) / c."precioPlanSeleccionado"::numeric                        acumulado
          from contrato c
                   inner join historico_plan_contrato hpc on c.id = hpc."idContrato"
                   inner join cuota c2 on hpc.id = c2."idHistoricoPlanContrato"
                   inner join licitacion l on c.id = l."idContrato"
          where to_char(l."fechaOferta", ''YYYY-MM'') = fecha_inicio
            and to_char(c2."fechaCobro", ''YYYY-MM'') = fecha_inicio
            and c.estado = ''EPR'') j';*/

select to_jsonb(array_agg(j))
into reporte
from (select c.id,
             c."nombresCliente",
             c."documentoIdentidad",
             c.estado,
             c."precioPlanSeleccionado",
             c."plazoMesSeleccionado",
             c."planSeleccionado"                                                                   plan,
             c."numeroDeContrato",
             c."nombreGrupo",
             c."valorFondo",
             c."fechaInicio"                                                                     as fechaIngreso,
             hpc."totalCuotasCobradas"                                                           as cuotasPagadas,
             hpc."totalCuotasMora"                                                               as cuotasMora,
             c2."valorCuota"                                                                     as valorMensual,
             (c."precioPlanSeleccionado" - c2."valorCuota") -
             (c."precioPlanSeleccionado" / c."plazoMesSeleccionado") * hpc."totalCuotasCobradas" as costoConsorcio
              ,
             l."valorOferta"                                                                     as licitacion,
             l."porcentajeOferta",
             ((((c."precioPlanSeleccionado"::numeric / c."plazoMesSeleccionado") * hpc."totalCuotasCobradas") +
               l."valorOferta") * 100) / c."precioPlanSeleccionado"::numeric                        acumulado
      from contrato c
               inner join historico_plan_contrato hpc on c.id = hpc."idContrato"
               inner join cuota c2 on hpc.id = c2."idHistoricoPlanContrato"
               inner join licitacion l on c.id = l."idContrato"
      where to_char(l."fechaOferta", 'YYYY-MM') = fecha_inicio
        and to_char(c2."fechaCobro", 'YYYY-MM') = fecha_inicio
        and c.estado = 'EPR') j;

SELECT jsonb_build_array(reporte, jsonb_array_length(reporte)) INTO reporte;

SELECT jsonb_pretty(reporte) INTO result;
--SELECT jsonb_pretty(jsonb_build_object('correcto', true, 'message', reporte)) INTO result;

RAISE NOTICE 'reporte de asamblea %', result;

RETURN result;
END;
$$;

---- REHISTRAR ID CONTRATOS ---
create or replace function registrar_id_contratos(id_contrato_ant integer, id_contrato_act integer) returns text
    language plpgsql
as
$$
DECLARE
result TEXT;
BEGIN
UPDATE contrato
SET "idContratos" = ARRAY_APPEND("idContratos" , id_contrato_ant) WHERE id = id_contrato_act;
RETURN '{"correcto":true, "message":"Se guardo contrato padre correctamente"}';
ROLLBACK;
END;

$$;

----
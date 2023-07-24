------------ CREAR PLAN ------------

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
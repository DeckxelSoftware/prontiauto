---------- ACTUALIZAR GRUPO ----------

-- la primera posicion del arreglo de montos es la suma de todos los montos
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
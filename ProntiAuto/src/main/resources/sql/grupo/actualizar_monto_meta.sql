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
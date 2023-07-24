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
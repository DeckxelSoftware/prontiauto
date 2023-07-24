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
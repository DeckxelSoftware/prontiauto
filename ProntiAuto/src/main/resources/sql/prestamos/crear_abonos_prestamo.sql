------------- CREAR ABONOS PRESTAMO -------------

CREATE OR REPLACE FUNCTION crear_abonos_prestamo(
    num_cuotas INTEGER, 
    modalidad_descuento CHAR VARYING, 
    monto NUMERIC, 
    tasa_interes NUMERIC, 
    id_prestamo INTEGER
) RETURNS TEXT AS $$
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
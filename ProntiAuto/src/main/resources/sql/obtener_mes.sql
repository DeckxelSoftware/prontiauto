    CREATE OR REPLACE FUNCTION obtener_mes(fecha DATE) RETURNS CHAR VARYING AS $$
    DECLARE
        result CHAR VARYING;
        mes INTEGER;
    BEGIN
        SELECT (to_char(fecha, 'MM'))::integer INTO mes;
        IF(mes = 1) THEN
            result := 'ENE';
        ELSEIF(mes = 2) THEN
            result := 'FEB';
        ELSEIF(mes = 3) THEN
            result := 'MAR';
        ELSEIF(mes = 4) THEN
            result := 'ABR';
        ELSEIF(mes = 5) THEN
            result := 'MAY';
        ELSEIF(mes = 6) THEN
            result := 'JUN';
        ELSEIF(mes = 7) THEN    
            result := 'JUL';
        ELSEIF(mes = 8) THEN
            result := 'AGO';
        ELSEIF(mes = 9) THEN
            result := 'SEP';
        ELSEIF(mes = 10) THEN
            result := 'OCT';
        ELSEIF(mes = 11) THEN
            result := 'NOV';
        ELSEIF(mes = 12) THEN
            result := 'DIC';
        END IF;
        RETURN result;
    END;
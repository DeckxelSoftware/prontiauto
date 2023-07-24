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
---------- ADD FILTROS JOIN ----------

CREATE OR REPLACE FUNCTION add_filtros_join(params TEXT[], search BOOLEAN, values_par TEXT[]) RETURNS TEXT AS $$
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
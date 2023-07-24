-------- ASIENTO CONTABLE CABECERA --------

CREATE OR REPLACE FUNCTION generar_asiento_contable_cabecera (jsonbpar CHAR VARYING) RETURNS TEXT AS $$
    DECLARE

    ------ asiento contable cabecera ------
    fecha DATE;
    anio INTEGER;
    mes_periodo CHAR VARYING(3);
    tipo_transaccion CHAR VARYING(2);
    tipo_asiento_contable CHAR VARYING(2);
    codigo_referencial_asiento_contable CHAR VARYING;
    beneficiario CHAR VARYING;
    total_debito NUMERIC;
    total_credito NUMERIC;
    total_saldo_actual_fecha NUMERIC;
    serie CHAR VARYING;
    diferencia NUMERIC;
    descripcion CHAR VARYING;
    transaccion_asiento_contable jsonb;

    ------ transaccion asiento contable ------
    detalle CHAR VARYING;
    valor_debito NUMERIC;
    valor_credito NUMERIC;
    numero_factura CHAR VARYING;

    ------ otras variables ------
    count INTEGER;
    num INTEGER;
    BEGIN
    fecha := (jsonbpar::jsonb->>'fecha')::DATE;
    tipo_transaccion := (jsonbpar::jsonb->>'tipo_transaccion')::CHAR VARYING;
    tipo_asiento_contable := (jsonbpar::jsonb->>'tipo_asiento_contable')::CHAR VARYING;
    codigo_referencial_asiento_contable := (jsonbpar::jsonb->>'codigo_referencial_asiento_contable')::CHAR VARYING;
    beneficiario := (jsonbpar::jsonb->>'beneficiario')::CHAR VARYING;
    total_debito := (jsonbpar::jsonb->>'total_debito')::NUMERIC;
    total_credito := (jsonbpar::jsonb->>'total_credito')::NUMERIC;
    total_saldo_actual_fecha := (jsonbpar::jsonb->>'total_saldo_actual_fecha')::NUMERIC;
    serie := (jsonbpar::jsonb->>'serie')::CHAR VARYING;
    diferencia := (jsonbpar::jsonb->>'diferencia')::NUMERIC;
    descripcion := (jsonbpar::jsonb->>'descripcion')::CHAR VARYING;
    transaccion_asiento_contable := (jsonbpar::jsonb->>'transaccion_asiento_contable')::jsonb;
    anio := (SELECT extract(year FROM fecha))::INTEGER;
    mes_periodo := public.obtener_mes(fecha);

    INSERT INTO asiento_contable_cabecera (
        "sisCreado", "sisActualizado", "sisHabilitado"
        fecha, anio, "mesPeriodo", "tipoTransaccion", "tipoAsientoContable", "codigoReferencialAsientoContable", "beneficiario", "totalDebito", "totalCredito", "totalSaldoActualFecha", "serie", "diferencia", "descripcion"
    ) VALUES (
        now(), now(), 'A'
        fecha, anio, mes_periodo, tipo_transaccion, tipo_asiento_contable, codigo_referencial_asiento_contable, beneficiario, total_debito, total_credito, total_saldo_actual_fecha, serie, diferencia, descripcion
    );
    num := jsonb_array_length(transaccion_asiento_contable);
    count := 0;
    WHILE (count < num)
    LOOP
        detalle := ((transaccion_asiento_contable->>count) ->> 'detalle')::CHAR VARYING;
        valor_debito := ((transaccion_asiento_contable->>count) ->> 'valorDebito')::NUMERIC;
        valor_credito := ((transaccion_asiento_contable->>count) ->> 'valorCredito')::NUMERIC;
        numero_factura := ((transaccion_asiento_contable->>count) ->> 'numeroFactura')::CHAR VARYING;
        INSERT INTO transaccion_asiento_contable (
            "sisCreado", "sisActualizado", "sisHabilitado"
            "detalle", "valorDebito", "valorCredito", "numeroFactura"
        ) VALUES (
            now(), now(), 'A'
            detalle, valor_debito, valor_credito, numero_factura
        );
        count := count + 1;
    END LOOP;
    RETURN '{"correcto":true, "message":"El asiento contable se ha creado correctamente"}';
    ROLLBACK;
    END;
    $$ LANGUAGE plpgsql;
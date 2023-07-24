----------- CREAR PRESTAMO -----------

CREATE OR REPLACE FUNCTION crear_prestamo(jsonpar CHAR VARYING) RETURNS TEXT AS $$
DECLARE
trabajador public.trabajador;
id_prestamo INTEGER;
id_trabajador INTEGER;
tipo_prestamo CHAR VARYING;
valor NUMERIC;
cuotas INTEGER;
tasa_interes NUMERIC;
concepto CHAR VARYING;
nombre_apellido_responsable CHAR VARYING;
comprobante_egreso CHAR VARYING;
modalidad_descuento CHAR VARYING;
BEGIN
id_trabajador :=(jsonpar::jsonb->>'idTrabajador')::INTEGER;
tipo_prestamo :=(jsonpar::jsonb->>'tipoPrestamo')::CHAR VARYING;
valor :=(jsonpar::jsonb->>'valor')::NUMERIC;
cuotas :=(jsonpar::jsonb->>'cuotas')::INTEGER;
tasa_interes :=(jsonpar::jsonb->>'tasaInteres')::NUMERIC;
concepto :=(jsonpar::jsonb->>'concepto')::CHAR VARYING;
nombre_apellido_responsable :=(jsonpar::jsonb->>'nombreApellidoResponsable')::CHAR VARYING;
comprobante_egreso :=(jsonpar::jsonb->>'comprobanteEgreso')::CHAR VARYING;
modalidad_descuento :=(jsonpar::jsonb->>'modalidadDescuento')::CHAR VARYING;

INSERT INTO prestamo(
    "sisCreado","sisActualizado","sisHabilitado",
    "idTrabajador","tipoPrestamo","fechaPrestamo",
    "comprobanteEgreso",valor,cuotas,"tasaInteres",
    "concepto",estado,"modalidadDescuento",
    "totalPagado","totalSaldo","nombreApellidoResponsable","estadoSolicitud"
) VALUES (
    now(),now(),'A',
    id_trabajador,tipo_prestamo,now(),
    comprobante_egreso,valor,cuotas,tasa_interes,
    concepto,'PNT',modalidad_descuento,
    0,0,nombre_apellido_responsable,'VIG'
) RETURNING id INTO id_prestamo;

PERFORM public.crear_abonos_prestamo(
    cuotas,modalidad_descuento,valor,tasa_interes,id_prestamo
);

RETURN '{"correcto":true, "message":"Se creo correctamente el prestamo"}';
ROLLBACK;
END;
$$ LANGUAGE plpgsql;
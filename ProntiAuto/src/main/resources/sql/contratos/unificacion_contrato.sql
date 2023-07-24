------------- UNIFICACION CONTRATO -------------

CREATE OR REPLACE FUNCTION unificacion_contrato(jsonpar CHAR VARYING) RETURNS CHARACTER VARYING AS $$
DECLARE
contrato1 public.contrato;
contrato2 public.contrato;
nuevo_plan public.plan;
plan1 public.plan;
plan2 public.plan;
cg public.configuracion_general;
hpc1 public.historico_plan_contrato;
hpc2 public.historico_plan_contrato;
cuota1 public.cuota;
cuota2 public.cuota;
valor_nueva_incripcion NUMERIC;
tasa_cambio_contrato NUMERIC;
total_pagado_primer_plan NUMERIC;
total_pagado_segundo_plan NUMERIC;
abono_capital NUMERIC;
iva NUMERIC;
tasa_administrativa NUMERIC;
impuesto NUMERIC;
numero_cuotas_pasar INTEGER;
abonos_capital_actual NUMERIC;
saldo_capital NUMERIC;
total_tasa_administrativa_cobrada NUMERIC;
total_monto_cobrado NUMERIC;
id_historico INTEGER;
capital1 NUMERIC;
capital2 NUMERIC;
capital3 NUMERIC;
saldo_pagado_tasa NUMERIC;
tasa_administrativa1 NUMERIC;
tasa_administrativa2 NUMERIC;
tasa_administrativa3 NUMERIC;
diferencia NUMERIC;
valor_absoluto NUMERIC;
valor_pagado_cuota NUMERIC;
valor_recargo NUMERIC;
id_nuevo_contrato INTEGER;
fecha_fin DATE;
abono_restante NUMERIC;
adicional_inscripcion NUMERIC;
inscripciones_pagadas_planes NUMERIC;
total_precios_planes NUMERIC;
total_pagado_planes NUMERIC;
fecha_contrato_mayor BOOLEAN;
nombre_grupo INTEGER;
id_cliente_en_grupo INTEGER;
version_contrato INTEGER;
primera_cuota_pagada CHAR VARYING;
monto_grupo_mayor NUMERIC;
tasa_administrativa_restante NUMERIC;
grupos INTEGER[];
montos NUMERIC[];
num_cuotas_abajo_hacia_arriba INTEGER;
---------- json values -----------
id_hpc1 INTEGER;
id_hpc2 INTEGER;
id_contrato1 INTEGER;
id_contrato2 INTEGER;
id_plan1 INTEGER;
id_plan2 INTEGER;
id_nuevo_plan INTEGER;
nc double precision;
pms INTEGER;
observacion CHARACTER VARYING;
fecha_inicio date; 
fic date;
dscto_recargo NUMERIC;
id_vendedor INTEGER;
nueva_cuota NUMERIC;
BEGIN

id_hpc1 := (jsonpar::jsonb->>'idHistoricoPlanContrato1')::INTEGER;
id_hpc2 := (jsonpar::jsonb->>'idHistoricoPlanContrato2')::INTEGER;
id_contrato1 := (jsonpar::jsonb->>'idContrato1')::INTEGER;
id_contrato2 := (jsonpar::jsonb->>'idContrato2')::INTEGER;
id_plan1 := (jsonpar::jsonb->>'idPlan1')::INTEGER;
id_plan2 := (jsonpar::jsonb->>'idPlan2')::INTEGER;
id_nuevo_plan := (jsonpar::jsonb->>'idNuevoPlan')::INTEGER;
nc := (jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
observacion := (jsonpar::jsonb->>'observacion')::VARCHAR;
fecha_inicio := (jsonpar::jsonb->>'fechaInicio')::DATE;
fic := (jsonpar::jsonb->>'fechaInicioCobro')::DATE;
dscto_recargo := (jsonpar::jsonb->>'dsctoRecargo')::NUMERIC;
id_vendedor := (jsonpar::jsonb->>'idVendedor')::INTEGER;
nueva_cuota := (jsonpar::jsonb->>'cuota')::NUMERIC;

adicional_inscripcion:=0;
cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
plan1 := (SELECT pl FROM plan pl WHERE id= id_plan1);
plan2 := (SELECT pl FROM plan pl WHERE id=id_plan2);
nuevo_plan := (SELECT pl FROM plan pl WHERE id=id_nuevo_plan);
contrato1 := (SELECT co FROM contrato co WHERE id=id_contrato1);
contrato2 := (SELECT co FROM contrato co WHERE id=id_contrato2);
hpc1 := (SELECT h FROM historico_plan_contrato h WHERE id=id_hpc1);
hpc2 := (SELECT h FROM historico_plan_contrato h WHERE id=id_hpc2);
cuota1 := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc1.id LIMIT 1);
cuota2 := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc2.id LIMIT 1);

-- verificar que el contrato este en estado registrado o en proceso
IF contrato1.estado <> 'REG' AND contrato1.estado <> 'EPR' THEN
	RETURN '{"correcto":false, "message":"El contrato 1 no se encuentra en estado registrado o en proceso"}';
END IF;

-- verificar que el contrato este en estado registrado o en proceso
IF contrato2.estado <> 'REG' AND contrato2.estado <> 'EPR' THEN
	RETURN '{"correcto":false, "message":"El contrato 2 no se encuentra en estado registrado o en proceso"}';
END IF;

-- verificar que el contrato 1 y el contrato 2 tengan la primera cuota pagada
IF (hpc1."totalCuotasCobradas" = 0) THEN
	RETURN '{"correcto":false, "message":"El contrato 1 no tiene cuotas pagadas"}';
END IF;
IF (hpc2."totalCuotasCobradas" = 0) THEN
	RETURN '{"correcto":false, "message":"El contrato 2 no tiene cuotas pagadas"}';
END IF;

-- calcular fecha fin de contrato
fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

-- contrato mas antiguo
fecha_contrato_mayor := (select (contrato1."fechaInicio"::date >= contrato2."fechaInicio"::date));
if(fecha_contrato_mayor) then
	nombre_grupo := contrato2."nombreGrupo";
	id_cliente_en_grupo := contrato2."idClienteEnGrupo";
	version_contrato := contrato2.version;
	monto_grupo_mayor := cuota2."abonoCapital" * (hpc2."totalCuotasCobradas" - 1);
	grupos := ARRAY[nombre_grupo, contrato1."nombreGrupo"];
	montos := ARRAY[monto_grupo_mayor, monto_grupo_mayor];
ELSE
	nombre_grupo := contrato1."nombreGrupo";
	id_cliente_en_grupo := contrato1."idClienteEnGrupo";
	version_contrato := contrato1.version;
	monto_grupo_mayor := cuota1."abonoCapital" * (hpc1."totalCuotasCobradas" - 1);
	grupos := ARRAY[nombre_grupo, contrato2."nombreGrupo"];
	montos := ARRAY[monto_grupo_mayor, monto_grupo_mayor];
end if;

-- se desactiva y se cambia el estado de los contratos
UPDATE contrato SET "sisHabilitado"='I', estado='UNI' WHERE id = id_contrato1;
UPDATE contrato SET "sisHabilitado"='I', estado='UNI' WHERE id = id_contrato2;

-- calcular el valor de la nueva cuota
abono_capital := nuevo_plan.precio/pms;
iva := (cg."ivaPorcentaje"/100);
tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
impuesto := tasa_administrativa * iva;

-- crear nuevo contrato
INSERT INTO contrato (
	"sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
	"fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
	"fechaIniciaCobro", "estado", "plazoMesSeleccionado", version, "cuotaActual",
	"tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
	"nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo", "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
) VALUES (
	now(), now(), 'A', nc, fecha_inicio, fecha_fin, 0, 0,
	observacion, fic, 'REG', pms, (version_contrato + 1), nueva_cuota,
	contrato1."tipoDocumentoIdentidad", contrato1."documentoIdentidad", nuevo_plan.modelo, nuevo_plan.precio,
	contrato1."nombresCliente", contrato1."apellidosCliente", nombre_grupo, dscto_recargo, id_cliente_en_grupo, id_vendedor, 0
) RETURNING id INTO id_nuevo_contrato;

-- totla precio de los 2 planes anteriores
total_precios_planes := plan1.precio + plan2.precio;

-- comprobar si la inscripcion del plan actual es mayor que la del plan anterior
if (nuevo_plan.precio > total_precios_planes) then
-- Valor pagados por inscripciones de los 2 planes
inscripciones_pagadas_planes := plan1.inscripcion + plan2.inscripcion;
adicional_inscripcion := nuevo_plan.inscripcion - inscripciones_pagadas_planes;
end if;

-- Valor nueva incripcion y total cobro inscripcion
tasa_cambio_contrato := cg."tasaCambioContrato"/100;
valor_nueva_incripcion := (nuevo_plan.precio * tasa_cambio_contrato) + adicional_inscripcion;

-- Total pagado de cada uno de los planes
total_pagado_primer_plan := hpc1."totalMontoCobrado" - cuota1."valorCuota";
total_pagado_segundo_plan := hpc2."totalMontoCobrado" - cuota2."valorCuota";
total_pagado_planes := total_pagado_primer_plan + total_pagado_segundo_plan;

-- calcular cuotas a pasar
numero_cuotas_pasar := (SELECT floor(total_pagado_planes / nueva_cuota));

-- generar tabla de cuotas
valor_pagado_cuota := total_pagado_planes - (numero_cuotas_pasar * nueva_cuota);
total_monto_cobrado := (nueva_cuota * numero_cuotas_pasar) + valor_pagado_cuota;

abono_restante := 0;
tasa_administrativa_restante := 0;
abonos_capital_actual := numero_cuotas_pasar * abono_capital;
total_tasa_administrativa_cobrada := numero_cuotas_pasar * tasa_administrativa;
IF(valor_pagado_cuota > 0) THEN
	tasa_administrativa_restante := tasa_administrativa + impuesto;
	abono_restante := valor_pagado_cuota - tasa_administrativa_restante;
	abonos_capital_actual := abonos_capital_actual + abono_restante;
	total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + tasa_administrativa;
END IF;

saldo_capital := nuevo_plan.precio - abonos_capital_actual;

-- calculos del descuento por recargo
if(dscto_recargo <> 0) then
	dscto_recargo := cg."cuotaAdministrativa" * (desc_recargo/100);
	valor_recargo := cg."cuotaAdministrativa" - dscto_recargo;
else
	valor_recargo := cg."cuotaAdministrativa";
end if;

-- Insertar nuevo historico plan contrato
INSERT INTO historico_plan_contrato (
	"sisActualizado", "sisCreado",
	"sisHabilitado", "totalInscripcionPlan",
	"valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
	"capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
	"valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
	"totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
	"totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba", 
	"valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
) VALUES (
	now(), now(),
	'A', nuevo_plan.inscripcion,
	0, valor_nueva_incripcion, nuevo_plan.precio,
	0, abonos_capital_actual, saldo_capital,
	tasa_administrativa, total_tasa_administrativa_cobrada, numero_cuotas_pasar,
	0, 0, total_monto_cobrado,0, 0,
	valor_recargo, id_nuevo_contrato, id_nuevo_plan, numero_cuotas_pasar, 
	0, 'N', 0, 0
) RETURNING id INTO id_historico;

-- comprobaciones del calculo correcto
capital1 := cuota1."abonoCapital" * (hpc1."totalCuotasCobradas" - 1);
capital2 := cuota2."abonoCapital" * (hpc2."totalCuotasCobradas" - 1);
capital3 := abonos_capital_actual;
saldo_pagado_tasa := (SELECT abs((capital1 + capital2) - capital3));
tasa_administrativa1 := (cuota1."valorTasaAdministrativa" * (hpc1."totalCuotasCobradas" - 1)) + (cuota1."valorImpuesto" * (hpc1."totalCuotasCobradas" - 1));
tasa_administrativa2 := (cuota2."valorTasaAdministrativa" * (hpc2."totalCuotasCobradas" - 1)) + (cuota2."valorImpuesto" * (hpc2."totalCuotasCobradas" - 1));
tasa_administrativa3 := (tasa_administrativa * numero_cuotas_pasar) + (impuesto * numero_cuotas_pasar) + tasa_administrativa_restante;
diferencia := tasa_administrativa1 + tasa_administrativa2 - tasa_administrativa3;
valor_absoluto := (SELECT round((SELECT abs(diferencia)), 3));

if (saldo_pagado_tasa <> valor_absoluto) then
	RETURN '{"correcto":false, "message":"Error en los calculo de comprobacion de la diferencia de la tase administrativa y el capital"}';
end if;

PERFORM public.actualizar_grupo(grupos, montos);

PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, valor_pagado_cuota,
	tasa_administrativa, impuesto, abono_capital,
	'N', pms, numero_cuotas_pasar, id_historico);

PERFORM public.registrar_id_contratos(contrato1.id);
PERFORM public.registrar_id_contratos(contrato2.id);

RETURN '{"correcto":true, "message":"Se unificaron los contratos correctamente"}';
ROLLBACK;
END;
$$ LANGUAGE 'plpgsql';
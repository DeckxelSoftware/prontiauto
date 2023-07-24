----------- REACTIVACION CONTRATO -----------

create or replace function reactivacion_contrato(jsonpar CHAR VARYING) returns CHARACTER VARYING as $$
Declare
contrato public.contrato;
plan public.plan;
cg public.configuracion_general;
hpc public.historico_plan_contrato;
cuota public.cuota;
valor_nueva_incripcion NUMERIC;
tasa_cambio_contrato NUMERIC;
total_pagado_primer_plan NUMERIC;
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
saldo_pagado_tasa NUMERIC;
tasa_administrativa1 NUMERIC;
tasa_administrativa2 NUMERIC;
diferencia NUMERIC;
valor_absoluto NUMERIC;
valor_pagado_cuota NUMERIC;
fecha_fin DATE;
abono_restante NUMERIC;
numero_cuotas_en_mora INTEGER;
tasa_administrativa_restante NUMERIC;
------- json value ---------
id_hpc INTEGER;
id_contrato INTEGER;
id_plan INTEGER;
nc DOUBLE PRECISION;
pms INTEGER;
observacion CHARACTER VARYING;
fecha_inicio DATE;
fic DATE;
id_vendedor INTEGER;
porcentaje_tasa BOOLEAN;
nueva_cuota NUMERIC;
BEGIN

id_hpc := (jsonpar::jsonb->>'idHistoricoPlanContrato')::INTEGER;
id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
id_plan := (jsonpar::jsonb->>'idPlan')::INTEGER;
nc := (jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
observacion := (jsonpar::jsonb->>'observacion')::VARCHAR;
fecha_inicio := (jsonpar::jsonb->>'fechaInicio')::DATE;
fic := (jsonpar::jsonb->>'fechaInicioCobro')::DATE;
id_vendedor := (jsonpar::jsonb->>'idVendedor')::INTEGER;
porcentaje_tasa := (jsonpar::jsonb->>'porcentajeTasa')::BOOLEAN;
nueva_cuota := (jsonpar::jsonb->>'cuota')::NUMERIC;

cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
plan := (SELECT pl FROM plan pl WHERE id=id_plan);
contrato := (SELECT co FROM contrato co WHERE id=id_contrato);
hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE id=id_hpc);
cuota := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc.id LIMIT 1);

-- verificar que el contrato este en estado registrado o en proceso
IF contrato.estado <> 'REG' AND contrato.estado <> 'EPR' THEN
	RETURN '{"correcto":false, "message":"El contrato no se encuentra en estado registrado o en proceso"}';
END IF;

-- verificar que el contrato anterior tengan la primera cuota pagada
numero_cuotas_en_mora := (SELECT count(*) FROM cuota WHERE "idHistoricoPlanContrato"=hpc.id AND "estaMora" = 'S');
IF (numero_cuotas_en_mora < 1) THEN
	RETURN '{"correcto":false, "message":"El contrato debe tener minimo dos cuotas en mora"}';
END IF;

-- calcular fecha fin de contrato
fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

-- se desactiva y se cambia el estado de los contratos
UPDATE contrato SET "sisHabilitado"='I', estado='REA' WHERE id = id_contrato;

-- calcular nueva cuota
abono_capital := plan.precio/pms;
iva := (cg."ivaPorcentaje"/100);
tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
impuesto := tasa_administrativa * iva;

-- crear nuevo contrato
INSERT INTO contrato (
	"sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
	"fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
	"fechaIniciaCobro", estado, "plazoMesSeleccionado", version, "cuotaActual",
	"tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
	"nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo", "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
) VALUES (
	now(), now(), 'A', nc, fecha_inicio, fecha_fin, 0, 0,
	observacion, fic, 'REG', pms, (contrato.version + 1), nueva_cuota,
	contrato."tipoDocumentoIdentidad", contrato."documentoIdentidad", plan.modelo, plan.precio,
	contrato."nombresCliente", contrato."apellidosCliente", contrato."nombreGrupo", 0, contrato."idClienteEnGrupo", id_vendedor, 0 , 0
) RETURNING id INTO id_contrato;

-- Valor nueva incripcion y total cobro inscripcion
tasa_cambio_contrato := cg."tasaCambioContrato"/100;
valor_nueva_incripcion := (plan.precio * tasa_cambio_contrato);
IF(porcentaje_tasa = true) THEN
    valor_nueva_incripcion := plan.inscripcion;
END IF;

-- Total Pagado primer plan
total_pagado_primer_plan := hpc."totalMontoCobrado" - cuota."valorCuota";

-- calcular cuotas a pasar
numero_cuotas_pasar := (SELECT floor(total_pagado_primer_plan / nueva_cuota));

-- generar tabla de cuotas
valor_pagado_cuota := total_pagado_primer_plan - (numero_cuotas_pasar * nueva_cuota);
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

saldo_capital := plan.precio - abonos_capital_actual;

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
	'A', 0,
	0, valor_nueva_incripcion, plan.precio,
	0, abonos_capital_actual, saldo_capital,
	tasa_administrativa, total_tasa_administrativa_cobrada, numero_cuotas_pasar,
	0, hpc."totalCuotasMora", total_monto_cobrado, 0, 0,
	0, id_contrato, id_plan, numero_cuotas_pasar, 
	0, 'N', 0, 0
) RETURNING id INTO id_historico;

-- comprobaciones del calculo correcto
capital1 := cuota."abonoCapital" * (hpc."totalCuotasCobradas" - 1);
capital2 := abonos_capital_actual;
saldo_pagado_tasa := (SELECT round((SELECT abs(capital1 - capital2)), 3));
tasa_administrativa1 := (cuota."valorTasaAdministrativa" * (hpc."totalCuotasCobradas" - 1)) + (cuota."valorImpuesto" * (hpc."totalCuotasCobradas" - 1));
tasa_administrativa2 := (tasa_administrativa * numero_cuotas_pasar) + (impuesto * numero_cuotas_pasar) + tasa_administrativa_restante;
diferencia := tasa_administrativa1 - tasa_administrativa2;
valor_absoluto := (SELECT round((SELECT abs(diferencia)), 3));

if (saldo_pagado_tasa <> valor_absoluto) then
	RETURN '{"correcto":false, "message":"Error en los calculo de comprobacion de la diferencia de la tase administrativa y el capital"}';
end if;

PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, valor_pagado_cuota,
	tasa_administrativa, impuesto, abono_capital,
	'N', pms, numero_cuotas_pasar, id_historico);

PERFORM public.registrar_id_contratos(contrato.id);

RETURN '{"correcto":true, "message":"Se reactivo correctamente el contrato"}';
ROLLBACK;
end;
$$ language 'plpgsql';


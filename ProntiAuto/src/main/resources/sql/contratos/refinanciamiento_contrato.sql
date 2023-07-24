----------- REFINANCIAMIENTO CONTRATO -----------

create or replace function refinanciamiento_contrato(jsonpar CHAR VARYING) returns CHARACTER VARYING as $$
Declare
cuota public.cuota;
plan public.plan;
contrato public.contrato;
hpc public.historico_plan_contrato;
cg public.configuracion_general;
numero_cuotas_pagar_refinanciamiento INTEGER;
valor_cuotas_refinanciamiento NUMERIC;
numero_cuotas_faltantes_refinanciamiento INTEGER;
valor_pendiente_pago NUMERIC;
numero_cuotas_restantes_sin_mora INTEGER;
valor_agregarse_cuota NUMERIC;
valor_capital_refinanciado NUMERIC;
valor_agregarse_capital NUMERIC;
nuevo_abono_capital NUMERIC;
tasa_administrativa NUMERIC;
impuesto NUMERIC;
abonos_capital_actual NUMERIC;
numero_cuotas_pasar INTEGER;
id_historico INTEGER;
resto_inicio NUMERIC;
resto_final NUMERIC;
numero_cuotas_inicio INTEGER;
numero_cuotas_pagadas INTEGER;
impuesto_tasa_administrativa NUMERIC;
saldo_capital NUMERIC;
total_tasa_administrativa_cobrada NUMERIC;
total_monto_cobrado NUMERIC;
iva NUMERIC;
nueva_cuota NUMERIC;
--------- josn value ---------
id_hpc INTEGER;
id_contrato INTEGER;
id_plan INTEGER;
pms INTEGER;
BEGIN

id_hpc := (jsonpar::jsonb->>'idHistoricoPlanContrato')::INTEGER;
id_contrato := (jsonpar::jsonb->>'idContrato')::INTEGER;
id_plan := (jsonpar::jsonb->>'idPlan')::INTEGER;
pms := (jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;

hpc := (SELECT hpc1 FROM historico_plan_contrato hpc1 WHERE id=id_hpc);
plan := (SELECT pl FROM plan pl WHERE id=id_plan);
cuota := (SELECT cuo FROM cuota cuo WHERE "idHistoricoPlanContrato"=hpc.id LIMIT 1);
contrato := (SELECT c FROM contrato c WHERE id=id_contrato);
cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
numero_cuotas_pagar_refinanciamiento := (SELECT round(hpc."totalCuotasMoraActual" * 0.25));
valor_cuotas_refinanciamiento := (cuota."valorCuota" * numero_cuotas_pagar_refinanciamiento);
numero_cuotas_faltantes_refinanciamiento := (SELECT round(hpc."totalCuotasMoraActual" - (hpc."totalCuotasMoraActual" * 0.25)));
valor_pendiente_pago := (cuota."valorCuota" * numero_cuotas_faltantes_refinanciamiento);
numero_cuotas_restantes_sin_mora := pms - hpc."totalCuotasMoraActual" - numero_cuotas_faltantes_refinanciamiento;
valor_agregarse_cuota := (valor_pendiente_pago / numero_cuotas_restantes_sin_mora);
nueva_cuota := (valor_agregarse_cuota + cuota."valorCuota");
valor_capital_refinanciado := (cuota."abonoCapital" * numero_cuotas_faltantes_refinanciamiento);
valor_agregarse_capital := (valor_capital_refinanciado / numero_cuotas_restantes_sin_mora);
nuevo_abono_capital := (valor_agregarse_capital + cuota."abonoCapital");

-- calcular tasa administrativa e impuesto
iva := (cg."ivaPorcentaje"/100);
tasa_administrativa := (nueva_cuota - nuevo_abono_capital)/(iva + 1);
impuesto := tasa_administrativa * iva;

-- crear nuevo contrato
INSERT INTO contrato (
	"sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
	"fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
	"fechaIniciaCobro", "estado", "plazoMesSeleccionado", version, "cuotaActual",
	"tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
	"nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo", "idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar"
) VALUES (
	now(), contrato."sisCreado", 'A', contrato."numeroDeContrato",
    contrato."fechaInicio", contrato."fechaFin", contrato."dsctoInscripcion", contrato."dsctoPrimeraCuota", contrato.observacion,
	contrato."fechaIniciaCobro", 'ERP', contrato."plazoMesSeleccionado", (contrato.version + 1), contrato."cuotaActual",
	contrato."tipoDocumentoIdentidad", contrato."documentoIdentidad", plan.modelo, plan.precio,
	contrato."nombresCliente", contrato."apellidosCliente", contrato."nombreGrupo", contrato."dsctoRecargo", contrato."idClienteEnGrupo", contrato."idVendedor", 0 , 0
) RETURNING id INTO id_contrato;

-- sacar los resto de las cuotas que no se pagaron
resto_inicio := (SELECT "valorPagadoCuota" FROM cuota WHERE "idHistoricoPlanContrato" = hpc.id AND "numeroCuota" = (hpc."totalCuotasCobradas" + 1));
resto_final := (SELECT "valorPagadoCuota" FROM cuota WHERE "idHistoricoPlanContrato" = hpc.id AND "numeroCuota" = (pms - hpc."numCuotasAbajoHaciaArriba" - 1));


-- crear nuevo historico plan contrato
numero_cuotas_pasar := hpc."totalCuotasCobradas" + numero_cuotas_pagar_refinanciamiento + numero_cuotas_faltantes_refinanciamiento;
numero_cuotas_pagadas := hpc."totalCuotasCobradas" + numero_cuotas_pagar_refinanciamiento;
abonos_capital_actual := (cuota."abonoCapital" * numero_cuotas_pagadas);
total_tasa_administrativa_cobrada := (cuota."valorTasaAdministrativa" * numero_cuotas_pagadas);
total_monto_cobrado := (cuota."valorCuota" * numero_cuotas_pagadas);

-- sumar posibles cuotas sobrantes
impuesto_tasa_administrativa := impuesto + tasa_administrativa;
if(resto_inicio > impuesto_tasa_administrativa) then
	abonos_capital_actual := abonos_capital_actual + resto_inicio - impuesto_tasa_administrativa;
	total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + cuota."valorTasaAdministrativa";
	total_monto_cobrado := total_monto_cobrado + resto_inicio;
end if;

if(resto_final > impuesto_tasa_administrativa) then
	abonos_capital_actual := abonos_capital_actual + resto_final - impuesto_tasa_administrativa;
	total_tasa_administrativa_cobrada := total_tasa_administrativa_cobrada + cuota."valorTasaAdministrativa";
	total_monto_cobrado := total_monto_cobrado + resto_final;
end if;

saldo_capital := (nueva_cuota * (numero_cuotas_restantes_sin_mora - 2)) + (nueva_cuota - resto_inicio) + (nueva_cuota - resto_final);

-- Insertar nuevo historico plan contrato
INSERT INTO historico_plan_contrato (
    "sisActualizado", "sisCreado", "sisHabilitado", "totalInscripcionPlan",
	"valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
	"capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
	"valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
	"totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
	"totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba", 
	"valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
) VALUES (
	now(), hpc."sisCreado", 'A', hpc."totalInscripcionPlan",
	hpc."valorDsctoInscripcion", hpc."totalCobroInscripcion", hpc."capitalTotal",
	valor_capital_refinanciado, abonos_capital_actual, saldo_capital,
	hpc."valorTasaAdministrativa", total_tasa_administrativa_cobrada, numero_cuotas_pagadas,
	0, hpc."totalCuotasMora", total_monto_cobrado, 0,
    0, 0, id_contrato, plan.id, hpc."numCuotasAbajoHaciaArriba", 
	0, 'N', 0, 0
) RETURNING id INTO id_historico;

PERFORM public.cuotas_refinanciamiento(cuota, plan, nueva_cuota, nuevo_abono_capital, tasa_administrativa, impuesto, resto_inicio, numero_cuotas_pasar, resto_final, pms, id_historico);

INSERT INTO public.refinanciamiento(
	"sisActualizado", "sisCreado", "sisHabilitado",
	"cuotasRestantesSinMora", "fechaRefinanciamiento", "totalCuotas",
	"totalCuotasFaltantesRefinanciamiento", "totalCuotasMora", "totalCuotasPagadas",
	"totalCuotasPagadasRefinanciamiento", "valorAgregarseCuota", "valorCuota",
	"valorPendientePago", "idHistoricoPlanContrato")
VALUES (
	now(), now(), 'A',
	numero_cuotas_restantes_sin_mora, now(), pms,
	numero_cuotas_faltantes_refinanciamiento, hpc."totalCuotasMora", hpc."totalCuotasCobradas",
	numero_cuotas_pagar_refinanciamiento, valor_agregarse_cuota, nueva_cuota,
	valor_pendiente_pago, id_historico
);

PERFORM public.registrar_id_contratos(contrato.id);

RETURN '{"correcto":true, "message":"Se refinanció el contrato correctamente"}';
ROLLBACK;
end;
$$ language 'plpgsql';


--------- CREAR CONTRATO ----------

CREATE OR replace FUNCTION crear_contrato(jsonpar CHAR VARYING) returns CHARACTER VARYING as $$
DECLARE
usuario public.usuario;
plan public.plan;
gp public.grupo;
cg public.configuracion_general;
id_cliente INTEGER;
id_vendedor INTEGER;
id_plan INTEGER;
nc DOUBLE PRECISION;
fecha_inicio DATE;
dscto_ins NUMERIC;
dscto_pc NUMERIC;
observacion CHARACTER VARYING;
fic DATE;
pms INTEGER;
nueva_cuota NUMERIC;
medio_captacion VARCHAR(255);
id_grupo INTEGER;
ng INTEGER;
id_cg INTEGER;
existe_grupo INTEGER;
grupo_lleno INTEGER;
id_contrato_anterior INTEGER;
id_contrato INTEGER;
id_hpc INTEGER;
fecha_fin DATE;
abono_capital NUMERIC;
iva NUMERIC;
tasa_administrativa NUMERIC;
impuesto NUMERIC;
total_cobro_inscripcion NUMERIC;
existe_cliente_en_grupo INTEGER;
BEGIN

id_contrato_anterior := (jsonpar::jsonb->>'idContrato')::INTEGER;
id_cliente :=(jsonpar::jsonb->>'idCliente')::INTEGER;
id_vendedor :=(jsonpar::jsonb->>'idVendedor')::INTEGER;
id_plan :=(jsonpar::jsonb->>'idPlan')::INTEGER;
nc :=(jsonpar::jsonb->>'numeroContrato')::DOUBLE PRECISION;
fecha_inicio :=(jsonpar::jsonb->>'fechaInicio')::DATE;
dscto_ins := (jsonpar::jsonb->>'dsctoInscripcion')::NUMERIC;
dscto_pc :=(jsonpar::jsonb->> 'dsctoPrimeraCuota')::NUMERIC;
observacion :=(jsonpar::jsonb->>'observacion')::CHARACTER VARYING;
fic :=(jsonpar::jsonb->>'fechaInicioCobro')::DATE;
pms :=(jsonpar::jsonb->>'plazoMesSeleccionado')::INTEGER;
nueva_cuota :=(jsonpar::jsonb->>'cuota')::NUMERIC;
medio_captacion :=(jsonpar::jsonb->>'medioCaptacion')::VARCHAR(255);


usuario := (SELECT u FROM cliente c INNER JOIN usuario u ON c."idUsuario"=u.id WHERE c.id=id_cliente);
plan := (SELECT pl FROM plan pl WHERE id=id_plan);
cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);

-- verificar que exista un grupo para el contrato del cliente y que aun no este lleno
existe_grupo := (SELECT count(*) FROM grupo WHERE "totalContratosUsados" < 144);
IF(existe_grupo = 0) THEN
	INSERT INTO grupo ("sisActualizado", "sisCreado", "sisHabilitado", "nombreGrupo", "sumatoriaMontoMeta", "totalContratosPermitidos", "totalContratosUsados")
	values (now(), now(), 'A', 1, 0, 144, 1) RETURNING id, "nombreGrupo" INTO id_grupo, ng;
ELSE
	-- verificar si en el grupo ya existe un contrato para el cliente
	gp := (SELECT g FROM grupo g WHERE g."totalContratosUsados" < 144 ORDER BY g."sisCreado" ASC LIMIT 1);
	existe_cliente_en_grupo := (SELECT count(*) FROM cliente_en_grupo ceg WHERE ceg."idCliente" = id_cliente AND ceg."idGrupo" = gp.id);

	IF (existe_cliente_en_grupo = 0) THEN
		UPDATE grupo SET "sisActualizado"=now(), "totalContratosUsados"=(gp."totalContratosUsados" + 1) WHERE id=gp.id;
		id_grupo := gp.id;
		ng := gp."nombreGrupo";
	ELSE
		INSERT INTO grupo ("sisActualizado", "sisCreado", "sisHabilitado", "nombreGrupo", "sumatoriaMontoMeta", "totalContratosPermitidos", "totalContratosUsados")
		values (now(), now(), 'A', (gp."nombreGrupo" + 1), 0, 144, 1) RETURNING id, "nombreGrupo" INTO id_grupo, ng;
	END IF;
END IF;

-- crear cliente en grupo
INSERT INTO cliente_en_grupo ("sisActualizado", "sisCreado", "sisHabilitado", "idCliente", "idGrupo") values (now(), now(), 'A', id_cliente, id_grupo) RETURNING id INTO id_cg;

-- calcular fecha fin de contrato
fecha_fin := (select (fic::date + (concat(pms ,' month'))::interval));

-- calcular nueva_cuota
abono_capital := plan.precio/pms;
iva := (cg."ivaPorcentaje"/100);
tasa_administrativa := (nueva_cuota - abono_capital)/(iva + 1);
impuesto := tasa_administrativa * iva;

-- crear nuevo contrato
INSERT INTO contrato (
	"sisActualizado", "sisCreado", "sisHabilitado", "numeroDeContrato",
	"fechaInicio", "fechaFin", "dsctoInscripcion", "dsctoPrimeraCuota", observacion,
	"fechaIniciaCobro", "estado", "plazoMesSeleccionado", version, "cuotaActual",
	"tipoDocumentoIdentidad", "documentoIdentidad", "planSeleccionado", "precioPlanSeleccionado",
	"nombresCliente", "apellidosCliente", "nombreGrupo", "dsctoRecargo",
	"idClienteEnGrupo", "idVendedor", "valorFondo", "cuotaACobrar", "medioCaptacion"
) VALUES (
	now(), now(), 'A', nc, fecha_inicio, fecha_fin, dscto_ins, dscto_pc,
	observacion, fic, 'REG', pms, 1, nueva_cuota,
	usuario."tipoDocumentoIdentidad", usuario."documentoIdentidad", plan.modelo, plan.precio,
	usuario.nombres, usuario.apellidos, ng, 0, id_cg, id_vendedor, 0 , 0, medio_captacion
) RETURNING id INTO id_contrato;

-- aplicar descuento de inscripcion y descuento de primera nueva_cuota
total_cobro_inscripcion := plan.inscripcion - (plan.inscripcion * (dscto_ins/100));

-- Insertar nuevo historico plan contrato
INSERT INTO historico_plan_contrato (
	"sisActualizado", "sisCreado", "sisHabilitado","totalInscripcionPlan",
	"valorDsctoInscripcion", "totalCobroInscripcion", "capitalTotal",
	"capitalPorRefinanciamiento", "abonosCapitalActual", "saldoCapital",
	"valorTasaAdministrativa", "totalTasaAdministrativaCobrada", "totalCuotasCobradas",
	"totalCuotasMoraActual", "totalCuotasMora", "totalMontoCobrado", "valorDsctoPrimeraCuota",
	"totalCobroPrimeraCuota", "valorRecargo", "idContrato", "idPlan", "numCuotasAbajoHaciaArriba", "valorADevolver", "inscripcionEstaPagada", "valorPagadoInscripcion", "totalDispositivoCobrado"
) VALUES (
	now(),now(),1, plan.inscripcion,
	dscto_ins, total_cobro_inscripcion, plan.precio,
	0, 0, plan.precio,
	tasa_administrativa, 0, 0,
	0, 0, 0, dscto_pc,
	0,0, id_contrato, id_plan,
    0, 0, 'N', 0, 0
) RETURNING id INTO id_hpc;

PERFORM public.crear_tabla_cuotas(fic, nueva_cuota, 0,
	tasa_administrativa, impuesto, abono_capital,
	'N', pms, 0, id_hpc);

-- actualizar los id de los contratos anteriores
if(id_contrato_anterior is not null) then
	PERFORM public.registrar_id_contratos(id_contrato_anterior);
end if;

RETURN '{"correcto":true, "message":"Se creo el contrato correctamente"}';
ROLLBACK;
END;
$$ LANGUAGE 'plpgsql';
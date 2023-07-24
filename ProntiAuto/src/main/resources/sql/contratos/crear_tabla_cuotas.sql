--------- CREAR TABLA CUOTAS ---------

create or replace function crear_tabla_cuotas(fecha_cobro_par date, valor_cuota_par NUMERIC, valor_pagado_cuota_par NUMERIC, valor_tasa_administrativa_par NUMERIC, valor_impuesto_par NUMERIC, abono_capital_par NUMERIC, esta_mora_par CHAR VARYING, plazo_par integer, cuotas_pagadas_par integer, id_historico_plan_contrato_par integer) returns void as $$
Declare
cont int4;
mes_siguiente date;
mes_siguiente0 date;
esta_pagado CHAR VARYING;
inicio_pago integer;
valor_pagado_cuota NUMERIC;
begin
cont:=1;
inicio_pago:=plazo_par - cuotas_pagadas_par;

while(cont<=plazo_par)
    loop
    mes_siguiente:=(select (fecha_cobro_par::date + (concat(cont ,'month'))::interval));
    esta_pagado:='N';
    valor_pagado_cuota:=0;

        if(cont > inicio_pago) then
            esta_pagado := 'S';
            valor_pagado_cuota := valor_cuota_par;
        end if;
        if(cont = inicio_pago) then
            valor_pagado_cuota := valor_pagado_cuota_par;
        end if;
        insert into cuota (
            "sisActualizado", "sisCreado", "sisHabilitado", 
            "idHistoricoPlanContrato", "fechaCobro", "numeroCuota",
            "valorCuota", "valorPagadoCuota", "valorTasaAdministrativa",
            "valorImpuesto", "abonoCapital", "estaPagado",
            "estaMora", "dispositivo", "rastreo", 
            "dispositivoEstaPagado", "rastreoEstaPagado", "valorPagadoDispositivo", "valorPagadoRastreo"
        ) values (
            now(),now(), 'A',
            id_historico_plan_contrato_par, mes_siguiente, cont,
            valor_cuota_par, valor_pagado_cuota, valor_tasa_administrativa_par, valor_impuesto_par, abono_capital_par, esta_pagado,
            esta_mora_par,  0, 0, 
            'N', 'N', 0, 0
        );
    cont:=cont+1;
    end loop;
end;
$$ language 'plpgsql';

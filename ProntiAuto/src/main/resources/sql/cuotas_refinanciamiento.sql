------------ CUOTAS_REFINANCIAMIENTO ------------

create or replace function cuotas_refinanciamiento(cuota_par public.cuota, plan_par public.plan, valor_cuota_par NUMERIC, abono_capital_par NUMERIC, tasa_administrativa_par NUMERIC, impuesto_par NUMERIC, resto_inicio_par NUMERIC, numero_cuotas_inicio_par NUMERIC, resto_final_par NUMERIC, pms_par INTEGER, id_historico_par INTEGER) returns void as $$
Declare
cg public.configuracion_general;
hpc public.historico_plan_contrato;
cont INTEGER;
mes_siguiente DATE;
esta_pagado CHAR VARYING;
valor_cuota NUMERIC;
valor_pagado_cuota NUMERIC;
valor_tasa_administrativa NUMERIC;
valor_impuesto NUMERIC;
abono_capital NUMERIC;
cuotas_final INTEGER;
begin
cont:=1;
cg := (SELECT conf FROM configuracion_general conf ORDER BY conf."sisCreado" DESC LIMIT 1);
hpc := (SELECT h FROM historico_plan_contrato h WHERE id = id_historico_par);
cuotas_final := pms_par - hpc."numCuotasAbajoHaciaArriba";

while(cont <= pms_par)
    loop
        mes_siguiente := (select (cuota_par."fechaCobro"::date + (concat(cont ,'month'))::interval));
        esta_pagado := 'N';
        valor_pagado_cuota := 0;

        if(cont <= numero_cuotas_inicio_par OR cont > cuotas_final) then
            valor_cuota := cuota_par."valorCuota";
            valor_tasa_administrativa := cuota_par."valorTasaAdministrativa";
            valor_impuesto := cuota_par."valorImpuesto";
            abono_capital := cuota_par."abonoCapital";
            valor_pagado_cuota := cuota_par."valorCuota";
            esta_pagado := 'S';
        elseif(cont = (numero_cuotas_inicio_par + 1)) then
            valor_pagado_cuota := resto_inicio_par;
            valor_cuota := valor_cuota_par;
            valor_tasa_administrativa := tasa_administrativa_par;
            valor_impuesto := impuesto_par;
            abono_capital := abono_capital_par;
        elseif(cont = (cuotas_final - 1)) then
            valor_pagado_cuota := resto_final_par;
            valor_cuota := valor_cuota_par;
            valor_tasa_administrativa := tasa_administrativa_par;
            valor_impuesto := impuesto_par;
            abono_capital := abono_capital_par;
        else
            valor_cuota := valor_cuota_par;
            valor_tasa_administrativa := tasa_administrativa_par;
            valor_impuesto := impuesto_par;
            abono_capital := abono_capital_par;
        end if;

        insert into cuota (
            "sisActualizado", "sisCreado", "sisHabilitado", 
            "idHistoricoPlanContrato", "fechaCobro", "numeroCuota",
            "valorCuota", "valorPagadoCuota", "valorTasaAdministrativa",
            "valorImpuesto", "abonoCapital", "estaPagado",
            "estaMora", "dispositivo", "rastreo", 
            "dispositivoEstaPagado", "rastreo_esta_pagado"
        ) values (
            now(),now(),'A',
            id_historico_par, mes_siguiente, cont,
            valor_cuota, valor_pagado_cuota, valor_tasa_administrativa, 
            valor_impuesto, abono_capital, esta_pagado,
            'N', 0, 0, 
            'N', 'N'
        );
        cont:=cont+1;
    end loop;
end;
$$ language 'plpgsql';


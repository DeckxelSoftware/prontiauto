package com.ec.prontiauto.repositoryImpl;

import com.ec.prontiauto.abstracts.AbstractRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class BalanceRepository extends AbstractRepository<Object, Integer> {

    public Object calcularBalanceGeneral(Integer identificador, Integer anio){
        List<String> params = Arrays.asList("anio_periodo","identificador_cuenta","tipo_movimiento", "identificador_nivel");
        List<Object> values = Arrays.asList(anio,identificador.toString(),"M", 1);
        return this.callSimpleStoreProcedure("generar_balance_general", params, values);
    }

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        return null;
    }
}

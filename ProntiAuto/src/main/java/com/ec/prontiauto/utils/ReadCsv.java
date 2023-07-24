package com.ec.prontiauto.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.ec.prontiauto.exception.ApiRequestException;
import com.opencsv.bean.CsvToBeanBuilder;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReadCsv {

    @PersistenceContext
    EntityManager entityManager;

    public <T> void readAndLoadDataCsv(Class<T> clazz, String fileName) {
        try {
            List<T> listData = new CsvToBeanBuilder<T>(new FileReader(fileName))
                    .withType(clazz).build()
                    .parse();
            listData.forEach(data -> {
                entityManager.persist(data);
            });
        } catch (IOException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    public <T> void readAndLoadDataWithRelationCsv(Class<?>[] clazzFathers, Class<T> clazzChild, String[] params,
            String[] references, String fileName) {
        try {
            List<T> listData = new CsvToBeanBuilder<T>(new FileReader(fileName))
                    .withType(clazzChild).build()
                    .parse();
            listData.forEach(data -> {
                try {
                    for (int i = 0; i < clazzFathers.length; i++) {
                        int id = data.getClass().getMethod("get" + references[i] + "1").invoke(data).hashCode();
                        Object dataFather = entityManager.find(clazzFathers[i], id);
                        data.getClass().getMethod("set" + params[i], clazzFathers[i]).invoke(data, dataFather);
                    }
                    entityManager.persist(data);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (IOException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    public void deleteDataFromTable(List<Class<?>> clazz) {
        for (Class<?> clazz1 : clazz) {
            entityManager.createQuery("DELETE FROM " + clazz1.getSimpleName()).executeUpdate();
        }
    }

}

package com.ec.prontiauto.abstracts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class AbstractMapper {
    public <T, K> K mapper(T inputClass, Class<K> outputClass) {
        ModelMapper modelMapper = new ModelMapper();
        K newOutputClass = modelMapper.map(inputClass, outputClass);
        return newOutputClass;
    }

    public <T, K> List<K> mapperList(List<T> inputClass, Class<K> outputClass) {
        ModelMapper modelMapper = new ModelMapper();
        List<K> newOutputClass = inputClass.stream()
                .map(input -> modelMapper.map(input, outputClass))
                .collect(Collectors.toList());
        return newOutputClass;
    }
}

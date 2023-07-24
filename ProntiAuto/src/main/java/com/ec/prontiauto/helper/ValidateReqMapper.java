package com.ec.prontiauto.helper;

import java.util.List;

public class ValidateReqMapper {
    private String key;
    private List<String> params;

    public ValidateReqMapper(String key, List<String> params) {
        this.key = key;
        this.params = params;
    }

    public ValidateReqMapper(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
    
}

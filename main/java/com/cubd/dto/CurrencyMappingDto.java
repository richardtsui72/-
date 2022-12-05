package com.cubd.dto;

import com.cubd.po.CurrencyMappingEntity;

public class CurrencyMappingDto {

    private String engName;
    private String name;

    public CurrencyMappingDto() {}

    public CurrencyMappingDto(String engName, String name) {
        this.engName = engName;
        this.name = name;
    }

    public CurrencyMappingDto(CurrencyMappingEntity currencyMappingEnntity) {
        this.engName = currencyMappingEnntity.getEngName();
        this.name = currencyMappingEnntity.getChName();
    }

    public String getEngName() {
        return engName;
    }
    public void setEngName(String engName) {
        this.engName = engName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}

package com.cubd.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cubd.dto.CurrencyMappingDto;

@Entity
@Table(name = "CURRENCY_MAPPING")
public class CurrencyMappingEntity {
    @Id
    private String engName;
    private String chName;

    public CurrencyMappingEntity() {}

    public CurrencyMappingEntity(CurrencyMappingDto currencyMappingDto) {

        this.engName = currencyMappingDto.getEngName();
        this.chName = currencyMappingDto.getName();
    }

    public String getEngName() {
        return engName;
    }
    public void setEngName(String engName) {
        this.engName = engName;
    }
    public String getChName() {
        return chName;
    }
    public void setChName(String chName) {
        this.chName = chName;
    }
}

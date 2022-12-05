package com.cubd.bean;

import java.util.List;

public class CoinInfoResp {

    private String updateTime;

    private List<CoinInfo> coinInfos ;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<CoinInfo> getCoinInfos() {
        return coinInfos;
    }

    public void setCoinInfos(List<CoinInfo> coinInfos) {
        this.coinInfos = coinInfos;
    }

}

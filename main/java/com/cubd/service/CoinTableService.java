package com.cubd.service;

import com.cubd.bean.CoinTable;

import java.util.List;


public interface CoinTableService {
    public List<CoinTable> queryAllCoinsList();

    public CoinTable queryByCoinType(String coinType);

    public void insertCoin(CoinTable coinTable);

    public void updateCoin(CoinTable coinTable);

    public void deleteCoin(CoinTable coinTable);
}

package com.cubd.service.impl;

import com.cubd.bean.CoinTable;
import com.cubd.repository.CoinTableRepository;
import com.cubd.service.CoinTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CoinTableServiceImpl implements CoinTableService{
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private CoinTableRepository coinTableRepository;

    @Override
    public List<CoinTable> queryAllCoinsList()
    {
        Iterable<CoinTable> iterable = coinTableRepository.findAll();
        List<CoinTable> list=new ArrayList<>();
        Iterator<CoinTable> iterator = iterable.iterator();
        while(iterator.hasNext()){
            CoinTable next = iterator.next();
            list.add(next);
        }
        return list;
    }

    @Override
    public CoinTable queryByCoinType(String coinType) {
        CoinTable coins = coinTableRepository.findByCoinType(coinType);

        return coins;
    }

    @Override
    public void insertCoin(CoinTable coinTable)
    {
        try {
            coinTableRepository.save(coinTable);
        }catch (Exception e){
            logger.error("Insert fail : "+e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateCoin(CoinTable coinTable)
    {
        try {
            String coinType = coinTable.getCoinType();
            CoinTable coinTable_New = coinTableRepository.findByCoinType(coinType);
            coinTable_New.setChineseName(coinTable.getChineseName());

            coinTableRepository.save(coinTable_New);
        }catch (Exception e){
            logger.error("Update fail : "+e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteCoin(CoinTable coinTable)
    {
        try{
            if(coinTable != null)
                coinTableRepository.delete(coinTable);
        }catch (Exception e) {
            logger.error("Delete fail : "+e.getMessage());
            throw e;
        }
    }
}

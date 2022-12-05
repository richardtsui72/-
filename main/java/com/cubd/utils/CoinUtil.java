package com.cubd.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cubd.bean.CoinInfo;
import com.cubd.bean.CoinTable;
import com.cubd.service.CoinTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CoinUtil {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private CoinTableService coinTableService;

    public List<CoinInfo> parseCoinMap(JsonNode node , Set<String> keySet) throws JsonProcessingException {
        List<CoinInfo> coinInfos = new ArrayList<>();

        for(String coinType : keySet)
        {
            Map coinMap = mapper.convertValue(node.get(coinType),Map.class);
            if (Objects.isNull(coinMap))
                return coinInfos;

            CoinInfo coinInfo = new CoinInfo();

            double rate = (double) coinMap.get("rate_float");

            coinInfo.setCoinType(coinType);
            coinInfo.setCoinRate(rate);

            coinInfos.add(coinInfo);
        }

        return coinInfos;
    }

    public String parseTimeFormat(String dateTime) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);
        Date date = dateFormat.parse(dateTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return sdf.format(calendar.getTime());
    }

    public void addChineseName(List<CoinInfo> coinInfos)
    {
        if(Objects.isNull(coinInfos))
            return;
        for(CoinInfo coinInfo : coinInfos)
        {
            String coinType = coinInfo.getCoinType();
            CoinTable coinTable = coinTableService.queryByCoinType(coinType);
            coinInfo.setCoinCHNName(coinTable.getChineseName());
        }
    }

}

package com.cubd.excutor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cubd.bean.CoinInfo;
import com.cubd.bean.CoinInfoResp;
import com.cubd.utils.CoinUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CoinExecutor {

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private CoinUtil coin;

    public CoinInfoResp parseCoin2Bean(String coinStr)
    {
        CoinInfoResp coinInfoResp = new CoinInfoResp();

        try {
            JsonNode jsonNode = mapper.readTree(coinStr);
            logger.info(jsonNode);

            String originUpdateTime = jsonNode.get("time").get("updated").asText();
            String updateTime = coin.parseTimeFormat(originUpdateTime);

            Map bpiMap = mapper.convertValue(jsonNode.get("bpi"),Map.class);
            Set<String> coinTypes = bpiMap.keySet();

            List<CoinInfo> coinInfos = coin.parseCoinMap(jsonNode.get("bpi"),coinTypes);
            coin.addChineseName(coinInfos);

            coinInfoResp.setUpdateTime(updateTime);
            coinInfoResp.setCoinInfos(coinInfos);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }


        return coinInfoResp;
    }

}

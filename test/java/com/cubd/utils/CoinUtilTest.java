package com.cubd.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cubd.bean.CoinInfo;
import com.cubd.bean.CoinTable;
import com.cubd.service.CoinTableService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
public class CoinUtilTest {

    private CoinUtil coinUtil = mock(CoinUtil.class,CALLS_REAL_METHODS);
    private CoinTableService coinTableService = mock(CoinTableService.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        ReflectionTestUtils.setField(coinUtil,"coinTableService",coinTableService);
        ReflectionTestUtils.setField(coinUtil,"mapper",mapper);
    }

    @Test
    public void test_parseCoinMap() throws JsonProcessingException {
        /** Arrange */
        String coinStr = "{\"time\":{\"updated\":\"Jun 9, 2022 10:48:00 UTC\"},\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"30,483.7961\",\"description\":\"United States Dollar\",\"rate_float\":30483.7961}}}";
        String key = "USD";
        JsonNode node = mapper.readTree(coinStr);
        Set<String> keySet = new HashSet<>();
        keySet.add(key);


        /** Action */
        List<CoinInfo> coinInfos = coinUtil.parseCoinMap(node.get("bpi"),keySet);

        /** Assert */
        for(CoinInfo actCoinInfo : coinInfos)
        {
            Assert.assertEquals(key,actCoinInfo.getCoinType());
            Assert.assertNull(actCoinInfo.getCoinCHNName());
        }
    }

    @Test
    public void test_parseCoinMap_null() throws JsonProcessingException {
        /** Arrange */
        String coinStr = "{\"time\":{\"updated\":\"Jun 9, 2022 10:48:00 UTC\"},\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"30,483.7961\",\"description\":\"United States Dollar\",\"rate_float\":30483.7961}}}";
        String key = "USD";
        JsonNode node = mapper.readTree(coinStr);
        Set<String> keySet = new HashSet<>();
        keySet.add(key);


        /** Action */
        List<CoinInfo> coinInfos = coinUtil.parseCoinMap(node,keySet);

        /** Assert */
        Assert.assertEquals(0,coinInfos.size());
    }

    @Test
    public void test_parseTimeFormat() throws ParseException {
        /** Arrange */
        String time = "Jun 9, 2022 10:48:00 UTC";
        /** Action */
        String timeStr = coinUtil.parseTimeFormat(time);


        /** Assert */
        Assert.assertEquals("2022/06/09 18:48:00",timeStr);
    }

    @Test
    public void test_parseTimeFormat_Exception() throws ParseException {
        /** Arrange */
        String time = "";

        /** Action */
        try{
            coinUtil.parseTimeFormat(time);
        }catch (Exception e){
            /** Assert */
            Assert.assertTrue(e.toString().contains("Unparseable date"));
        }
    }

    @Test
    public void test_addChineseName(){

        List<CoinInfo> coinInfos = new ArrayList<>();
        CoinInfo coinInfo = new CoinInfo();
        coinInfo.setCoinType("USA");
        coinInfo.setCoinRate(123.5);

        coinInfos.add(coinInfo);

        CoinTable coinTable = new CoinTable();
        coinTable.setChineseName("美金");

        when(coinTableService.queryByCoinType("USA")).thenReturn(coinTable);


        coinUtil.addChineseName(coinInfos);


        verify(coinTableService,times(1)).queryByCoinType(anyString());
        for(CoinInfo actCoinInfo : coinInfos)
        {
            Assert.assertEquals("美金",actCoinInfo.getCoinCHNName());
        }
    }

    @Test
    public void test_addChineseName_null(){

        List<CoinInfo> coinInfos = new ArrayList<>();


        coinUtil.addChineseName(coinInfos);


        verify(coinTableService,times(0)).queryByCoinType(anyString());
    }

}

package com.cubd.test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import com.cubd.dto.CurrencyMappingDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)

public class SpringbootApplicationTests {

    private static final Logger log = LogManager.getLogger(SpringbootApplicationTests.class);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${coindesk.url}")
    private String coindeskUrl;

    /**
     * 1. 測試呼叫查詢幣別對應表資料API，並顯示其內容。
     * @throws Exception
     * @throws UnsupportedEncodingException
     */
    @Test
    @Order(1)
    void testRead() throws UnsupportedEncodingException, Exception {
        String findAllResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/currencyMapping/findAll").contentType(MediaType.APPLICATION_JSON))
                // 檢查是否回傳200
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 檢查是否回傳json
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info("All currency mappings => {}", findAllResult);
        String findUSDResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/currencyMapping/findById?engName=USD").contentType(MediaType.APPLICATION_JSON))
                // 檢查是否回傳200
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 檢查是否回傳json
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // 檢查是否回傳正確的值
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("美金")).andReturn().getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        log.info("USD currency mapping => {}", findUSDResult);
    }

    /**
     * 2. 測試呼叫新增幣別對應表資料API。
     * @throws Exception
     */
    @Test
    @Order(2)
    void testCreate() throws Exception {
        CurrencyMappingDto currencyMappingDto = new CurrencyMappingDto("JPY", "日幣");
        String requestBody = objectMapper.writeValueAsString(currencyMappingDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/currencyMapping/create").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                // 檢查是否回傳200
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 3. 測試呼叫更新幣別對應表資料API，並顯示其內容。
     * @throws Exception
     * @throws UnsupportedEncodingException
     */
    @Test
    @Order(3)
    void testUpdate() throws UnsupportedEncodingException, Exception {
        CurrencyMappingDto currencyMappingDto = new CurrencyMappingDto("JPY", "日圓");
        String requestBody = objectMapper.writeValueAsString(currencyMappingDto);
        String updateJPYResult = mockMvc
                .perform(MockMvcRequestBuilders.put("/currencyMapping/update").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                // 檢查是否回傳200
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 檢查是否回傳json
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // 檢查是否回傳正確的值
                .andExpect(MockMvcResultMatchers.jsonPath("$.engName").value("JPY"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("日圓")).andReturn().getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        log.info("JPY currency mapping => {}", updateJPYResult);
    }

    /**
     * 4. 測試呼叫刪除幣別對應表資料API。
     * @throws Exception
     */
    @Test
    @Order(4)
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/currencyMapping/delete/JPY").contentType(MediaType.APPLICATION_JSON))
                // 檢查是否回傳200
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 5. 測試呼叫coindesk API，並顯示其內容。
     */
    @Test
    @Order(5)
    void testCoinDeskAPI() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(coindeskUrl, String.class);
        // 檢查是否回傳200
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        log.info("Response from CoinDesk API => {}", responseEntity.getBody());
    }

    /**
     * 6. 測試呼叫資料轉換的API，並顯示其內容。
     * @throws Exception
     * @throws UnsupportedEncodingException
     */
    @Test
    @Order(6)
    void testNewCoinDeskAPI() throws UnsupportedEncodingException, Exception {
        String coindeskResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/coindesk").contentType(MediaType.APPLICATION_JSON))
                // 檢查是否回傳200
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 檢查是否回傳json
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info("Response from New CoinDesk API => {}", coindeskResult);
    }

}

package com.cubd.controller;

import com.cubd.bean.CoinInfoResp;
import com.cubd.bean.resp.ResponseWrap;
import com.cubd.config.ServiceConfigs;
import com.cubd.excutor.CoinExecutor;
import com.cubd.utils.OkHttpClientUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coin")
public class CoinController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private OkHttpClientUtil client;

    @Autowired
    private CoinExecutor coin;

    @Autowired
    private ServiceConfigs serviceConfigs;


    @GetMapping(value = "coindesk",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCoinDesk()
    {
        String responseStr = client.get(serviceConfigs.getCoinAPI());

        return ResponseEntity.ok(responseStr);
    }
    @GetMapping(value = "info",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCoinInfo()
    {
        ResponseWrap responseWrap = new ResponseWrap();
        HttpStatus status = HttpStatus.OK;

        try {
            String coinInfoStr = client.get(serviceConfigs.getCoinAPI());
            CoinInfoResp coinInfoRep = coin.parseCoin2Bean(coinInfoStr);

            responseWrap.setContent(coinInfoRep);



        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseWrap.setMsg(e.getMessage());
            logger.error(e);
        }

        return new ResponseEntity(responseWrap,status);
    }

}

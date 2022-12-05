package com.cubd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cubd.bean.CoinTable;
import com.cubd.bean.resp.ResponseWrap;
import com.cubd.service.CoinTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("coinTable")
public class CoinTableController {

    private Logger logger = LogManager.getLogger(this.getClass());
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private CoinTableService coinTableService;

    @GetMapping(value = "query" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity query()
    {
        ResponseWrap responseWrap = new ResponseWrap();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        try {

            List<CoinTable> coins = coinTableService.queryAllCoinsList();
            responseWrap.setContent(coins);

            status = HttpStatus.OK;


        } catch (Exception e) {
            logger.error(e);
            responseWrap.setMsg(e.getMessage());
        }

        return new ResponseEntity(responseWrap,status);
    }
    @PostMapping(value = "inset" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insert(@RequestBody CoinTable coinTable)
    {
        ResponseWrap responseWrap = new ResponseWrap();
        HttpStatus status = HttpStatus.LOCKED;

        try {
            logger.info(mapper.writeValueAsString(coinTable));
            coinTableService.insertCoin(coinTable);
            status = HttpStatus.OK;

        } catch (Exception e) {
            logger.error(e);
            responseWrap.setMsg(e.getMessage());
        }

        return new ResponseEntity(status);
    }

    @PostMapping(value = "update" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody CoinTable coinTable)
    {
        ResponseWrap responseWrap = new ResponseWrap();
        HttpStatus status = HttpStatus.LOCKED;

        try {
            logger.info(mapper.writeValueAsString(coinTable));
            coinTableService.updateCoin(coinTable);
            status = HttpStatus.OK;

        } catch (Exception e) {
            logger.error(e);
            responseWrap.setMsg(e.getMessage());
        }

        return new ResponseEntity(status);
    }

    @PostMapping(value = "delete" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@RequestBody CoinTable coinTable)
    {
        ResponseWrap responseWrap = new ResponseWrap();
        HttpStatus status = HttpStatus.LOCKED;

        try {
            logger.info(mapper.writeValueAsString(coinTable));
            coinTableService.deleteCoin(coinTable);
            status = HttpStatus.OK;

        } catch (Exception e) {
            logger.error(e);
            responseWrap.setMsg(e.getMessage());
            return new ResponseEntity(responseWrap,status);
        }

        return new ResponseEntity(status);
    }
}

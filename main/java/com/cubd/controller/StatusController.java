package com.cubd.controller;

import com.cubd.config.ServiceConfigs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ServiceConfigs serviceConfigs;

    @GetMapping("faq")
    public String faq()
    {
        logger.info("Service OK !");
        return "Service OK !";
    }
}

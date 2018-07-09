package com.stock.stockdetails.controller;

import com.stock.stockdetails.model.Stock;
import com.stock.stockdetails.service.StockService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/stock")
public class StockDetailsController {

    private static  final Logger logger= LoggerFactory.getLogger(StockDetailsController.class);

    @Autowired
    private StockService stockService;

    /**
     * This API will return the Stock details based on day, month and year
     * @param day
     * @param month
     * @param year
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/averageCloseRateOverPeriod" ,method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Stock>> averageCloseRateOverPeriod(
            @RequestParam(value = "day", required = false) String day ,
            @RequestParam(value = "month",   required = false) String month,
            @RequestParam(value = "year", required = true) String year) throws IOException {

        logger.info("In AverageCloseRateOverPeriod Entry Point");
        if(StringUtils.isEmpty(year)){
            return new ResponseEntity("Year should not be null", HttpStatus.BAD_REQUEST);
        }
        List<Stock> stockList =stockService.getAverageCloseRateOverPeriod(day,month,year);
        logger.info("In AverageCloseRateOverPeriod Exit Point");
        if (stockList != null) {
            return new ResponseEntity<>(stockList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }
}

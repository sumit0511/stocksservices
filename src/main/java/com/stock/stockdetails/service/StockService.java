package com.stock.stockdetails.service;

import com.stock.stockdetails.model.Stock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class StockService{


    @Autowired
    private CsvStockParser csvStockParser;

    private static  final Logger logger= LoggerFactory.getLogger(StockService.class);

    public List<Stock> getAverageCloseRateOverPeriod(String day, String month, String year) throws IOException{

        logger.info("In getAverageCloseRateOverPeriod Entry method");
        Map<String, Map<String, Map<String,  String>>> stockYearMap = csvStockParser.parseStockCsv();
        List<Stock> stockList = new ArrayList<>();
        if(!StringUtils.isEmpty(year)) {
            Map<String, Map<String, String>> monthMap = stockYearMap.get(year);
            if (!StringUtils.isEmpty(month)) {
                if (monthMap.containsKey(month)) {
                    Map<String, String> dayMap = monthMap.get(month);
                    if (!StringUtils.isEmpty(day)) {
                        if (dayMap.containsKey(day)) {
                            stockList.add(populateStock(day,month,year,dayMap.get(day)));
                            return stockList;
                        }
                        return null;
                    }
                    for (Map.Entry entry : dayMap.entrySet()) {
                        stockList.add(populateStock(entry.getKey().toString(),month,year,entry.getValue().toString()));
                    }
                    return stockList;
                }
                return null;
            }
            for (Map.Entry entry : monthMap.entrySet()) {
                Map<String, String> dayMap = monthMap.get(entry.getKey().toString());
                Double finalCloseRate = 0.0D;
                Stock stockResponseObject = new Stock();
                stockResponseObject.setDate(getMonth(Integer.parseInt(entry.getKey().toString())) + "-" + year);
                for (Map.Entry dayEntry : dayMap.entrySet()) {
                    finalCloseRate = finalCloseRate + Double.parseDouble(dayEntry.getValue().toString());
                }
                finalCloseRate = finalCloseRate / dayMap.size();
                stockResponseObject.setCloseRate(finalCloseRate.toString());
                stockList.add(stockResponseObject);

            }
            return stockList;
        }
        return null;
    }

    private Stock populateStock(String day, String month, String year, String closeRate){
        Stock stock = new Stock();
        stock.setDate(day + "-" + getMonth(Integer.parseInt(month)) + "-" + year);
        stock.setCloseRate(closeRate);
        return stock;
    }

    private String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}

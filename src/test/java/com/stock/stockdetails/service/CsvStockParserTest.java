package com.stock.stockdetails.service;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvStockParserTest {

    private CsvStockParser csvStockParser= new CsvStockParser();

    private Map<String, Map<String, Map<String,  String>>> yearMap = new HashMap<>();
    private Map<String, Map<String,  String>> monthMap = new HashMap<>();
    private Map<String,  String> dayMap = new HashMap<>();

    @Test
    public void parseStockCsv() throws IOException {
        Map<String, Map<String, Map<String,  String>>> yearMap = csvStockParser.parseStockCsv();
        Assert.assertNotNull(yearMap);
        monthMap=yearMap.get("72");
        dayMap=monthMap.get("6");

        Assert.assertEquals(yearMap.size(),47);
        Assert.assertEquals(yearMap.get("72").get("6").get("6"),"2.124835");
        Assert.assertEquals(yearMap.get("79").get("10").get("12"),"1.642286");
        Assert.assertEquals(monthMap.size(),7);
        Assert.assertEquals(dayMap.get("6"),"2.124835");
        Assert.assertEquals(dayMap.size(),22);

    }
}

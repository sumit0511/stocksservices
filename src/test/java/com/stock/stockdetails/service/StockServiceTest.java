package com.stock.stockdetails.service;


import com.stock.stockdetails.model.Stock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockServiceTest {


    private StockService stockService= new StockService();

    private Map<String, Map<String, Map<String,  String>>> yearMap = new HashMap<>();
    private Map<String, Map<String,  String>> monthMap = new HashMap<>();
    private Map<String,  String> dayMap = new HashMap<>();

    @Before
    public void setup() throws IOException {

        dayMap.put("2","2.235");
        dayMap.put("3","3.287");
        monthMap.put("6",dayMap);
        monthMap.put("7",dayMap);
        yearMap.put("72",monthMap);
        CsvStockParser mockCsvStockParser= Mockito.mock(CsvStockParser.class);
        ReflectionTestUtils.setField(stockService,"csvStockParser",mockCsvStockParser);
        Mockito.when(mockCsvStockParser.parseStockCsv()).thenReturn(yearMap);
    }

    @Test
    public void testAverageCloseRateByDate() throws IOException {
        List<Stock> stockList = stockService.getAverageCloseRateOverPeriod("2","6","72");
        Assert.assertNotNull(stockList);
        Assert.assertEquals(stockList.size(),1);
        Assert.assertEquals(stockList.get(0).getDate(),"2-June-72");
        Assert.assertEquals(stockList.get(0).getCloseRate(),"2.235");
    }

    @Test
    public void testAverageCloseRateByMonth() throws IOException {
        List<Stock> stockList = stockService.getAverageCloseRateOverPeriod("","6","72");
        Assert.assertNotNull(stockList);
        Assert.assertEquals(stockList.size(),2);
        Assert.assertEquals(stockList.get(0).getDate(),"2-June-72");
        Assert.assertEquals(stockList.get(0).getCloseRate(),"2.235");
        Assert.assertEquals(stockList.get(1).getDate(),"3-June-72");
        Assert.assertEquals(stockList.get(1).getCloseRate(),"3.287");
    }

    @Test
    public void testAverageCloseRateByYear() throws IOException {
        List<Stock> stockList = stockService.getAverageCloseRateOverPeriod("","","72");
        Assert.assertNotNull(stockList);
        Assert.assertEquals(stockList.size(),2);
        Assert.assertEquals(stockList.get(0).getDate(),"June-72");
        Assert.assertEquals(stockList.get(0).getCloseRate(),"2.761");
        Assert.assertEquals(stockList.get(1).getDate(),"July-72");
        Assert.assertEquals(stockList.get(1).getCloseRate(),"2.761");
    }
}

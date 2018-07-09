package com.stock.stockdetails.controller;
import com.stock.stockdetails.model.Stock;
import com.stock.stockdetails.service.StockService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StockDetailsControllerTest {

    private StockDetailsController stockDetailsController= new StockDetailsController();
    private StockService mockStockService= Mockito.mock(StockService.class);
    @Before
    public void setup(){
        ReflectionTestUtils.setField(stockDetailsController,"stockService",mockStockService);
    }

 @Test
 public void testAverageCloseRateOverPeriod() throws IOException {
     Stock stock = new Stock();
     stock.setDate("2-June-72");
     stock.setCloseRate("2.235");
     Mockito.when(mockStockService.getAverageCloseRateOverPeriod("2","6","72")).thenReturn(Arrays.asList(stock));
     ResponseEntity<List<Stock>> responseEntity= stockDetailsController.averageCloseRateOverPeriod("2","6","72");
     Assert.assertNotNull(responseEntity);
     Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
     List<Stock> stockList= responseEntity.getBody();
     Assert.assertEquals(stockList.size(),1);
     Assert.assertEquals(stockList.get(0).getDate(),"2-June-72");
     Assert.assertEquals(stockList.get(0).getCloseRate(),"2.235");
 }

    @Test
    public void testAverageCloseRateOverPeriod_BlankYear() throws IOException {
        ResponseEntity<List<Stock>> responseEntity= stockDetailsController.averageCloseRateOverPeriod("2","6","");
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
        Assert.assertEquals("Year should not be null",responseEntity.getBody());
    }
}

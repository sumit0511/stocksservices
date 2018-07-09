package com.stock.stockdetails.service;

import com.opencsv.CSVReader;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CsvStockParser {

    private static  final org.slf4j.Logger logger= LoggerFactory.getLogger(CsvStockParser.class);

    public Map<String, Map<String, Map<String,  String>>> parseStockCsv() throws IOException {
        logger.info("In ParseStockCsv Method Entry");
        String csvFile= ResourceUtils.getFile("classpath:F.csv").getAbsolutePath();
        Map<String,  String> dayMap = new HashMap<>();
        Map<String, Map<String,  String>> monthMap = new HashMap<>();
        Map<String, Map<String, Map<String,  String>>> yearMap = new HashMap<>();

        try(CSVReader csvReader = new CSVReader(new FileReader(csvFile), ',', '\'', 1)) {
            String[] row;
            List<String[]> content=csvReader.readAll();
            for (Object object : content)
            {
                row = (String[]) object;
                logger.info("Date=" + row[0] + " , CloseRate = " + row[4]);
                String[] dateArray=  row[0].split("/");
                if (yearMap.containsKey(dateArray[2])) {
                    monthMap = yearMap.get(dateArray[2]);
                    if (monthMap.containsKey(dateArray[0])) {
                        dayMap = monthMap.get(dateArray[0]);
                        dayMap.put(dateArray[1],row[4]);
                    } else {
                        dayMap = new HashMap<>();
                        dayMap.put(dateArray[1],row[4]);
                    }
                    monthMap.put(dateArray[0], dayMap);
                    yearMap.put(dateArray[2], monthMap);
                } else {
                    monthMap = new HashMap<>();
                    dayMap = new HashMap<>();
                    dayMap.put(dateArray[1],row[4]);
                    monthMap.put(dateArray[0], dayMap);
                    yearMap.put(dateArray[2], monthMap);
                }
            }
        }
        logger.info("In ParseStockCsv Method Exit");
        return yearMap;
    }


}

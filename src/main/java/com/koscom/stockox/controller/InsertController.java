package com.koscom.stockox.controller;

import com.koscom.stockox.service.InsertService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@ResponseBody
public class InsertController {

    @Autowired
    InsertService insertService;
    /**
     * 2021.01.27 회사에 대한 정보 넣는 request function
     * table : company_info
     */
    @GetMapping("/insert/companyInfo")
    public void insertCompanyInfo() throws IOException {
        insertService.insertCompanyInfo();
        System.out.println("Inserting company information is finished");
    }

    /**
     * 2021.01.27 회사의 가격에 대한 정보 넣는 request function
     * table : company_price_info
     */
    @GetMapping("/insert/companyPriceInfo")
    public void insertCompanyPriceInfo() throws IOException, ParseException {
        insertService.insertCompanyPriceInfo();
        System.out.println("Inserting company price information is finished");
    }

    @GetMapping("/insert/kospiInfo")
    public void insertKospiInfo() throws IOException, ParseException{

        insertService.insertKospiInfo();
        System.out.println("inserting kospi information is finished.");

        insertService.insertKosdaqInfo();
        System.out.println("inserting Kosdaq information is finished");
    }

    @GetMapping("/insert/priceBogi")
    public void insertPriceBogi() throws IOException, ParseException{

        System.out.println("start inserting start price bogi");
        insertService.insertStartToEndBogi();
        System.out.println("inserting start price bogi is finished");

        System.out.println("start inserting end price bogi");
        insertService.insertMaxToMinBogi();
        System.out.println("inserting end price bogi is finished");

    }

    @GetMapping("/insert/kosBogi")
    public void insertKosBogi() throws IOException, ParseException{

        System.out.println("start inserting kospi price bogi");
        insertService.insertKospiTradePriceBogi();
        System.out.println("inserting kospi price is finished");

        System.out.println("start inserting kosdaq price bogi");
        insertService.insertKosdaqTradePriceBogi();
        System.out.println("inserting kosdaq price is finished");
    }
}

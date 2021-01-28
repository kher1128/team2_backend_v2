package com.koscom.stockox.service;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface InsertService {
    /**
     * 2021.01.27 회사 코드 및 회사 종류 insert
     * target table : company_info
     */
    public void insertCompanyInfo() throws IOException;

    /**
     * 2021.01.27 회사별 시가,종가,저가,고가 가격 받아옴
     * target table : company_price_info
     */
    public void insertCompanyPriceInfo() throws IOException, ParseException;
    /**
     * 2021.01.27 kospi 정보 insert
     * target table : kospi_info
     */
    public void insertKospiInfo() throws IOException, ParseException;
    /**
     * 2021.01.27 kosdaq 정보 insert
     * target table : kosdaq_info
     */
    public void insertKosdaqInfo() throws IOException, ParseException;


    /**
     * 2021.01.27 무제에 쓰일 시가 대비 종가 차이 가격 퍼센트 insert
     */
    public void insertStartToEndBogi() ;

    /**
     * 2021.01.27 무제에 쓰일 고가 대비 저가 차이 가격 퍼센트 insert
     */
    public void insertMaxToMinBogi() ;
    /**
     * 2021.01.27 문제에 쓰일 kospi 가격 보기 insert
     * target table : price_bogi
     */
    public void insertKospiTradePriceBogi() throws IOException, ParseException;
    /**
     * 2021.01.27 문제에 쓰일 kosdaq 가격 보기 insert
     * target table : price_bogi
     */
    public void insertKosdaqTradePriceBogi() throws IOException, ParseException;
}

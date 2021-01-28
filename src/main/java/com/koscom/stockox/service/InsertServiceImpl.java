package com.koscom.stockox.service;

import com.koscom.stockox.dto.*;
import com.koscom.stockox.mapper.KoscomMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InsertServiceImpl implements  InsertService {

    @Autowired
    KoscomMapper koscomMapper;

    @Override
    public void insertCompanyInfo() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://sandbox-apigw.koscom.co.kr/v2/market/stocks/{marketcode}/lists"
                .replace("{marketcode}", URLEncoder.encode("kospi", "UTF-8")));

        urlBuilder.append("?");
        urlBuilder.append(URLEncoder.encode("apikey","UTF-8")+"="+URLEncoder.encode("l7xx1340964a7c3242339f76f2d6a983bdf0", "UTF-8"));
        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");


        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String json = sb.toString();

        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String,Object> map = jsonParser.parseMap(json);

        ArrayList<Object> isuLists = (ArrayList<Object>) map.get("isuLists");

        for(int i=0;i<isuLists.size();i++){
            /**
             * 2021.01.27 jsonData parsing
             * csh1man
             */
            String jsonStr = isuLists.get(i).toString();
            jsonStr = jsonStr.replace("{"," ");
            jsonStr = jsonStr.replace("}"," ");

            String[] jsonList = jsonStr.split(",");

            String companyCode = jsonList[0].split("=")[1];
            String companyName = jsonList[2].split("=")[1];
            /**
             * 2021.01.27 CompanyInfo DTO를 이용하여 AWS RDS 테이블에 데이터 insert
             * csh1man
             */
            System.out.println(companyCode + companyName);
            CompanyInfo companyInfo = new CompanyInfo(companyCode,companyName);
            koscomMapper.insertCompanyInfo(companyInfo);
        }
    }

    @Override
    public void insertCompanyPriceInfo() throws IOException, ParseException {

        List<CompanyInfo> companyInfoList = koscomMapper.findAllCompany();

        Date dDate = new Date();
        dDate = new Date(dDate.getTime()+(1000*60*60*24*-1));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String yesterday = simpleDateFormat.format(dDate);

        for(int i=0;i<companyInfoList.size();i++){

            String companyId = companyInfoList.get(i).getCompanyId();
            String companyName = companyInfoList.get(i).getCompanyName();

            StringBuilder urlBuilder = new StringBuilder("https://sandbox-apigw.koscom.co.kr/v2/market/stocks/{marketcode}/{issuecode}/history"
                    .replace("{marketcode}", URLEncoder.encode("kospi", "UTF-8"))
                    .replace("{issuecode}", URLEncoder.encode(companyId, "UTF-8")));
            urlBuilder.append("?");
            urlBuilder.append(URLEncoder.encode("trnsmCycleTpCd","UTF-8") + "=" + URLEncoder.encode("D", "UTF-8") + "&");
            urlBuilder.append(URLEncoder.encode("inqStrtDd","UTF-8") + "=" + URLEncoder.encode(yesterday, "UTF-8") + "&");
            urlBuilder.append(URLEncoder.encode("inqEndDd","UTF-8") + "=" + URLEncoder.encode(yesterday, "UTF-8") + "&");
            urlBuilder.append(URLEncoder.encode("reqCnt","UTF-8") + "=" + URLEncoder.encode("20", "UTF-8") + "&");
            urlBuilder.append(URLEncoder.encode("apikey","UTF-8") + "=" + URLEncoder.encode("l7xx1340964a7c3242339f76f2d6a983bdf0", "UTF-8"));
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String json = sb.toString();

            JSONParser jsonParser = new JSONParser();

            json = jsonParser.parse(json).toString();

            JSONObject result = (JSONObject) ((JSONObject) jsonParser.parse(json)).get("result");

            JSONArray hisLists  = (JSONArray) result.get("hisLists");

            String opnPrc = ((JSONObject) hisLists.get(0)).get("opnprc").toString(); //시가
            String trdPrc = ((JSONObject) hisLists.get(0)).get("trdPrc").toString(); //종가
            String lwPrc = ((JSONObject) hisLists.get(0)).get("lwprc").toString(); //저가
            String hgPrc = ((JSONObject) hisLists.get(0)).get("hgprc").toString(); //고가

            /**
             * 2021.01.27 간혹 회사별로 각 시/종/저/고 중에서 빠져있는 회사가 있어서 예외 처리.
             * csh1man
             */

           if(!opnPrc.equals("0") && !trdPrc.equals("0") && !lwPrc.equals("0") && !hgPrc.equals("0")){

               CompanyPriceInfo companyPriceInfo =
                       new CompanyPriceInfo(companyId,Double.parseDouble(opnPrc),Double.parseDouble(trdPrc),Double.parseDouble(hgPrc),Double.parseDouble(lwPrc));

               koscomMapper.insertCompanyPriceInfo(companyPriceInfo);
           }
        }


    }

    @Override
    public void insertKospiInfo() throws IOException, ParseException {

        StringBuilder urlBuilder = new StringBuilder("https://sandbox-apigw.koscom.co.kr/v2/market/stocks/{marketcode}/index"
                .replace("{marketcode}", URLEncoder.encode("kospi", "UTF-8")));
        urlBuilder.append("?");
        urlBuilder.append(URLEncoder.encode("apikey","UTF-8") + "=" + URLEncoder.encode("l7xx1340964a7c3242339f76f2d6a983bdf0", "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String json = sb.toString();
        JSONParser jsonParser = new JSONParser();

        JSONObject result = (JSONObject) ((JSONObject) jsonParser.parse(json)).get("result");
        Double opnPrc = Double.parseDouble(result.get("opnprc").toString());
        Double trdPrc = Double.parseDouble(result.get("trdPrc").toString());
        Double lwPrc = Double.parseDouble(result.get("lwprc").toString());
        Double hgPrc = Double.parseDouble(result.get("hgprc").toString());

        KospiInfo kospiInfo = new KospiInfo(opnPrc,hgPrc,lwPrc,trdPrc);
        koscomMapper.insertKospiInfo(kospiInfo);

    }

    @Override
    public void insertKosdaqInfo() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("https://sandbox-apigw.koscom.co.kr/v2/market/stocks/{marketcode}/index"
                .replace("{marketcode}", URLEncoder.encode("kosdaq", "UTF-8")));
        urlBuilder.append("?");
        urlBuilder.append(URLEncoder.encode("apikey","UTF-8") + "=" + URLEncoder.encode("l7xx1340964a7c3242339f76f2d6a983bdf0", "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String json = sb.toString();
        JSONParser jsonParser = new JSONParser();

        JSONObject result = (JSONObject) ((JSONObject) jsonParser.parse(json)).get("result");
        Double opnPrc = Double.parseDouble(result.get("opnprc").toString());
        Double trdPrc = Double.parseDouble(result.get("trdPrc").toString());
        Double lwPrc = Double.parseDouble(result.get("lwprc").toString());
        Double hgPrc = Double.parseDouble(result.get("hgprc").toString());

        KosdaqInfo kosdaqInfo = new KosdaqInfo(opnPrc,hgPrc,lwPrc,trdPrc);
        koscomMapper.insertKosdaqInfo(kosdaqInfo);
    }

    @Override
    public void insertStartToEndBogi() {
        List<CompanyInfo> companyInfoList = koscomMapper.findAllCompany();

        for(int i=0 ; i <companyInfoList.size() ; i++){

            String companyId = companyInfoList.get(i).getCompanyId();
            int isExisted = koscomMapper.isExistedPrice(companyId);
            /**
             *  가격이 존재하는 회사일 경우
             */
            if(isExisted > 0){

                double endPrice = koscomMapper.getEndPrice(companyId);
                double startPrice = koscomMapper.getStartPrice(companyId);

                double answer = 0;
                double wrongAnswer1 = 0;
                double wrongAnswer2 = 0;
                double wrongAnswer3 = 0;

                /** 시가보다 종가가 높을 경우  >> 상승 */
                if(endPrice >= startPrice){
                    answer = (endPrice-startPrice)/startPrice * 100;
                }
                /** 시가보다 종가가 낮을 경우 >> 하락 */
                else{
                    answer = (startPrice - endPrice) / startPrice * 100;

                }
                answer = Double.parseDouble(String.format("%.2f",answer));
                Double makeAnswer = answer * 100;

                if(makeAnswer >=100){
                    wrongAnswer1 = (makeAnswer -100) / 100;
                    wrongAnswer2 = (makeAnswer-10) / 100;
                    wrongAnswer3 = (makeAnswer-1) / 100;
                }
                else if(makeAnswer >= 10){
                    wrongAnswer1 = (makeAnswer -10) / 100;
                    wrongAnswer2 = (makeAnswer-5) / 100;
                    wrongAnswer3 = (makeAnswer-1) / 100;
                }
                else if(makeAnswer>=1){
                    wrongAnswer1 = (makeAnswer-1) / 100;
                    wrongAnswer2 = (makeAnswer-0.5) /100;
                    wrongAnswer3 = (makeAnswer-0.1) /100;
                }

                answer = Double.parseDouble(String.format("%.2f",answer));
                wrongAnswer1 = Double.parseDouble(String.format("%.2f",wrongAnswer1));
                wrongAnswer2 = Double.parseDouble(String.format("%.2f",wrongAnswer2));
                wrongAnswer3 = Double.parseDouble(String.format("%.2f",wrongAnswer3));
                System.out.println(companyId + " " + companyInfoList.get(i).getCompanyName());
                PriceBogi priceBogi = new PriceBogi(companyId,"1",answer,wrongAnswer1,wrongAnswer2,wrongAnswer3);
                koscomMapper.createPriceBogi(priceBogi);

            }
        }
    }

    @Override
    public void insertMaxToMinBogi() {
        List<CompanyInfo> companyInfoList = koscomMapper.findAllCompany();

        for(int i=0;i<companyInfoList.size();i++){

            String companyId = companyInfoList.get(i).getCompanyId();
            int isExisted = koscomMapper.isExistedPrice(companyId);

            if(isExisted > 0){
                String companyName = companyInfoList.get(i).getCompanyName();
                double maxPrice = koscomMapper.getMaxPrice(companyId);
                double minPrice = koscomMapper.getMinPrice(companyId);
                double answer = 0;
                double wrongAnswer1 = 0;
                double wrongAnswer2 = 0;
                double wrongAnswer3 = 0;

                answer = Math.abs(maxPrice-minPrice) / minPrice * 100;

                Double makeAnswer = answer * 100;

                if(makeAnswer >=100){
                    wrongAnswer1 = (makeAnswer -100) / 100;
                    wrongAnswer2 = (makeAnswer-10) / 100;
                    wrongAnswer3 = (makeAnswer-1) / 100;
                }
                else if(makeAnswer >= 10){
                    wrongAnswer1 = (makeAnswer -10) / 100;
                    wrongAnswer2 = (makeAnswer-5) / 100;
                    wrongAnswer3 = (makeAnswer-1) / 100;
                }
                else if(makeAnswer>=1){
                    wrongAnswer1 = (makeAnswer-1) / 100;
                    wrongAnswer2 = (makeAnswer-0.5) /100;
                    wrongAnswer3 = (makeAnswer-0.1) /100;
                }

                answer = Double.parseDouble(String.format("%.2f",answer));
                wrongAnswer1 = Double.parseDouble(String.format("%.2f",wrongAnswer1));
                wrongAnswer2 = Double.parseDouble(String.format("%.2f",wrongAnswer2));
                wrongAnswer3 = Double.parseDouble(String.format("%.2f",wrongAnswer3));

                PriceBogi priceBogi = new PriceBogi(companyId,"2",answer,wrongAnswer1,wrongAnswer2,wrongAnswer3);
                koscomMapper.createPriceBogi(priceBogi);
            }
        }
    }

    @Override
    public void insertKospiTradePriceBogi() throws IOException, ParseException {
        KospiInfo kospiInfo = koscomMapper.getKospiInfo();

        double answer = kospiInfo.getTradePrice();
        double wrongAnswer1 = answer -100;
        double wrongAnswer2 = answer - 10;
        double wrongAnswer3 = answer - 5;

        PriceBogi priceBogi = new PriceBogi("kospi","3",answer,wrongAnswer1,wrongAnswer2,wrongAnswer3);

        koscomMapper.createPriceBogi(priceBogi);
    }

    @Override
    public void insertKosdaqTradePriceBogi() throws IOException, ParseException {
        KosdaqInfo kosdaqInfo = koscomMapper.getKosdaqInfo();

        double answer = kosdaqInfo.getTradePrice();
        double wrongAnswer1 = answer -100;
        double wrongAnswer2 = answer - 10;
        double wrongAnswer3 = answer - 5;

        PriceBogi priceBogi = new PriceBogi("kosdaq","4",answer,wrongAnswer1,wrongAnswer2,wrongAnswer3);

        koscomMapper.createPriceBogi(priceBogi);
    }

}

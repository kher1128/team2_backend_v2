package com.koscom.stockox.service;

import com.koscom.stockox.dto.BaseProblem;
import com.koscom.stockox.dto.CompanyInfo;
import com.koscom.stockox.dto.PriceProblem;
import com.koscom.stockox.mapper.KoscomMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Service
public class ReturnServiceImpl implements ReturnService{

    @Autowired
    KoscomMapper koscomMapper;

    @Override
    public JSONArray returnProblem(String company_id) {

        String[] flag = {"1","2","3","4"};
        PriceProblem priceProblem = null;

        JSONArray jsonArray = new JSONArray();

        for(int i=0;i<4;i++){
            int randomIdx = (int)(Math.random() * 4) + 1;
            JSONObject jsonObject = new JSONObject();
            if(i==0 || i==1){
                priceProblem = koscomMapper.returnProblem(company_id,flag[i],randomIdx);
            }
            else if(i==2){
                priceProblem = koscomMapper.returnProblem("kospi",flag[i],randomIdx);
            }
            else if(i==3){
                priceProblem = koscomMapper.returnProblem("kosdaq",flag[i],randomIdx);
            }
            if(priceProblem == null){
                System.out.println("i : " + i);
                System.out.println("flag : "+ flag[i]);
                System.out.println("company_id : "+ company_id);
                System.out.println("random_idx : "+ randomIdx);

             }

            jsonObject.put("problem",priceProblem.getProblem());
            jsonObject.put("is_true",priceProblem .getIsTrue());

            jsonArray.add(jsonObject);
        }

        /**
         * 1~30 숫자 중에서 임의의 숫자 4개 생성 (중복 제거)
         */

        Random random = new Random();
        int[] baseIdx = this.getRandomIdxList();

        for(int i=0;i<6;i++){
            JSONObject jsonObject1 = new JSONObject();
            System.out.println(koscomMapper.returnBaseProblem(baseIdx[i]).getWrong_answer() );
            BaseProblem baseProblem = koscomMapper.returnBaseProblem(baseIdx[i]);
            int trueOrFalse = random.nextInt(2)+1;

            if(trueOrFalse ==1){
                jsonObject1.put("problem",baseProblem.getAnswer());
                jsonObject1.put("is_true",true);
            }
            else if(trueOrFalse ==2){
                jsonObject1.put("problem",baseProblem.getWrong_answer());
                jsonObject1.put("is_true",false);
            }
            jsonArray.add(jsonObject1);
        }
        return jsonArray;
    }

    @Override
    public List returnCompanyList() {
        List<CompanyInfo> companyInfoList = koscomMapper.findAllCompany();
        return companyInfoList;
    }

    @Override
    public List returnNewsTitle(String company_name) {
        String clientId = "rhncl5RCIR2VT3kXEejE"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "WBmajfWSrI"; //애플리케이션 클라이언트 시크릿값"
        String text = null;
        try {
            text = URLEncoder.encode(company_name, "UTF-8"); // company_name에 회사이름 넣으면 utf-8변환되서 검색에 사용
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&sort=sim&display=5"; // 5개만 추출, 연관성 위주로 정렬
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret); // 헤더 인증id 추가
        String responseBody = get(apiURL,requestHeaders);    // API 호출

        org.json.JSONObject Obj = new org.json.JSONObject(responseBody);
        org.json.JSONArray objArr = Obj.getJSONArray("items");
        List list = new ArrayList();

        for(int i=0;i<objArr.length();i++){     // List에 기사 제목 추가
            Map result = new HashMap<String, String>();
            org.json.JSONObject targetObj = objArr.getJSONObject(i);
            String title = targetObj.get("title").toString();
            title = title.replace("<b>", "");
            title = title.replace("</b>", "");
            title = title.replace("&quot", "");
            title = title.replace("&nbsp", "");
            title = title.replace("&amp", "");
            title = title.replace(";", "");
            result.put("title", title);
            list.add(result);
        }
        return list;
    }
    private static String get(String apiUrl, Map<String, String> requestHeaders){ // API 호출해서 데이터 가져오기
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }
    private static HttpURLConnection connect(String apiUrl){ // HTTP 연결
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }
    private static String readBody(InputStream body){ // API 결과로부터 데이터 읽어오기
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
    /**
     * 6개의 난수를 만들어서 배열로 반환해주는 function
     */
    public int[] getRandomIdxList(){
        int baseIdx[] = new int[6];
        Random r = new Random();

        for(int i=0;i<6;i++){
            baseIdx[i] = r.nextInt(30)+1;
            for(int j=0;j<i;j++){
                if(baseIdx[i]==baseIdx[j]){
                    i--;
                }
            }
        }
        return baseIdx;
    }
}

package com.koscom.stockox.dto;

/**
 * 2021.01.22 문제 테이블에 넣기 위한 DTO 정의
 * 상세 설명
 *  1. companyId : 회사코드
 *  2. flag : 어떤 가격과 관련된 것인지
 *      - 1 : 시가
 *      - 2 : 종가
 *      - 3 : 고가
 *      - 4 : 저가
 *  3. answer : 정답
 *  4. wrongAnswer1 : 오답1
 *  5. wrongAnswer2 : 오답2
 *  6. wrongAnswer3 : 오답3
 */
public class PriceBogi {

    String companyId;
    String flag;
    double answer;
    double wrongAnswer1;
    double wrongAnswer2;
    double wrongAnswer3;


    public PriceBogi(String companyId,String flag, double answer,double wrongAnswer1,double wrongAnswer2,double wrongAnswer3){
        this.companyId = companyId;
        this.flag = flag;
        this.answer = answer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }
    /**
     * 2021.01.22 companyId setter getter
     */
    public void setCompanyId(String companyId){
        this.companyId = companyId;
    }
    public String getCompanyId(){
        return this.companyId;
    }
    /**
     * 2021.01.22 flag setter getter
     */
    public void setFlag(String flag){
        this.flag = flag;
    }
    public String getFlag(){
        return this.flag;
    }
    /**
     * 2021.01.22 answer setter getter
     */
    public void setAnswer(double answer){
        this.answer = answer;
    }
    public double getAnswer(){
        return this.answer;
    }
    /**
     * 2021.01.22 wrongAnswer1 setter getter
     */
    public void setWrongAnswer1(double wrongAnswer1){
        this.wrongAnswer1 = wrongAnswer1;
    }
    public double getWrongAnswer1(){
        return this.wrongAnswer1;
    }
    /**
     * 2021.01.22 wrongAnswer2 setter getter
     */
    public void setWrongAnswer2(double wrongAnswer2){
        this.wrongAnswer2 = wrongAnswer2;
    }
    public double getWrongAnswer2(){
        return this.wrongAnswer2;
    }
    /**
     * 2021.01.22 wrongAnswer3 setter getter
     */
    public void setWrongAnswer3(double wrongAnswer3){
        this.wrongAnswer3 = wrongAnswer3;
    }
    public double getWrongAnswer3(){
        return this.wrongAnswer3;
    }
}
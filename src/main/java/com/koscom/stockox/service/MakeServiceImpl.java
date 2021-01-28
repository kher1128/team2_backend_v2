package com.koscom.stockox.service;

import com.koscom.stockox.dto.*;
import com.koscom.stockox.mapper.KoscomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MakeServiceImpl implements  MakeService{

    @Autowired
    KoscomMapper koscomMapper;

    @Override
    public void makeProblemEndFromStart() {
        List<CompanyInfo> companyInfoList = koscomMapper.findAllCompany();

        for(int i=0;i<companyInfoList.size();i++){

            String companyId = companyInfoList.get(i).getCompanyId();

            int isExisted = koscomMapper.isExistedBogi(companyId);
            String flag = "1";
            if(isExisted > 0){
                String companyName = companyInfoList.get(i).getCompanyName();
                PriceBogi priceBogi = koscomMapper.getPriceBogi(companyId,flag);

                String situation ="";

                CompanyPriceInfo companyPriceInfo = koscomMapper.getCompanyPriceInfo(companyId);

                Double endPrice = companyPriceInfo.getCompanyEndPrice();
                Double startPrice = companyPriceInfo.getCompanyStartPrice();
                /**
                 * 시가 대비 종가가 상승했는 지 하락했는 지에 대한 확인
                 */
                if(endPrice > startPrice){
                    situation = "상승";
                }
                else{
                    situation ="하락";
                }

                String answer = companyName+"의 시가 대비 종가는 약 " +priceBogi.getAnswer() +"% "+situation+"했다.";
                String wrongAnswer1 = companyName+"의 시가 대비 종가는 약 " +priceBogi.getWrongAnswer1() +"% "+situation+"했다.";
                String wrongAnswer2 = companyName+"의 시가 대비 종가는 약 " +priceBogi.getWrongAnswer2() +"% "+situation+"했다.";
                String wrongAnswer3 = companyName+"의 시가 대비 종가는 약 " +priceBogi.getWrongAnswer3() +"% "+situation+"했다.";


                koscomMapper.createProblem(new PriceProblem(companyId,answer,true,flag,1));
                koscomMapper.createProblem(new PriceProblem(companyId,wrongAnswer1,false,flag,2));
                koscomMapper.createProblem(new PriceProblem(companyId,wrongAnswer2,false,flag,3));
                koscomMapper.createProblem(new PriceProblem(companyId,wrongAnswer3,false,flag,4));
            }
        }
    }

    @Override
    public void makeProblemMaxFromMin() {
        List<CompanyInfo> companyInfoList = koscomMapper.findAllCompany();

        for(int i=0;i<companyInfoList.size();i++){

            String companyId = companyInfoList.get(i).getCompanyId();
            int isExisted = koscomMapper.isExistedPrice(companyId);

            String flag ="2";
            if(isExisted > 0){
                String companyName = companyInfoList.get(i).getCompanyName();
                PriceBogi priceBogi = koscomMapper.getPriceBogi(companyId,flag);

                String situation ="차이";

                CompanyPriceInfo companyPriceInfo = koscomMapper.getCompanyPriceInfo(companyId);



                String answer = companyName + "의 저가 대비 고가는 약 " + priceBogi.getAnswer() +"% 차이 난다.";
                String wrongAnswer1 = companyName + "의 저가 대비 고가는 약 " + priceBogi.getWrongAnswer1() +"% 차이 난다.";
                String wrongAnswer2 = companyName + "의 저가 대비 고가는 약 " + priceBogi.getWrongAnswer2() +"% 차이 난다.";
                String wrongAnswer3 = companyName + "의 저가 대비 고가는 약 " + priceBogi.getWrongAnswer3() +"% 차이 난다.";

                koscomMapper.createProblem(new PriceProblem(companyId,answer,true,flag,1));
                koscomMapper.createProblem(new PriceProblem(companyId,wrongAnswer1,false,flag,2));
                koscomMapper.createProblem(new PriceProblem(companyId,wrongAnswer2,false,flag,3));
                koscomMapper.createProblem(new PriceProblem(companyId,wrongAnswer3,false,flag,4));
            }
        }

    }

    @Override
    public void makeProblemAboutKospi() {

        PriceBogi priceBogi = koscomMapper.getPriceBogi("kospi","3");

        String answer = "kospi 지수는 " +priceBogi.getAnswer() +"이다.";
        String wrongAnswer1 = "kospi 지수는 " + priceBogi.getWrongAnswer1() +"이다.";
        String wrongAnswer2 = "kospi 지수는 " + priceBogi.getWrongAnswer2() +"이다.";
        String wrongAnswer3 = "kospi 지수는 " + priceBogi.getWrongAnswer3() +"이다.";
        String flag = "3";
        koscomMapper.createProblem(new PriceProblem("kospi",answer,true,flag,1));
        koscomMapper.createProblem(new PriceProblem("kospi",wrongAnswer1,false,flag,2));
        koscomMapper.createProblem(new PriceProblem("kospi",wrongAnswer2,false,flag,3));
        koscomMapper.createProblem(new PriceProblem("kospi",wrongAnswer3,false,flag,4));
    }

    @Override
    public void makeProblemAboutKosdaq() {
        PriceBogi priceBogi = koscomMapper.getPriceBogi("kosdaq","4");

        String answer = "kosdaq 지수는 " +priceBogi.getAnswer() +"이다.";
        String wrongAnswer1 = "kosdaq 지수는 " + priceBogi.getWrongAnswer1() +"이다.";
        String wrongAnswer2 = "kosdaq 지수는 " + priceBogi.getWrongAnswer2() +"이다.";
        String wrongAnswer3 = "kosdaq 지수는 " + priceBogi.getWrongAnswer3() +"이다.";
        String flag = "4";

        koscomMapper.createProblem(new PriceProblem("kosdaq",answer,true,flag,1));
        koscomMapper.createProblem(new PriceProblem("kosdaq",wrongAnswer1,false,flag,2));
        koscomMapper.createProblem(new PriceProblem("kosdaq",wrongAnswer2,false,flag,3));
        koscomMapper.createProblem(new PriceProblem("kosdaq",wrongAnswer3,false,flag,4));
    }


}

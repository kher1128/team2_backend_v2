package com.koscom.stockox.controller;

import com.koscom.stockox.dto.CompanyInfo;
import com.koscom.stockox.service.ReturnService;
import org.apache.ibatis.annotations.Param;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
public class ReturnController {

    @Autowired
    ReturnService returnService;

    @GetMapping("/companyList")
    public List returnCompanyList(){

        List<CompanyInfo> companyInfoList = returnService.returnCompanyList();

        return companyInfoList;
    }
    @GetMapping("/getProblem")
    public JSONArray returnProblem(@Param("company_id") String company_id){

        JSONArray problemArray = returnService.returnProblem(company_id);
        return problemArray;
    }

    @GetMapping("/title")
    public List returnNewsTitle(@Param("company_name") String company_name){
            return returnService.returnNewsTitle(company_name);
    }
}

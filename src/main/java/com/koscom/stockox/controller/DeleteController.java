package com.koscom.stockox.controller;

import com.koscom.stockox.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class DeleteController {
    @Autowired
    DeleteService deleteService;

    @GetMapping("/delete/all")
    public void deleteAllData(){
        System.out.println("removing Company information is started");
        deleteService.deleteCompanyInfo();
        System.out.println("removing Company information is finished");

        System.out.println("removing Company Price Information is started");
        deleteService.deleteCompanyPriceInfo();
        System.out.println("removing Company Price Information is finished");

        System.out.println("removing kospi price information is started");
        deleteService.deleteKospiPriceInfo();
        System.out.println("removing kospi price information is finished");

        System.out.println("removing kosdaq price information is started");
        deleteService.deleteKosdaqPriceInfo();
        System.out.println("removing kosdaq price information is finished");

        System.out.println("removing price bogi is started");
        deleteService.deletePriceBogi();
        System.out.println("removing price bogi is finished");

        System.out.println("removing problem is started");
        deleteService.deleteProblem();
        System.out.println("removing problem is finished");
    }

    @GetMapping("/delete/companyInfo")
    public void deleteCompanyInfo(){
        System.out.println("removing Company information is started");
        deleteService.deleteCompanyInfo();
        System.out.println("removing Company information is finished");
    }
    @GetMapping("/delete/companyPriceInfo")
    public void deleteCompanyPriceInfo(){
        System.out.println("removing Company Price Information is started");
        deleteService.deleteCompanyPriceInfo();
        System.out.println("removing Company Price Information is finished");
    }
    @GetMapping("/delete/kospiinfo")
    public void deleteKospiInfo(){
        System.out.println("removing kospi price information is started");
        deleteService.deleteKospiPriceInfo();
        System.out.println("removing kospi price information is finished");

    }
    @GetMapping("/delete/kosdaqInfo")
    public void deleteKosdaqinfo(){
        System.out.println("removing kosdaq price information is started");
        deleteService.deleteKosdaqPriceInfo();
        System.out.println("removing kosdaq price information is finished");
    }
    @GetMapping("/delete/priceBogi")
    public void deletePriceBogi(){
        System.out.println("removing kospi price information is started");
        deleteService.deleteKospiPriceInfo();
        System.out.println("removing kospi price information is finished");
    }
    @GetMapping("/delete/problem")
    public void deleteProblem(){
        System.out.println("removing problem is started");
        deleteService.deleteProblem();
        System.out.println("removing problem is finished");
    }
}

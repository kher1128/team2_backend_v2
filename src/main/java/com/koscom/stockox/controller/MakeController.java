package com.koscom.stockox.controller;

import com.koscom.stockox.service.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MakeController {

    @Autowired
    MakeService makeService;


    @GetMapping("/make/problem")
    public void makeProblem(){
        System.out.println("시가 대비 종가 가격 문제 생성 시작.");
        makeService.makeProblemEndFromStart();
        System.out.println("시가 대비 종가 가격 문제 생성 완료.");

        System.out.println("고가 대비 저가 가격 문제 생성 시작.");
        makeService.makeProblemMaxFromMin();
        System.out.println("고가 대비 저가 가격 문제 생성 완료.");

        System.out.println("코스피 문제 생성 시작");
        makeService.makeProblemAboutKospi();
        System.out.println("코스피 문제 생성 완료");

        System.out.println("코스닥 문제 생성 시작");
        makeService.makeProblemAboutKosdaq();
        System.out.println("코스닥 문제 생성 완료");
    }
}

package com.koscom.stockox.dto;

public class BaseProblem {

    int id;
    String answer;
    String wrong_answer;

    public void setId(){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getAnswer(){
        return this.answer;
    }

    public void setWrong_answer(String wrong_answer) {
        this.wrong_answer = wrong_answer;
    }
    public String getWrong_answer(){
        return this.wrong_answer;
    }
}
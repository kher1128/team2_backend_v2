package com.koscom.stockox.dto;

public class PriceProblem {

    String companyId;
    String problem;
    Boolean isTrue;
    String flag;
    int index;

    public PriceProblem(String companyId,String problem,Boolean isTrue,String flag,int index){
        this.companyId = companyId;
        this.problem = problem;
        this.isTrue = isTrue;
        this.flag = flag;
        this.index = index;
    }
    public void setCompanyId(String companyId){
        this.companyId = companyId;
    }
    public String getCompanyId(){
        return this.companyId;

    }

    public void setProblem(String problem){
        this.problem = problem;

    }
    public String getProblem(){
        return this.problem;

    }

    public void setIsTrue(Boolean isTrue){
        this.isTrue = isTrue;

    }
    public Boolean getIsTrue(){
        return this.isTrue;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getFlag(){
        return this.flag;
    }

    public void setIsTrue(int index){
        this.index = index;
    }
    public int getIndex(){
        return this.index;
    }
}

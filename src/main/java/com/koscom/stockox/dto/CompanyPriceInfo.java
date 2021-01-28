package com.koscom.stockox.dto;

public class CompanyPriceInfo {

    String companyId;
    Double companyStartPrice;
    Double companyEndPrice;
    Double companyMaxPrice;
    Double companyMinPrice;


    public CompanyPriceInfo(String companyId,Double companyStartPrice,Double companyEndPrice,Double companyMaxPrice,Double companyMinPrice){
        this.companyId = companyId;
        this.companyStartPrice = companyStartPrice;
        this.companyEndPrice = companyEndPrice;
        this.companyMaxPrice = companyMaxPrice;
        this.companyMinPrice = companyMinPrice;
    }


    /**
     * 2021.01.27 회사 ID 관련 정보 getter setter
     * csh1man
     */
    public void setCompanyId(String companyId){
        this.companyId = companyId;
    }
    public String getCompanyId(){
        return this.companyId;
    }
    /**
     * 2021.01.27 시가 관련 정보 getter setter
     * csh1man
     */
    public void setCompanyStartPrice(Double companyStartPrice){
        this.companyStartPrice = companyStartPrice;
    }
    public Double getCompanyStartPrice(){
        return this.companyStartPrice;
    }
    /**
     * 2021.01.27 종가 관련 정보 getter setter
     * csh1man
     */
    public void setCompanyEndPrice(Double companyEndPrice){
        this.companyEndPrice = companyEndPrice;
    }
    public Double getCompanyEndPrice(){
        return this.companyEndPrice;
    }

    /**
     * 2021.01.27 저가 관련 정보 getter setter
     * csh1man
     */
    public void setCompanyMinPrice(Double companyMinPrice){
        this.companyMinPrice = companyMinPrice;
    }
    public Double getCompanyMinPrice(){
        return this.companyMinPrice;
    }

    /**
     * 2021.01.27 고가 관련 정보 getter setter
     * csh1man
     */
    public void setCompanyMaxPrice(Double companyMaxPrice){
        this.companyMaxPrice = companyMaxPrice;
    }
    public Double getCompanyMaxPrice(){
        return this.companyMaxPrice;
    }
}


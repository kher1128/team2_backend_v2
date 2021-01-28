package com.koscom.stockox.dto;

/**
 * 2021.01.27 회사에 대한 정보를 담는 DTO
 * variable
 *  - companyId : 회사 ID (Primary Key)
 *  - companyName : 회사 명칭
 */
public class CompanyInfo {

    String companyId;

    String companyName;

    public CompanyInfo(String companyId,String companyName){
        this.companyId = companyId;
        this.companyName = companyName;
    }
    public void setCompanyId(String companyId){
        this.companyId = companyId;
    }
    public String getCompanyId(){
        return this.companyId;
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    public String getCompanyName(){
        return this.companyName;
    }
}

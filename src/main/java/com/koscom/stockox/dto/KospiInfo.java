package com.koscom.stockox.dto;

public class KospiInfo {

    Double startPrice;
    Double maxPrice;
    Double minPrice;
    Double tradePrice;

    /**
     * 2021.01.21 kospiinfo constructor
     * csh1man
     */
    public KospiInfo(Double startPrice,Double maxPrice,Double minPrice,Double tradePrice){
        this.startPrice= startPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.tradePrice = tradePrice;
    }
    /**
     * 2021.01.21 kospi 시가 정보 관련 setter getter
     * csh1man
     */
    public void setStartPrice(Double startPrice){
        this.startPrice = startPrice;
    }
    public Double getStartPrice(){
        return this.startPrice;
    }

    /**
     * 2021.01.21 kospi 고가 정보 관련 setter getter
     * csh1man
     */
    public void setMaxPrice(Double maxPrice){
        this.maxPrice = maxPrice;
    }
    public Double getMaxPrice(){
        return this.maxPrice;
    }

    /**
     * 2021.01.21 kospi 저가 정보 관련 setter getter
     * csh1man
     */
    public void setMinPrice(Double minPrice){
        this.maxPrice = minPrice;
    }
    public Double getMinPrice(){
        return this.minPrice;
    }

    /**
     * 2021.01.21 kospi 체결 가격 정보 관련 setter getter
     * csh1man
     */

    public void setTradePrice(Double tradePrice){
        this.tradePrice = tradePrice;
    }
    public Double getTradePrice(){
        return  this.tradePrice;
    }

}

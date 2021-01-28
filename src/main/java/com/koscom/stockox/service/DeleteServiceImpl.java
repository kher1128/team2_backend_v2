package com.koscom.stockox.service;

import com.koscom.stockox.mapper.KoscomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteServiceImpl implements  DeleteService{

    @Autowired
    KoscomMapper koscomMapper;

    @Override
    public void deleteCompanyInfo() {
        koscomMapper.deleteCompanyInfo();
    }

    @Override
    public void deleteCompanyPriceInfo() {
        koscomMapper.deleteCompanyPriceInfo();
    }

    @Override
    public void deleteKospiPriceInfo() {
        koscomMapper.deleteKospiInfo();
    }

    @Override
    public void deleteKosdaqPriceInfo() {
        koscomMapper.deleteKosdaqInfo();
    }

    @Override
    public void deletePriceBogi() {
        koscomMapper.deletePriceBogi();
    }

    @Override
    public void deleteProblem() {
        koscomMapper.deleteProblem();
    }
}

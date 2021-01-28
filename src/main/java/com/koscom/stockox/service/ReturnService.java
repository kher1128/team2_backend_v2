package com.koscom.stockox.service;

import org.json.simple.JSONArray;

import java.util.List;

public interface ReturnService {

    public JSONArray returnProblem(String company_id);
    public List returnCompanyList();
    public List returnNewsTitle(String ompany_name);
}

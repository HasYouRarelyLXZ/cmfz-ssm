package com.baizhi.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface DetailService {

    public Map<String, Object> getWenData(String id, String uid);
}

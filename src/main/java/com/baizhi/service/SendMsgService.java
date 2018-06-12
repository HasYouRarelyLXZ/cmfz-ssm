package com.baizhi.service;

import java.util.Map;

public interface SendMsgService {
    public Map<String, Object> check(String phone, String code);
}

package com.baizhi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 首页Service
 */
public interface ViewService {

    Map<String, Object> getFirst_page(String uid, String type, String sub_type);
}

package com.baizhi.service;

import com.baizhi.entity.Log;

import java.util.List;
import java.util.Map;

public interface LogService {
    Map<String, Object> queryLogAll(int page, int pageSize);

    List<Log> queryList();
}

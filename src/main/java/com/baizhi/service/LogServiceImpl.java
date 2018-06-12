package com.baizhi.service;

import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public Map<String, Object> queryLogAll(int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        page = (page - 1) * pageSize;
        int rowNum = logMapper.getRowNum();
        List<Log> logs = logMapper.selectAll(page, pageSize);
        map.put("page", rowNum);
        map.put("rows", logs);
        return map;
    }

    @Override
    public List<Log> queryList() {
        return logMapper.selectAllList();
    }
}

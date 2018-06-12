package com.baizhi.controller;

import com.baizhi.service.DetailService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 专辑详情及章节
 */
@Controller
public class DetailController {
    @Autowired
    private DetailService detailService;

    @ResponseBody
    @RequestMapping(value = "/wen", method = RequestMethod.GET)
    public Map<String, Object> wen(String id, String uid) {
        Map<String, Object> map = detailService.getWenData(id, uid);
        return map;
    }
}

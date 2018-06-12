package com.baizhi.controller;

import com.baizhi.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页视图
 */
@Controller
public class ViewFirstController {
    @Autowired
    public ViewService viewService;

    @ResponseBody
    @RequestMapping(value = "/first_page", method = RequestMethod.GET)
    public Map<String, Object> first_page(String uid, String type, String sub_type) {
        Map<String, Object> map = viewService.getFirst_page(uid, type, sub_type);
        return map;
    }

}

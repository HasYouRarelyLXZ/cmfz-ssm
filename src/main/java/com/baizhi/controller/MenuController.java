package com.baizhi.controller;

import com.baizhi.entity.Menu;
import com.baizhi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/Menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/showMenu")

    public @ResponseBody
    List<Menu> showMenu() {
        List<Menu> menus = menuService.queryMenuList();
        return menus;
    }
}

package com.baizhi.service;

import com.baizhi.entity.TAdmin;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface TAdminService {

    public Map<String,Object> login(String name, String password);

}

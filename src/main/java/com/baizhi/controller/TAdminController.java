package com.baizhi.controller;

import com.baizhi.entity.TAdmin;
import com.baizhi.service.TAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TAdminController {
    @Autowired
    private TAdminService tAdminService;

    /**
     * 处理/login请求
     */
    @ResponseBody
    @RequestMapping(value = "/loginAdmin")
    public Map<String,Object> login(String name,String password,String enCode,HttpSession session) {

        Map<String, Object> map=null;
        if(enCode!=null){
            String code = (String)session.getAttribute("code");
            //比较俩个验证码
            if(enCode.equalsIgnoreCase(code)) {  //如果验证码一致则进行后续操作
                map = tAdminService.login(name, password);
            }else {
                map=new HashMap<>();
                map.put("msg","验证码错误");
            }
        }
        return  map;
    }

    /*管理员用户登出
     * */
    @RequestMapping("/loginout")
    public String loginout(HttpSession session, SessionStatus sessionStatus) {
        String tuser =(String ) session.getAttribute("tuser");
        //System.out.println(user.getName());
        session.invalidate();
        return "login";
    }

}
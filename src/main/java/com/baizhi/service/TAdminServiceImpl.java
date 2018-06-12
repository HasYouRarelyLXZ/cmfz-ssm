package com.baizhi.service;

import com.baizhi.aop.LogAnnotation;
import com.baizhi.dao.TAdminDao;
import com.baizhi.entity.TAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class TAdminServiceImpl implements TAdminService {
    @Autowired
    private TAdminDao tAdminDao;


    @Override
    public Map<String, Object> login(String name, String password) {
        Map<String,Object> map=new HashMap<>();
        //确保用户名密码不为空
                if(name!=null||password!=password){
                    //用户信息验证
                    TAdmin tAdmin = tAdminDao.queryAdmin(name);
                    if(tAdmin!=null){  //用户名存在。验证密码
                        if(password.equals(tAdmin.getPassword())){
                            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                            request.getSession().setAttribute("tuser",tAdmin.getName());
                            map.put("msg","登录成功");
                        }else {
                            map.put("msg","密码错误");
                        }
                    }else {
                        map.put("msg","用户名不存在");
                    }
                }else {
                    map.put("msg","用户名或密码不能为空");
                }
            return  map;
        }
}

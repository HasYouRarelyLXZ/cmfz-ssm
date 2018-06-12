package com.baizhi.controller;


import com.baizhi.util.CreateValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/image")
public class ImageController {
    @RequestMapping("/code")
    public void getCodeNumber(HttpSession session, OutputStream outputStream) throws IOException {
        //得到随机数
        CreateValidateCode cvc = new CreateValidateCode();
        String code = cvc.getCode();
        //将code存入到session作用域，方便后面的比对
        session.setAttribute("code", code);
        //调用工具类的方法画图片
        cvc.write(outputStream);
    }

}

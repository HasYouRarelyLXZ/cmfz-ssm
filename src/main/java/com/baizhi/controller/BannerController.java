package com.baizhi.controller;


import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @ResponseBody
    @RequestMapping("/getjson")
    public Map<String, Object> getBannerJson(int page, int rows) {
        Map<String, Object> map = bannerService.queryBannerList(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("/add")
    public void addBanner(MultipartFile imgPath, String status, String desc, String title, HttpServletRequest request) {
        //1上传图片  获取到当前文件    存储位置    文件覆盖的问题（重命名）
        System.out.printf(status + "" + desc + "" + title);
        //获取当前项目路径

        String projetPath = request.getSession().getServletContext().getRealPath("/");

        File file = new File(projetPath);
        //web项目的路径
        String webappsPath = file.getParent();
        //上传文件夹的路径
        File uploadPath = new File(webappsPath + "/upload");
        //创建上传文件夹
        if (!uploadPath.exists()) {
            uploadPath.mkdir();
        }


        //获取原始文件名  1.jpg
        String oldFilename = imgPath.getOriginalFilename();
        //获取后缀名
        String prefix = FilenameUtils.getExtension(oldFilename);
        String id = UUID.randomUUID().toString().replace("-", "");
        String newName = id + "." + prefix;     //随机数+后缀

        try {
            //上传到指定的文件夹
            imgPath.transferTo(new File(uploadPath.getPath(), newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建Banner对象
        Banner banner = new Banner();
        banner.setId(id);
        banner.setTitle(title);
        banner.setImgPath("/upload/" + newName);
        banner.setDesc(desc);
        banner.setStatus(status);
        banner.setDate(new Date());
        //2将数据存储到数据库
        bannerService.insert(banner);
    }

    @ResponseBody
    @RequestMapping("/deletebanner")
    public void delete(String id) {
        System.out.println(id);
        bannerService.deleteBanner(id);
    }

    @ResponseBody
    @RequestMapping("/updatebanner")
    public void update(String id, String status) {
        bannerService.updateBanner(id, status);
    }

}

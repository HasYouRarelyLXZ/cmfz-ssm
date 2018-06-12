package com.baizhi.controller;


import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
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
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @ResponseBody
    @RequestMapping("/getAlbumJson")
    public Map<String, Object> getAlbumJson(int page, int rows) {
        System.out.println(page);
        System.out.println(rows);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Album> albums = albumService.queryAlbumAll(page, rows);
        int total = albumService.queryAlbumNum();
        map.put("total", total);
        map.put("rows", albums);
        return map;
    }

    /*添加专辑*/
    @ResponseBody
    @RequestMapping("/add")
    public void addAlbum(MultipartFile imgPath, Album album, HttpServletRequest request) {
        //1上传图片  获取到当前文件    存储位置    文件覆盖的问题（重命名）

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
        //获取id
        String id = UUID.randomUUID().toString().replace("-", "");
        //重命名文件
        String newName = id + "." + prefix;     //随机数+后缀

        try {
            //上传到指定的文件夹,其实就是文件的拷贝
            imgPath.transferTo(new File(uploadPath.getPath(), newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //补全对象的值
        album.setId(id);
        album.setCount(0);
        album.setCoverImg("/upload/" + newName);
        album.setPublishDate(new Date());
        //album.setChildren(null);
        //2将数据存储到数据库
        albumService.add(album);
    }
}




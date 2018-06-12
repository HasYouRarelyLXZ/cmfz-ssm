package com.baizhi.controller;


import com.baizhi.entity.Section;
import com.baizhi.service.AlbumService;
import com.baizhi.service.SectionService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;


@Controller
@RequestMapping("/chapter")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    /*添加章节*/
    @ResponseBody
    @RequestMapping("/add")
    public void addSection(MultipartFile addFile, String id, HttpServletRequest request) {
        //1上传图片  获取到当前文件    存储位置    文件覆盖的问题（重命名）

        //获取当前项目路径
        String projetPath = request.getSession().getServletContext().getRealPath("/");
        File file = new File(projetPath);
        //web项目的路径
        String webappsPath = file.getParent();
        //上传文件夹的路径
        File uploadPath = new File(webappsPath + "/audio");
        //创建上传文件夹
        if (!uploadPath.exists()) {
            uploadPath.mkdir();
        }

        //获取原始文件名  1.jpg
        String oldFilename = addFile.getOriginalFilename();
        //获取文件名前边的
       /* String[] split = oldFilename.split(".");
        String beforeName=split[0];*/
        //获取后缀名
        String prefix = FilenameUtils.getExtension(oldFilename);
        //获取id
        String sectionId = UUID.randomUUID().toString().replace("-", "");
        //重命名文件
        String newName = sectionId + "." + prefix;     //随机数+后缀

        try {
            //上传到指定的文件夹,其实就是文件的拷贝
            addFile.transferTo(new File(uploadPath.getPath(), newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //补全对象的值
        Section section = new Section();
        section.setId(sectionId);
        section.setOldname(oldFilename);
        section.setSize("120M");
        section.setDuration("3分钟"); //时长
        section.setTimes("5");
        section.setTitle(oldFilename);
        section.setUploadPath("/audio/" + newName);
        section.setUploadDate(new Date());
        section.setAlbum_id(id);
        //2将数据存储到数据库
        sectionService.insert(section);  //如果上传成功，则需要更新章节数量

    }

    @ResponseBody
    @RequestMapping("/download")
    public void download(String url, String name, HttpServletResponse response, HttpServletRequest request) {

        //     文件存储位置          url:/audio/b0879e48-fb84-4e93-b0e2-faaaee297f9f.mp3         歌曲.mp3
        String projectPath = request.getSession().getServletContext().getRealPath("/");

        File file = new File(projectPath);

        String webappsPath = file.getParent();

        String filePath = webappsPath + url;

        File downFile = new File(filePath);

        //设置解码方式
        String fileName = null;
        try {
            fileName = new String(name.getBytes("UTF-8"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应头   响应类型MP3
        response.setHeader("content-disposition", "attachment;fileName=" + fileName);
        response.setContentType("audio/mpeg");
        //响应出去
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(downFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}




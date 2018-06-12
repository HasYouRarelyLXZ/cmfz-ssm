package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.SectionDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DetailServiceIpml implements DetailService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    public Map<String, Object> getWenData(String id, String uid) {
        Map<String, Object> map = new HashMap<>();
        //判断用户id是否为空,uid由上级页面传来不应该为空
        if (id.isEmpty() || uid.isEmpty()) {
            map.put("errMsg", "用户ID不能为空");
            return map;
        } else {
            Album album = albumDao.queryAlbum(id);//获取一个专辑
            List<Section> children = album.getChildren();
            map.put("list", children);
            album.setChildren(null);  //用完children集合list存到map之后，把children置为null
            map.put("introduction", album); //得到一个children为空的album对象

            return map;
        }
    }
}

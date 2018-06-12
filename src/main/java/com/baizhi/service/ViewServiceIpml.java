package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ArticalMapper;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Artical;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页Service
 */
@Service
@Transactional
public class ViewServiceIpml implements ViewService {
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ArticalMapper articalMapper;
    @Autowired
    private BannerDao bannerDao;

    @Override
    public Map<String, Object> getFirst_page(String uid, String type, String sub_type) {
        Map<String, Object> map = new HashMap<>();
        if (type.isEmpty()) {
            map.put("errMsg", "请求数据类型不允许为空");
        } else {//类型已经不为空
            if (uid.isEmpty()) {
                map.put("errMsg", "用户ID不允许为空");
            } else {//用户ID和type都不为空
                if (type.equals("si")) { //如果数据类型为"si"
                    //判断第三个参数
                    if (sub_type.isEmpty()) {
                        map.put("errMsg", "数据类型为si,sub_type参数必须传入");
                    }
                    {//正常传入三个参数时候
                        if (sub_type.equals("ssyj")) {
                            //上师言教
                            //秒宝文章
                            List<Artical> articals = articalMapper.queryArticalALL(0, 2);
                            map.put("artical", articals);
                        } else {
                            //显秘法要
                            User user = userDao.queryUserByID(uid);
                            //获取对应用户的上师id
                            String duru_id = user.getDuru_id();
                            //articalMapper.selectByPrimaryKey(duru_id);
                            //文章模块到此暂停
                        }
                    }
                } else {//type不是si,正常传入其他俩个参数
                    if (type.equals("all")) {//首页
                        //轮播图
                        List<Banner> banners = bannerDao.queryBanner(0, 5);
                        //妙音专辑
                        List<Album> albums = albumDao.queryAlbumALL(0, 6);
                        //秒宝文章
                        List<Artical> articals = articalMapper.queryArticalALL(0, 2);
                        map.put("header", banners);
                        map.put("album", albums);
                        map.put("artical", articals);
                    } else {//闻
                        //妙音专辑
                        List<Album> albums = albumDao.queryAlbumALL(0, 6);
                        map.put("album", albums);
                    }
                }
            }
        }
        return map;
    }
}

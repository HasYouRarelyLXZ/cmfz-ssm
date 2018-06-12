package com.baizhi.service;

import com.baizhi.aop.LogAnnotation;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    public Map<String, Object> queryBannerList(int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        int page = (pageNumber - 1) * pageSize;
        List<Banner> banners = bannerDao.queryBanner(page, pageSize);
        int bannerNum = bannerDao.queryBannerNum();
        map.put("total", bannerNum);
        map.put("rows", banners);
        return map;
    }

    @Override
    public int queryBannerNum() {
        return bannerDao.queryBannerNum();
    }

    @LogAnnotation(name = "添加一个轮播图")
    @Override
    public void insert(Banner banner) {
        bannerDao.add(banner);
    }

    @LogAnnotation(name = "通过id更改轮播图状态")
    @Override
    public void updateBanner(String id, String status) {
        bannerDao.update(id, status);
    }

    @LogAnnotation(name = "删除轮播图")
    @Override
    public void deleteBanner(String id) {
        bannerDao.delete(id);
    }

}

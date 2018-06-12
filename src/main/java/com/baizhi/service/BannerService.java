package com.baizhi.service;

import com.baizhi.entity.Banner;
import com.baizhi.entity.Menu;

import java.util.List;
import java.util.Map;

public interface BannerService {
    Map<String, Object> queryBannerList(int pageNumber, int pageSize);

    int queryBannerNum();

    void insert(Banner banner);

    void updateBanner(String id, String status);

    void deleteBanner(String id);

}

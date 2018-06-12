package com.baizhi.dao;

import com.baizhi.entity.Banner;
import com.baizhi.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    List<Banner> queryBanner(@Param("page") int page, @Param("pageSize") int pageSize);

    int queryBannerNum();

    void add(Banner banner);

    void update(@Param("id") String id, @Param("status") String status);

    void delete(String id);
}

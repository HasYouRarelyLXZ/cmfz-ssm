package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //分页展示所有
    List<Album> queryAlbumALL(@Param("page") int page, @Param("pageSize") int pageSize);

    //查询总条数
    int queryAlbumNum();

    //查询一条专辑信息
    Album queryAlbum(String id);

    //添加专辑
    void insert(Album album);

    //更新章节数量
    void update(@Param("id") String id, @Param("count") int count);
}

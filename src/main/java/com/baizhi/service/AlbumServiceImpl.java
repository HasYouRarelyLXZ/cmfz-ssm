package com.baizhi.service;

import com.baizhi.aop.LogAnnotation;
import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    public Album queryAlbum(String id) {        //查询单条专辑
        return albumDao.queryAlbum(id);
    }

    @Override
    public List<Album> queryAlbumAll(int pageNumber, int pageSize) {
        int page = (pageNumber - 1) * pageSize;
        return albumDao.queryAlbumALL(page, pageSize);
    }

    @Override
    public int queryAlbumNum() {
        return albumDao.queryAlbumNum();
    }

    @LogAnnotation(name = "添加一个专辑")
    @Override
    public void add(Album album) {   //添加专辑
        albumDao.insert(album);
    }
}

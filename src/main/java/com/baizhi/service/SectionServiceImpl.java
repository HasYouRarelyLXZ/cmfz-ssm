package com.baizhi.service;

import com.baizhi.aop.LogAnnotation;
import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.SectionDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SectionServiceImpl implements SectionService {
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private AlbumDao albumDao;

    @Override
    @LogAnnotation(name = "添加一个章节")
    public void insert(Section section) {
        sectionDao.add(section);
        //如果插入成功，不发生异常
        Album album = albumDao.queryAlbum(section.getAlbum_id());
        int count = album.getCount();
        int newCount = count + 1; //得到新数量
        //更新对应章节数
        albumDao.update(album.getId(), newCount);
    }
}
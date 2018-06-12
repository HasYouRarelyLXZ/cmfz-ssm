package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;

public interface AlbumService {
    Album queryAlbum(String id);

    List<Album> queryAlbumAll(int pageNumber, int pageSize);

    int queryAlbumNum();

    void add(Album album);
}

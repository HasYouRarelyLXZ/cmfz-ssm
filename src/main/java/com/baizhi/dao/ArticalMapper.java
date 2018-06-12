package com.baizhi.dao;

import com.baizhi.entity.Artical;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticalMapper {
    int deleteByPrimaryKey(String id);

    int insert(Artical record);

    int insertSelective(Artical record);

    Artical selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Artical record);

    int updateByPrimaryKey(Artical record);

    List<Artical> queryArticalALL(@Param("page") int page, @Param("pageSize") int pageSize);
}
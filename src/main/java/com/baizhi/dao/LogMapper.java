package com.baizhi.dao;

import com.baizhi.entity.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogMapper {
    int deleteByPrimaryKey(String id);

    int insert(Log record);

    int getRowNum();

    int insertSelective(Log record);

    Log selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);

    List<Log> selectAll(@Param("page") int page, @Param("pageSize") int pageSize);

    List<Log> selectAllList();

}
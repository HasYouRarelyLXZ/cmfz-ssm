package com.baizhi.dao;

import com.baizhi.entity.TAdmin;
import org.apache.ibatis.annotations.Param;

public interface TAdminDao {
    TAdmin queryAdmin(String name);
}

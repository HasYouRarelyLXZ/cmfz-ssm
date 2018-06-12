package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //查询所有用户
    List<User> queryUser(@Param("page") int page, @Param("pageSize") int pageSize);
    //根据省份分布

    //返回总条数
    int queryUserNum();

    //冻结用户
    void updateUserStatus(@Param("id") String id, @Param("status") String status);

    User queryUserByID(String id);

    //用户分布分组查询
    List<UserModel> queryBySex(String sex);

    //一定日期内用户注册数量
    int queryNamesByDate(int date);

    //插入一条数据
    void insertSelective(User user);

    //批量插入数据
    void insertBatch(List<User> list);

    //根据手机号查询用户信息
    User queryByPhone(String phone);

    //修改用户信息
    void updateByPrimaryKeySelective(User user);

    //获取金刚道友
    List<User> queryOthersUserByID(String id);

}

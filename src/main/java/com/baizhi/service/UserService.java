package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.User;
import com.baizhi.entity.UserModel;

import java.util.List;
import java.util.Map;

public interface UserService {
    //查询用户列表
    List<User> showUserAll(int pageNumber, int pageSize);

    //返回总条数
    int getUserNum();

    //冻结用户
    void changeUserStatus(String id, String status);

    //查询一个用户
    User getUserByID(String id);

    User getUserByPhone(String phone);
    Map<String,Object> getUserByPhoneUnix(String phone);
    List<UserModel> queryBySexGroup(String sex);

    int queryByDate(int date);

    void save(List<User> list);

    //用户手机号密码登录
    Map<String, Object> login(String phone, String password);

    //用户注册
    public Map<String, Object> regist(String phone, String password);

    //根据id修改用户信息
    public Map<String, Object> mofify(User user);

    //获取金刚道友其他修行用户
    public Map<String, Object> member(String uid);
}

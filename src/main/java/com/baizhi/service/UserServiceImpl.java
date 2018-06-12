package com.baizhi.service;

import com.baizhi.aop.LogAnnotation;
import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.ErrorMsg;
import com.baizhi.entity.User;
import com.baizhi.entity.UserModel;
import com.baizhi.util.MD5Util;
import com.baizhi.util.SaltUtil;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public List<User> showUserAll(int pageNumber, int pageSize) {
        int page = (pageNumber - 1) * pageSize;
        return userDao.queryUser(page, pageSize);
    }

    @Override
    public int getUserNum() {
        return userDao.queryUserNum();
    }

    @LogAnnotation(name = "更改用户状态")
    @Override
    public void changeUserStatus(String id, String status) {
        userDao.updateUserStatus(id, status);
    }

    @Override
    public User getUserByID(String id) {
        return userDao.queryUserByID(id);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userDao.queryByPhone(phone);
    }

    @Override
    public Map<String, Object> getUserByPhoneUnix(String phone) {
        User user = userDao.queryByPhone(phone);
        Map<String,Object> map=new HashMap<>();
        if(user!=null){
            map.put("success","登陆成功");
            map.put("user",user);
        }else{
            map.put("errMsg","手机号不存在");
        }
        return map;

    }

    @Override
    public List<UserModel> queryBySexGroup(String sex) {
        return userDao.queryBySex(sex);
    }

    @Override
    public int queryByDate(int date) {
        return userDao.queryNamesByDate(date);
    }

    @LogAnnotation(name = "批量插入用户数据")
    @Override
    public void save(List<User> list) {
        userDao.insertBatch(list);
    }

    @Override
    public Map<String, Object> login(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        if (phone.isEmpty() || password.isEmpty()) {
            map.put("errMsg", "手机号或密码不能为空");
        } else {
            User user = userDao.queryByPhone(phone);
            if (user == null) { //如果查询没有结果，返回为空
                map.put("errMsg", "不存在该用户，请先注册");
            } else { //如果用户存在，则比对验证密码是否一致
                //获取数据库中存储的密码
                String password1 = user.getPassword();
                //对当前密码进行MD5+盐加密
                //1.获取数据库中存储的盐值、
                String salt = user.getSalt();
                String md5CodePassword = MD5Util.getMd5Code(password, salt);
                //比对俩个密码
                if (md5CodePassword.equals(password1)) {
                    //如果俩个密码一致
                    map.put("success", user);
                } else {
                    map.put("errMsg", new ErrorMsg("-200", "密码错误"));
                }
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> regist(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        if (phone.isEmpty() || password.isEmpty()) {
            map.put("errMsg", "手机号或密码不能为空");
        } else {
            //根据手机号查询用户是否存在
            User user = userDao.queryByPhone(phone);
            if (user != null) {
                //用户已经存在
                map.put("errMsg", new ErrorMsg("-200", "该手机号已经存在"));
            } else {
                //用户不存就注册用户
                User user1 = new User();
                //生成随机的id
                user1.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                //对密码加盐处理
                String salt = SaltUtil.getSalt();
                //对密码加密
                String passwordMd5 = MD5Util.getMd5Code(password, salt);
                //插库
                user1.setSalt(salt);
                user1.setPassword(passwordMd5);
                userDao.insertSelective(user1);
                //插库成功
                User user2 = userDao.queryByPhone(phone);
                map.put("success", user2);
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> mofify(User user) {
        Map<String, Object> map = new HashMap<>();
        if (user == null || user.getId().isEmpty()) {
            map.put("errMsg", "用户或用户id不能为空");
        } else {
            //更新数据
            userDao.updateByPrimaryKeySelective(user);
            User user1 = userDao.queryUserByID(user.getId());
            map.put("success", user1);
        }
        return map;
    }

    @Override
    public Map<String, Object> member(String uid) {
        Map<String, Object> map = new HashMap<>();
        if (uid.isEmpty()) {
            map.put("errMsg", "用户id不能为空");
        } else {
            List<User> users = userDao.queryOthersUserByID(uid);
            map.put("success", users);
        }
        return map;
    }

}

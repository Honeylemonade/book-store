package com.xyp.backendbookstore.service;

import com.xyp.backendbookstore.dao.UserDao;
import com.xyp.backendbookstore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * UserService:
 *
 * @Author XvYanpeng
 * @Date 2019/12/4 11:19
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User findUserByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    public ArrayList<String> getRolesByUserId(Integer userId) {
        return  userDao.getRolesByUserId(userId);
    }
}

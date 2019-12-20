package com.xyp.backendbookstore.service;

import com.xyp.backendbookstore.dao.AuthorityDao;
import com.xyp.backendbookstore.dao.UserDao;
import com.xyp.backendbookstore.entity.Authority;
import com.xyp.backendbookstore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * SecurityService:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 13:40
 */
@Service
public class SecurityService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Autowired
    AuthorityDao authorityDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        ArrayList<Authority> authorityArrayList = authorityDao.findByUserName(userName);
        String[] authorities = new String[authorityArrayList.size()];
        for (int i = 0; i < authorities.length; i++) {
            authorities[i] = authorityArrayList.get(i).getAuthorityName();
        }
        //创建userDetail
        UserDetails userDetail = org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
        return userDetail;
    }
}

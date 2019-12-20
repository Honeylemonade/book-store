package com.xyp.backendbookstore.service;

import com.xyp.backendbookstore.dao.UserDao;
import com.xyp.backendbookstore.dto.LoginDto;
import com.xyp.backendbookstore.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * LoginService:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 19:43
 */
@Service
public class LoginService {
    @Autowired
    UserDao userDao;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    SecurityService securityService;
    @Autowired
    JWTUtil jwtUtil;

    public String Login(LoginDto loginDto) {
        try {
            //认证用户身份
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
            //认证通过后，获取当前用户的userdetail
            UserDetails userDetails = securityService.loadUserByUsername(loginDto.getUserName());
            //生成token
            String token = jwtUtil.generateToken(userDetails);
            return token;
        } catch (Exception e) {
            return null;
        }
    }
}

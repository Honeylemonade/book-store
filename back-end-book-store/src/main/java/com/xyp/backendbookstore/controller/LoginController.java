package com.xyp.backendbookstore.controller;

import com.xyp.backendbookstore.dto.LoginDto;
import com.xyp.backendbookstore.entity.User;
import com.xyp.backendbookstore.service.LoginService;
import com.xyp.backendbookstore.service.UserService;
import com.xyp.backendbookstore.util.ResponseResult;
import com.xyp.backendbookstore.vo.LoginRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * LoginController:
 *
 * @Author XvYanpeng
 * @Date 2019/11/29 8:52
 */
@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginRequestVo loginRequestVo) {
        try {
            LoginDto loginDto = new LoginDto(loginRequestVo.getUserName(), loginRequestVo.getPassword());
            String token = loginService.Login(loginDto);
            User user = userService.findUserByUserName(loginRequestVo.getUserName());
            Map map = new HashMap();
            map.put("token", token);
            map.put("userName", user.getUserName());
            map.put("userId", user.getUserId());
            ArrayList<String> roles = userService.getRolesByUserId(user.getUserId());
            //判断是否获取到token并且拥有普通用户身份
            if (token != null && roles.contains("普通用户")) {
                return new ResponseResult(0, "登录成功！", map);
            } else {
                return new ResponseResult(1, "登录失败！", null);
            }
        } catch (Exception e) {
            return new ResponseResult(1, "登录失败！", null);
        }
    }

    @PostMapping("/adminlogin")
    public ResponseResult adminLogin(@RequestBody LoginRequestVo loginRequestVo) {
        try {
            LoginDto loginDto = new LoginDto(loginRequestVo.getUserName(), loginRequestVo.getPassword());
            String token = loginService.Login(loginDto);
            User user = userService.findUserByUserName(loginRequestVo.getUserName());
            Map map = new HashMap();
            map.put("token", token);
            map.put("userName", user.getUserName());
            map.put("userId", user.getUserId());
            ArrayList<String> roles = userService.getRolesByUserId(user.getUserId());
            //判断是否获取到token并且拥有普通用户身份
            if (token != null&&roles.contains("管理员")) {
                return new ResponseResult(0, "登录成功！", map);
            } else {
                return new ResponseResult(1, "登录失败！", null);
            }
        } catch (Exception e) {
            return new ResponseResult(1, "登录失败！", null);
        }
    }
}

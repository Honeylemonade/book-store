package com.xyp.backendbookstore.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginRequestVo:用于封装客户端发送的账号密码
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 19:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestVo {
    private String userName;
    private String password;
}

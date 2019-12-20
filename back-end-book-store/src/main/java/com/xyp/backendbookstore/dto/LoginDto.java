package com.xyp.backendbookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginDto:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 19:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String userName;
    private String password;
}

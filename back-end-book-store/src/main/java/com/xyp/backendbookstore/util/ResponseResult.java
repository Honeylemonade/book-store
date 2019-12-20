package com.xyp.backendbookstore.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ResponseResult:用于封装response
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 19:31
 */
@Data
@AllArgsConstructor
public class ResponseResult {
    private int code;
    private String message;
    private Object result;

}

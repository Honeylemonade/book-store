package com.xyp.backendbookstore.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

/**
 * JWTUtil:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 19:54
 */
@Component
public class JWTUtil {
    private String SECRET_KEY = "159852";

    /**
     * @Author XvYanpeng
     * @Description 根据token获取claim，能获取到则说明是正确的token
     * @Date 2019/11/28 20:12
     */
    public Claims getClaims(String token) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            throw new Exception("token 被篡改");
        }
    }

    /**
     * @Author XvYanpeng
     * @Description 根据token获取username
     * @Date 2019/11/9 14:32
     */
    public String getUserName(String token) throws Exception {
        Claims claims = getClaims(token);
        String userName = claims.getSubject();
        return userName;
    }

    public String[] getAuthorities(String token) throws Exception {
        Claims claims = getClaims(token);
        ArrayList<Map> mapArrayList = (ArrayList<Map>) claims.get("authorities");
        String[] strings = new String[mapArrayList.size()];
        for (int i = 0; i < mapArrayList.size(); i++) {
            strings[i] = (String) mapArrayList.get(i).get("authority");
        }
        return strings;
    }

    /**
     * @Author XvYanpeng
     * @Description 判断token是否过期
     * @Date 2019/11/9 14:33
     */
    public boolean isTokenExpired(String token) throws Exception {
        Claims claims = getClaims(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }


    /**
     * @Author XvYanpeng
     * @Description 创建一个jwt
     * @Date 2019/11/9 14:35
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            //能够解析说明没有修改过
            Claims claims = getClaims(token);
            //并且不过期，则token有效
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}

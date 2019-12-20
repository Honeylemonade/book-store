package com.xyp.backendbookstore.filter;

import com.xyp.backendbookstore.service.SecurityService;
import com.xyp.backendbookstore.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWTRequestFilter:
 *
 * @Author XvYanpeng
 * @Date 2019/11/29 9:26
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    SecurityService securityService;
    @Autowired
    JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String token = null;
            String userName = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                userName = jwtUtil.getUserName(token);
            }
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.validateToken(token)) {
                    UserDetails userDetails = User.withUsername(userName).password("").authorities(jwtUtil.getAuthorities(token)).build();
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            //token错误或失效，停止解析直接交给security
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}

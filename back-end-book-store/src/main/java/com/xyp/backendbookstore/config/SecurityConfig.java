package com.xyp.backendbookstore.config;

import com.xyp.backendbookstore.filter.JWTRequestFilter;
import com.xyp.backendbookstore.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 13:38
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityService securityService;
    @Autowired
    JWTRequestFilter jwtRequestFilter;

    /**
     * @Author XvYanpeng
     * @Description 用于配置“认证”
     * @Date 2019/11/29 9:40
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //交给service去数据库中查询具体用户信息
        auth.userDetailsService(securityService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * @Author XvYanpeng
     * @Description 用于配置“授权”
     * @Date 2019/11/29 9:40
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/billinformations").hasAuthority("get*/billinformations")
                .antMatchers(HttpMethod.GET, "/allbillinformations").hasAuthority("get*/allbillinformations")
                .antMatchers(HttpMethod.GET, "/monthlyincome/{number}").hasAuthority("get*/monthlyincome/{number}")
                .antMatchers(HttpMethod.GET, "/topsaillingbook").hasAuthority("get*/topsaillingbook")
                .antMatchers(HttpMethod.POST, "/books").hasAuthority("post*/books")
                .antMatchers(HttpMethod.PUT, "/books").hasAuthority("put*/books")
                .antMatchers(HttpMethod.DELETE, "/books/{bookId}").hasAuthority("delete*/books/{bookId}")
                .antMatchers(HttpMethod.PATCH, "/books/{bookId}/{number}").hasAuthority("patch*/books/{bookId}/{number}")
                .antMatchers(HttpMethod.GET, "/books/recommendedBooks").hasAuthority("get*/books/recommendedBooks")
                .antMatchers(HttpMethod.PATCH, "/transaction/{id}").hasAuthority("patch*/transaction/{id}")
                .antMatchers(HttpMethod.POST, "/transaction").hasAuthority("post*/transaction")
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //添加JWT解析过滤器
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * @Author XvYanpeng
     * @Description 提取出manager，用于认证
     * @Date 2019/11/29 9:40
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

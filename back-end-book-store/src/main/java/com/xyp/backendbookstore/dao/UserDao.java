package com.xyp.backendbookstore.dao;

import com.xyp.backendbookstore.entity.Authority;
import com.xyp.backendbookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * UserDao:
 *
 * @Author XvYanpeng
 * @Date 2019/11/27 22:37
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
/**
* @Author XvYanpeng
* @Description 根据用户id查询用户角色
* @Date 2019/12/4 12:34
*/
    @Query("select role.roleName" +
            " from User user," +
            " UserRoleRelation userRoleRelation," +
            " Role role" +
            " where user.userId=userRoleRelation.userId" +
            " and userRoleRelation.roleId=role.roleId" +
            " and user.userId=?1")
    ArrayList<String> getRolesByUserId(Integer userId);
}

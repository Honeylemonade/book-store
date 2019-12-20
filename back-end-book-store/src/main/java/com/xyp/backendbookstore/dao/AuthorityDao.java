package com.xyp.backendbookstore.dao;

import com.xyp.backendbookstore.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * AuthorityDao:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 14:06
 */
@Repository
public interface AuthorityDao extends JpaRepository<Authority, Integer> {
    @Query(value = "SELECT\n" +
            "\tauthority.*\n" +
            "FROM\n" +
            "\tauthority \n" +
            "WHERE\n" +
            "\tauthority_id IN (\n" +
            "\tSELECT\n" +
            "\t\tauthority_id \n" +
            "\tFROM\n" +
            "\t\t`role_authority_relation` \n" +
            "\tWHERE\n" +
            "\trole_id = ( SELECT role_id FROM user_role_relation WHERE user_id = ( SELECT user_id FROM USER WHERE user_name = ? ) ) \n" +
            "\t)", nativeQuery = true)
    ArrayList<Authority> findByUserName(String userName);
}

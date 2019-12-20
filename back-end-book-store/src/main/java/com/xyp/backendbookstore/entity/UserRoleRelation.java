package com.xyp.backendbookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * UserRoleRelation:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 9:52
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userRoleRelationId;
    private Integer userId;
    private Integer roleId;
}

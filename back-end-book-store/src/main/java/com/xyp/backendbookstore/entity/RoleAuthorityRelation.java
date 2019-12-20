package com.xyp.backendbookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * RoleAuthorityId:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 9:57
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAuthorityRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleAuthorityId;
    private Integer roleId;
    private Integer authorityId;
}

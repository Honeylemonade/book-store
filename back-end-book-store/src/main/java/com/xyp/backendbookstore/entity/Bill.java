package com.xyp.backendbookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Order:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 9:58
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billId;
    private Integer userId;
    private Integer billPrice;
    private Integer billTime;
}

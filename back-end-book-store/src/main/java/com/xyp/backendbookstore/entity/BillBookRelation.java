package com.xyp.backendbookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * OrderBookRelation:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 10:01
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillBookRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billBookId;
    private Integer billId;
    private Integer bookId;
    private Boolean flag;

}

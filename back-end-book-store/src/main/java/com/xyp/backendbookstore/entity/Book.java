package com.xyp.backendbookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Book:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 9:59
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    private String bookName;
    private String author;
    private String type;
    private String publisher;
    private Integer bookPrice;
    private Integer amount;
}

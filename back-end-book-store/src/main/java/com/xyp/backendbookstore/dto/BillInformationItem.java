package com.xyp.backendbookstore.dto;

import com.xyp.backendbookstore.entity.Bill;
import com.xyp.backendbookstore.entity.BillBookRelation;
import com.xyp.backendbookstore.entity.Book;
import com.xyp.backendbookstore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillInformationItem:
 *
 * @Author XvYanpeng
 * @Date 2019/12/2 20:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillInformationItem {
    private Integer bookId;
    private String bookName;
    private String author;
    private String type;
    private String publisher;
    private Integer bookPrice;
    private Integer amount;
    private Integer billBookId;
    private Integer billId;
    private Boolean flag;
    private Integer userId;
    private String userName;
    private Integer billPrice;
    private Integer billTime;

    public BillInformationItem(Book book, BillBookRelation billBookRelation, Bill bill, User user) {
        this.bookId = book.getBookId();
        this.bookName = book.getBookName();
        this.author = book.getAuthor();
        this.type = book.getType();
        this.publisher = book.getPublisher();
        this.bookPrice = book.getBookPrice();
        this.amount = book.getAmount();
        this.billBookId = billBookRelation.getBillBookId();
        this.billId = billBookRelation.getBillId();
        this.flag = billBookRelation.getFlag();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.billPrice = bill.getBillPrice();
        this.billTime = bill.getBillTime();
    }
}

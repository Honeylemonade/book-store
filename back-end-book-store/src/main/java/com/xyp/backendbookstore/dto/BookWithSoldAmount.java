package com.xyp.backendbookstore.dto;

import com.xyp.backendbookstore.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * BookWithSoldAmount:
 *
 * @Author XvYanpeng
 * @Date 2019/12/1 13:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithSoldAmount {
    private Integer bookId;
    private String bookName;
    private String author;
    private String type;
    private String publisher;
    private Integer bookPrice;
    private Integer amount;
    private Integer soldAmount;

    public BookWithSoldAmount(Book book, Long soldAmount) {
        this.bookId = book.getBookId();
        this.bookName = book.getBookName();
        this.author = book.getAuthor();
        this.type = book.getType();
        this.publisher = book.getPublisher();
        this.bookPrice = book.getBookPrice();
        this.amount = book.getAmount();
        this.soldAmount = Integer.valueOf(Math.toIntExact(soldAmount));
    }
}

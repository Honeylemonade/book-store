package com.xyp.backendbookstore.dao;

import com.xyp.backendbookstore.dto.BookWithSoldAmount;
import com.xyp.backendbookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * BookDao:
 *
 * @Author XvYanpeng
 * @Date 2019/11/29 10:00
 */
@Repository
public interface BookDao extends JpaRepository<Book, Integer> {
    @Override
    ArrayList<Book> findAll();

    /**
     * @Author XvYanpeng
     * @Description 查找书籍销售排名
     * @Date 2019/12/2 19:18
     */
    @Query("select new com.xyp.backendbookstore.dto.BookWithSoldAmount(b,count(c)) " +
            "from " +
            "Book b ," +
            "BillBookRelation c " +
            "where b.bookId=c.bookId" +
            " group by c.bookId" +
            " order by count (c) desc ")
    ArrayList<BookWithSoldAmount> getTopSaillingBook();

    Book findByBookId(Integer bookId);
}
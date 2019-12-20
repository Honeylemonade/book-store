package com.xyp.backendbookstore.service;

import com.xyp.backendbookstore.dao.BillBookRelationDao;
import com.xyp.backendbookstore.dao.BookDao;
import com.xyp.backendbookstore.dto.BookWithSoldAmount;
import com.xyp.backendbookstore.entity.Book;
import com.xyp.backendbookstore.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * BookService:
 *
 * @Author XvYanpeng
 * @Date 2019/11/29 9:58
 */
@Service
public class BookService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    BookDao bookDao;
    @Autowired
    BillBookRelationDao billBookRelationDao;


    public ArrayList<Book> findAvailableFromRedis() {
        //首先去缓存中查询
        ArrayList<Book> bookArrayList = redisUtil.findAll();
        //如果查询不到，则刷新缓存
        if (bookArrayList.size() == 0) {
            redisUtil.refresh();
            bookArrayList = redisUtil.findAll();
        }
        return bookArrayList;
    }

    public ArrayList<Book> findAll() {
        ArrayList<Book> bookArrayList = bookDao.findAll();
        return bookArrayList;
    }

    /**
     * @Author XvYanpeng
     * @Description 查询书籍排行信息
     * @Date 2019/12/1 15:10
     */
    public ArrayList<BookWithSoldAmount> topSaillingBook() {
        ArrayList<BookWithSoldAmount> bookArrayList = bookDao.getTopSaillingBook();
        return bookArrayList;
    }

    /**
     * @Author XvYanpeng
     * @Description 插入书籍
     * @Date 2019/12/7 14:24
     */
    public void save(Book book) throws Exception {
        //检查是否存在空字段
        if (book.getBookName() == null ||
                book.getAuthor() == null ||
                book.getType() == null ||
                book.getPublisher() == null ||
                book.getBookPrice() == null) {
            throw new Exception("存在空字段");
        } else {
            if (book.getAmount() == null) {
                //插入数据库,初始数量为0
                book.setAmount(0);
            }
            bookDao.save(book);
        }
    }

    public void deleteBookById(Integer bookId) {
        bookDao.deleteById(bookId);
    }

    /**
     * @Author XvYanpeng
     * @Description 根据id进货
     * @Date 2019/12/8 22:16
     */
    public void purchase(Integer bookId, Integer number) throws Exception {
        try {
            if (bookId == null || number <= 0) {
                throw new Exception("error");
            } else {
                Book book = bookDao.findByBookId(bookId);
                book.setAmount(book.getAmount() + number);
                bookDao.save(book);
            }
        } catch (Exception e) {
            throw new Exception("error");
        }
    }

    /**
     * @Author XvYanpeng
     * @Description 获取推荐书籍
     * 普通当库存不足100时，推荐购进
     * 前三名书籍库存不足200时，推荐购进
     * @Date 2019/12/9 9:59
     */
    public ArrayList<Book> getRecommendedBooks() {
        HashSet<Book> recommendedBooks = new HashSet<>();
        //普通当库存不足100时，推荐购进
        ArrayList<Book> bookArrayList = bookDao.findAll();
        for (Book book : bookArrayList) {
            if (book.getAmount() < 100) {
                recommendedBooks.add(book);
            }
        }
        //前三名书籍库存不足200时，推荐购进
        ArrayList<BookWithSoldAmount> topSaillingBooks = bookDao.getTopSaillingBook();
        ArrayList<Book> temp = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (topSaillingBooks.get(i).getAmount() < 200) {
                Book tempBook = new Book(
                        topSaillingBooks.get(i).getBookId(),
                        topSaillingBooks.get(i).getBookName(),
                        topSaillingBooks.get(i).getAuthor(),
                        topSaillingBooks.get(i).getType(),
                        topSaillingBooks.get(i).getPublisher(),
                        topSaillingBooks.get(i).getBookPrice(),
                        topSaillingBooks.get(i).getAmount()
                );
                recommendedBooks.add(tempBook);
            }
        }
        //转换为ArrayList
        return new ArrayList<>(recommendedBooks);
    }
}

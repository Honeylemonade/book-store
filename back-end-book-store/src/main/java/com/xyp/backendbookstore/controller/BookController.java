package com.xyp.backendbookstore.controller;

import com.xyp.backendbookstore.dto.BookWithSoldAmount;
import com.xyp.backendbookstore.entity.Book;
import com.xyp.backendbookstore.service.BookService;
import com.xyp.backendbookstore.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * BookController:
 *
 * @Author XvYanpeng
 * @Date 2019/11/28 12:47
 */
@RestController
@CrossOrigin
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/books")
    public ResponseResult books() {
        try {
            ArrayList<Book> arrayList = bookService.findAll();
            return new ResponseResult(0, "success", arrayList);
        } catch (Exception e) {
            return new ResponseResult(1, "error", null);
        }
    }

    @GetMapping("/availablebooks")
    public ResponseResult availablebooks() {
        try {
            ArrayList<Book> arrayList = bookService.findAvailableFromRedis();
            //去除无货商品
            for (Book book : arrayList) {
                if (book.getAmount() <= 0) {
                    arrayList.remove(book);
                }
            }
            return new ResponseResult(0, "success", arrayList);
        } catch (Exception e) {
            return new ResponseResult(1, "error", null);
        }
    }

    @GetMapping("/topsaillingbook")
    public ResponseResult topsaillingbook() {
        try {
            ArrayList<BookWithSoldAmount> arrayList = bookService.topSaillingBook();
            return new ResponseResult(0, "success", arrayList);
        } catch (Exception e) {
            return new ResponseResult(1, "error", null);
        }
    }

    @PostMapping("/books")
    public ResponseResult insertBook(@RequestBody Book book) {
        try {
            bookService.save(book);
            return new ResponseResult(0, "插入成功", null);
        } catch (Exception e) {
            return new ResponseResult(1, "插入失败，请检查填写信息", null);
        }
    }

    @PutMapping("/books")
    public ResponseResult updateBook(@RequestBody Book book) {
        try {
            bookService.save(book);
            return new ResponseResult(0, "修改成功", null);
        } catch (Exception e) {
            return new ResponseResult(1, "修改失败", null);
        }
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseResult deleteBook(@PathVariable("bookId") Integer bookId) {
        try {
            bookService.deleteBookById(bookId);
            return new ResponseResult(0, "删除成功", null);
        } catch (Exception e) {
            return new ResponseResult(1, "删除失败", null);
        }
    }

    @PatchMapping("/books/{bookId}/{number}")
    public ResponseResult deleteBook(@PathVariable("bookId") Integer bookId, @PathVariable("number") Integer number) {
        try {
            bookService.purchase(bookId, number);
            return new ResponseResult(0, "进货成功", null);
        } catch (Exception e) {
            return new ResponseResult(1, "进货失败", null);
        }
    }

    @GetMapping("/books/recommendedBooks")
    public ResponseResult recommendedBooks() {
        try {
            ArrayList<Book> bookArrayList = bookService.getRecommendedBooks();
            return new ResponseResult(0, "查看推荐成功", bookArrayList);
        } catch (Exception e) {
            return new ResponseResult(1, "查看推荐失败", null);
        }
    }
}

package com.xyp.backendbookstore.dao;

import com.xyp.backendbookstore.dto.BillInformationItem;
import com.xyp.backendbookstore.dto.BookWithSoldAmount;
import com.xyp.backendbookstore.entity.BillBookRelation;
import com.xyp.backendbookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * BillBookRelationDao:
 *
 * @Author XvYanpeng
 * @Date 2019/12/1 15:03
 */
@Repository
public interface BillBookRelationDao extends JpaRepository<BillBookRelation, Integer> {
    @Override
    ArrayList<BillBookRelation> findAll();

    /**
     * @Author XvYanpeng
     * @Description 获取当前用户订单详情
     * @Date 2019/12/3 18:48
     */
    @Query("select new com.xyp.backendbookstore.dto.BillInformationItem(book,billBookRelation,bill,user) " +
            "from " +
            "Book book ," +
            "BillBookRelation billBookRelation ," +
            "Bill bill ," +
            "User user " +
            "where " +
            "user.userId=bill.userId" +
            " and bill.billId=billBookRelation.billId" +
            " and billBookRelation.bookId=book.bookId" +
            " and user.userName=?1")
    ArrayList<BillInformationItem> getBillInformationItem(String userName);

    /**
     * @Author XvYanpeng
     * @Description 管理员获取全部订单详情
     * @Date 2019/12/3 18:48
     */
    @Query("select new com.xyp.backendbookstore.dto.BillInformationItem(book,billBookRelation,bill,user) " +
            "from " +
            "Book book ," +
            "BillBookRelation billBookRelation ," +
            "Bill bill ," +
            "User user " +
            "where " +
            "user.userId=bill.userId" +
            " and bill.billId=billBookRelation.billId" +
            " and billBookRelation.bookId=book.bookId")
    ArrayList<BillInformationItem> getAllBillInformationItem();


    /**
     * @Author XvYanpeng
     * @Description 退货修改标志位为1
     * @Date 2019/12/2 21:58
     */

    @Query(" UPDATE BillBookRelation billBookRelation" +
            " SET flag = 1" +
            " where billBookRelation.billBookId=?1")
    @Modifying
    void returnBookByRelationId(Integer id);

    /**
     * @Author XvYanpeng
     * @Description 根据订单id获取订单详情
     * @Date 2019/12/3 18:47
     */
    @Query("select new com.xyp.backendbookstore.dto.BillInformationItem(book,billBookRelation,bill,user) " +
            "from " +
            "Book book ," +
            "BillBookRelation billBookRelation ," +
            "Bill bill ," +
            "User user " +
            "where " +
            "user.userId=bill.userId" +
            " and bill.billId=billBookRelation.billId" +
            " and billBookRelation.bookId=book.bookId" +
            " and bill.billId=?1")
    ArrayList<BillInformationItem> getBillInformationItemByBillId(Integer billId);
}

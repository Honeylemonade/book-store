package com.xyp.backendbookstore.dao;

import com.xyp.backendbookstore.entity.Authority;
import com.xyp.backendbookstore.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * BillDao:
 *
 * @Author XvYanpeng
 * @Date 2019/12/1 16:14
 */
@Repository
public interface BillDao extends JpaRepository<Bill, Integer> {
    @Query("select sum (bill.billPrice) " +
            "from " +
            "Bill bill" +
            " where bill.billTime >= ?1 " +
            " and bill.billTime<= ?2")
    int getMonthlyIncome(int beginStamp, int endStamp);
}

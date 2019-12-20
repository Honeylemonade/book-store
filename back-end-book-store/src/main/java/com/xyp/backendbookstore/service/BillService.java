package com.xyp.backendbookstore.service;

import com.xyp.backendbookstore.dao.BillBookRelationDao;
import com.xyp.backendbookstore.dao.BillDao;
import com.xyp.backendbookstore.dao.BookDao;
import com.xyp.backendbookstore.dao.UserDao;
import com.xyp.backendbookstore.dto.BillInformation;
import com.xyp.backendbookstore.dto.BillInformationItem;
import com.xyp.backendbookstore.entity.Bill;
import com.xyp.backendbookstore.entity.BillBookRelation;
import com.xyp.backendbookstore.entity.Book;
import com.xyp.backendbookstore.entity.User;
import com.xyp.backendbookstore.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

/**
 * BillService:
 *
 * @Author XvYanpeng
 * @Date 2019/12/1 16:04
 */
@Service
public class BillService {
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    BillBookRelationDao billBookRelationDao;
    @Autowired
    UserDao userDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    BillDao billDao;

    /**
     * @Author XvYanpeng
     * @Description 获取当前用户交易信息
     * @Date 2019/12/2 22:05
     */
    public ArrayList<BillInformation> getBillDetails(HttpServletRequest httpServletRequest) throws Exception {
        //获取用户名
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String userName = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            userName = jwtUtil.getUserName(token);
        }
        if (userName == null) {
            throw new Exception("用户名获取失败");
        }
        ArrayList<BillInformationItem> billInformationItemArrayList = billBookRelationDao.getBillInformationItem(userName);
        //item拼接为对象形式
        ArrayList<BillInformation> billInformationArrayList = new ArrayList<>();
        for (BillInformationItem item : billInformationItemArrayList) {
            BillInformation targetObject = null;
            //寻找对应容器对象
            for (BillInformation object : billInformationArrayList) {
                if (object.getBillId().equals(item.getBillId())) {
                    targetObject = object;
                }
            }
            //如果找到了
            if (targetObject != null) {
                targetObject.getBillInformationItemArrayList().add(item);
            } else {
                //如果没找到,创建容器
                BillInformation newBillInformation = new BillInformation(item.getBillId(), item.getBillPrice(), item.getBillTime(), new ArrayList<>());
                newBillInformation.getBillInformationItemArrayList().add(item);
                billInformationArrayList.add(newBillInformation);
            }
        }
        return billInformationArrayList;
    }

    /**
     * @Author XvYanpeng
     * @Description 获取全部用户交易信息
     * @Date 2019/12/2 22:06
     */
    public ArrayList<BillInformation> getAllBillDetails() throws Exception {
        ArrayList<BillInformationItem> billInformationItemArrayList = billBookRelationDao.getAllBillInformationItem();
        //item拼接为对象形式
        ArrayList<BillInformation> billInformationArrayList = new ArrayList<>();
        for (BillInformationItem item : billInformationItemArrayList) {
            BillInformation targetObject = null;
            //寻找对应容器对象
            for (BillInformation object : billInformationArrayList) {
                if (object.getBillId().equals(item.getBillId())) {
                    targetObject = object;
                }
            }
            //如果找到了
            if (targetObject != null) {
                targetObject.getBillInformationItemArrayList().add(item);
            } else {
                //如果没找到,创建容器
                BillInformation newBillInformation = new BillInformation(item.getBillId(), item.getBillPrice(), item.getBillTime(), new ArrayList<>());
                newBillInformation.getBillInformationItemArrayList().add(item);
                billInformationArrayList.add(newBillInformation);
            }
        }
        return billInformationArrayList;
    }

    /**
     * @Author XvYanpeng
     * @Description 退货：将relation的flag置为1
     * @Date 2019/12/2 22:06
     */
    @Transactional
    @Rollback(value = false)
    public void returnBookByRelationId(Integer id) {
        billBookRelationDao.returnBookByRelationId(id);
    }

    /**
     * @Author XvYanpeng
     * @Description 处理订单请求，若成功返回该订单id，否则返回null
     * @Date 2019/12/3 18:04
     */
    @Transactional
    public Integer transaction(int[] bookIdArray, HttpServletRequest httpServletRequest) throws Exception {
        //获取用户名
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String userName = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            userName = jwtUtil.getUserName(token);
        }
        if (userName == null) {
            throw new Exception("用户名获取失败");
        }
        User user = userDao.findByUserName(userName);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : bookIdArray) {
            Integer temp = map.get(i);
            if (temp == null) {
                map.put(i, 1);
            } else {
                map.put(i, temp + 1);
            }
        }
        int totalPrice = 0;
        //检测书籍库存量是否足够,并统计订单总价格
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Book tempBook = bookDao.findByBookId(entry.getKey());
            if (tempBook.getAmount() < entry.getValue()) {
                throw new Exception("书籍库存量不足");
            }
            totalPrice += tempBook.getBookPrice() * entry.getValue();
        }
        //获取下单时间
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        //插入bill
        Bill resultBill = billDao.save(new Bill(null, user.getUserId(), totalPrice, timestamp));
        //插入bill_book_relation
        for (int i : bookIdArray) {
            billBookRelationDao.save(new BillBookRelation(null, resultBill.getBillId(), i, false));
        }
        //减少book库存
        for (int i : bookIdArray) {
            Book tempBook = bookDao.getOne(i);
            tempBook.setAmount(tempBook.getAmount() - 1);
        }
        return resultBill.getBillId();
    }

    public BillInformation getBillDetailsByBillId(Integer billId) {
        ArrayList<BillInformationItem> billInformationItemArrayList = billBookRelationDao.getBillInformationItemByBillId(billId);
        //item拼接为对象形式
        ArrayList<BillInformation> billInformationArrayList = new ArrayList<>();
        for (BillInformationItem item : billInformationItemArrayList) {
            BillInformation targetObject = null;
            //寻找对应容器对象
            for (BillInformation object : billInformationArrayList) {
                if (object.getBillId().equals(item.getBillId())) {
                    targetObject = object;
                }
            }
            //如果找到了
            if (targetObject != null) {
                targetObject.getBillInformationItemArrayList().add(item);
            } else {
                //如果没找到,创建容器
                BillInformation newBillInformation = new BillInformation(item.getBillId(), item.getBillPrice(), item.getBillTime(), new ArrayList<>());
                newBillInformation.getBillInformationItemArrayList().add(item);
                billInformationArrayList.add(newBillInformation);
            }
        }
        return billInformationArrayList.get(0);
    }

    public int getMonthlyIncome(Integer number) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, number + 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            int beginStamp = (int) (calendar.getTimeInMillis() / 1000);
            calendar.add(Calendar.MONTH, number + 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            int endStamp = (int) (calendar.getTimeInMillis() / 1000);
            int result = billDao.getMonthlyIncome(beginStamp, endStamp);
            return result;
        } catch (Exception e) {
            return 0;
        }
    }
}

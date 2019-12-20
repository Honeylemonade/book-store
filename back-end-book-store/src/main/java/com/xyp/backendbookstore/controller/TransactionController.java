package com.xyp.backendbookstore.controller;

import com.xyp.backendbookstore.dto.BillInformation;
import com.xyp.backendbookstore.service.BillService;
import com.xyp.backendbookstore.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * TransactionController:
 *
 * @Author XvYanpeng
 * @Date 2019/12/2 21:55
 */
@RestController
@CrossOrigin
public class TransactionController {
    @Autowired
    BillService billService;

    @PatchMapping("/transaction/{id}")
    public ResponseResult returnBookByRelationId(@PathVariable("id") Integer id) {
        try {
            billService.returnBookByRelationId(id);
            return new ResponseResult(0, "退货成功", id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseResult(1, "退货失败", null);
        }
    }

    @PostMapping("/transaction")
    public ResponseResult transaction(@RequestBody int[] bookIdArray, HttpServletRequest httpServletRequest) {
        try {
            Integer billId = billService.transaction(bookIdArray, httpServletRequest);
            if (billId == null) {
                return new ResponseResult(0, "交易失败", null);
            } else {
                //返回订单信息
                BillInformation billInformation = billService.getBillDetailsByBillId(billId);
                return new ResponseResult(1, "交易成功", billInformation);
            }
        } catch (Exception e) {
            return new ResponseResult(1, "交易失败", null);
        }
    }
}

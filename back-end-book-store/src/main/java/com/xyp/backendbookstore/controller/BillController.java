package com.xyp.backendbookstore.controller;

import com.xyp.backendbookstore.dto.BillInformation;
import com.xyp.backendbookstore.service.BillService;
import com.xyp.backendbookstore.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * BillController:
 *
 * @Author XvYanpeng
 * @Date 2019/12/1 15:56
 */
@RestController
@CrossOrigin
public class BillController {
    @Autowired
    BillService billService;

    /**
     * @Author XvYanpeng
     * @Description 获取当前用户历史订单信息【用户】
     * @Date 2019/12/9 10:28
     */
    @GetMapping("/billinformations")
    public ResponseResult billinformations(HttpServletRequest httpServletRequest) {
        try {
            ArrayList<BillInformation> billDetails = billService.getBillDetails(httpServletRequest);
            //进行排序
            Collections.sort(billDetails, new Comparator<BillInformation>() {
                @Override
                public int compare(BillInformation o1, BillInformation o2) {
                    return o1.getBillId() - o2.getBillId();
                }
            });
            return new ResponseResult(0, "该用户订单信息返回成功", billDetails);
        } catch (Exception e) {
            return new ResponseResult(1, "该用户订单信息返回失败", null);
        }
    }

    /**
     * @Author XvYanpeng
     * @Description 获取全部用户订单【管理员】
     * @Date 2019/12/9 10:28
     */
    @GetMapping("/allbillinformations")
    public ResponseResult allbillinformations() {
        try {
            ArrayList<BillInformation> billDetails = billService.getAllBillDetails();
            return new ResponseResult(0, "该用户订单信息返回成功", billDetails);
        } catch (Exception e) {
            return new ResponseResult(1, "该用户订单信息返回失败", null);
        }
    }

    @GetMapping("/monthlyincome/{number}")
    public ResponseResult getMonthlyIncome(@PathVariable("number") Integer number) {
        try {
            if (number > 0) {
                return new ResponseResult(1, "number不能大于0", null);
            } else {
                int income = billService.getMonthlyIncome(number);
                return new ResponseResult(0, "请求成功", income);
            }
        } catch (Exception e) {
            return new ResponseResult(1, "请求失败", null);
        }
    }
}

package com.xyp.backendbookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * BillInformation:
 *
 * @Author XvYanpeng
 * @Date 2019/12/2 20:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillInformation {
    private Integer billId;
    private Integer billPrice;
    private Integer billTime;
    ArrayList<BillInformationItem> billInformationItemArrayList=new ArrayList<>();
}

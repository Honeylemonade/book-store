package com.xyp.backendbookstore;


import com.xyp.backendbookstore.dao.BillBookRelationDao;
import com.xyp.backendbookstore.dao.BillDao;
import com.xyp.backendbookstore.dao.BookDao;
import com.xyp.backendbookstore.dao.UserDao;
import com.xyp.backendbookstore.dto.BillInformation;
import com.xyp.backendbookstore.dto.BillInformationItem;
import com.xyp.backendbookstore.dto.BookWithSoldAmount;
import com.xyp.backendbookstore.service.BillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;

@SpringBootTest
class BackEndBookStoreApplicationTests {
    @Autowired
    UserDao userDao;

    @Transactional
    @Test
    void contextLoads() throws Exception {
        System.out.println(userDao.getRolesByUserId(1));
    }

}

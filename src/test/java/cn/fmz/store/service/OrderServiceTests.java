package cn.fmz.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fmz.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private IOrderService service;

    @Test
    public void createOrder() {
        try {
            Integer aid = 30;
            Integer[] cids = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
            Integer uid = 14;
            String username = "管理员";
            service.createOrder(aid, cids, uid, username);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void closeIfUnpaid() {
        try {
            Integer oid = 7;
            service.closeIfUnpaid(oid);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

}









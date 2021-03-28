package cn.fmz.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fmz.store.service.ex.ServiceException;
import cn.fmz.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

    @Autowired
    private ICartService service;

    @Test
    public void addToCart() {
        try {
            Integer uid = 10;
            Integer pid = 10000042;
            Integer num = 5;
            String username = "管理员";
            service.addToCart(pid, num, uid, username);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getByUid() {
        Integer uid = 14;
        List<CartVO> list = service.getByUid(uid);
        System.err.println("count=" + list.size());
        for (CartVO item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void addNum() {
        try {
            Integer cid = 5;
            Integer uid = 15;
            String username = "外星人";
            service.addNum(cid, uid, username);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getByCids() {
        Integer[] cids = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Integer uid = 15;
        List<CartVO> list = service.getByCids(cids, uid);
        System.err.println("count=" + list.size());
        for (CartVO item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void delete() {
        try {
            Integer[] cids = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            Integer uid = 14;
            service.delete(cids, uid);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

}









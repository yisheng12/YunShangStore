package cn.fmz.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fmz.store.entity.Product;
import cn.fmz.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private IProductService service;

    @Test
    public void getHotList() {
        List<Product> list = service.getHotList();
        System.err.println("count=" + list.size());
        for (Product item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void getById() {
        Integer id = 10000042;
        Product data = service.getById(id);
        System.err.println(data);
    }

    @Test
    public void reduceNum() {
        try {
            Integer pid = 10000022;
            Integer amount = 10;
            service.reduceNum(pid, amount);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void addNum() {
        try {
            Integer pid = 10000017;
            Integer amount = -300;
            service.addNum(pid, amount);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

}










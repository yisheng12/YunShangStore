package cn.fmz.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fmz.store.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTests {

    @Autowired
    private ProductMapper mapper;

    @Test
    public void updateNum() {
        Integer pid = 10000022;
        Integer num = 100;
        Integer rows = mapper.updateNum(pid, num);
        System.err.println("rows=" + rows);
    }

    @Test
    public void findHotList() {
        List<Product> list = mapper.findHotList();
        System.err.println("count=" + list.size());
        for (Product item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void findById() {
        Integer id = 10000022;
        Product data = mapper.findById(id);
        System.err.println(data);
    }

}










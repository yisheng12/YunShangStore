package cn.tedu.store.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTests {

    @Autowired
    private OrderMapper mapper;

    @Test
    public void insertOrder() {
        Order order = new Order();
        order.setUid(1);
        order.setRecvName("小李");
        order.setRecvPhone("13000130000");
        Integer rows = mapper.insertOrder(order);
        System.err.println("rows=" + rows);
    }

    @Test
    public void insertOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(2);
        Integer rows = mapper.insertOrderItem(orderItem);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updateStatus() {
        Integer oid = 7;
        Integer status = 0;
        Integer rows = mapper.updateStatus(oid, status);
        System.err.println("rows=" + rows);
    }

    @Test
    public void findByOid() {
        Integer oid = 7;
        Order result = mapper.findByOid(oid);
        System.err.println(result);
    }

}










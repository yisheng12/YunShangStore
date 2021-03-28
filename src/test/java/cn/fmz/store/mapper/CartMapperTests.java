package cn.fmz.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fmz.store.entity.Cart;
import cn.fmz.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {

    @Autowired
    private CartMapper mapper;

    @Test
    public void addnew() {
        Cart cart = new Cart();
        cart.setUid(1);
        cart.setPid(2);
        cart.setNum(3);
        cart.setPrice(4L);
        Integer rows = mapper.addnew(cart);
        System.err.println("rows=" + rows);
    }

    @Test
    public void deleteByCids() {
        Integer[] cids = {5, 7, 9, 11};
        Integer rows = mapper.deleteByCids(cids);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updateNum() {
        Integer cid = 1;
        Integer num = 30;
        String modifiedUser = "管理员";
        Date modifiedTime = new Date();
        Integer rows = mapper.updateNum(cid, num, modifiedUser, modifiedTime);
        System.err.println("rows=" + rows);
    }

    @Test
    public void findByCid() {
        Integer cid = 1;
        Cart cart = mapper.findByCid(cid);
        System.err.println(cart);
    }

    @Test
    public void findByUidAndPid() {
        Integer uid = 1;
        Integer pid = 2;
        Cart cart = mapper.findByUidAndPid(uid, pid);
        System.err.println(cart);
    }

    @Test
    public void findByUid() {
        Integer uid = 14;
        List<CartVO> list = mapper.findByUid(uid);
        System.err.println("count=" + list.size());
        for (CartVO item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void findByCids() {
        Integer[] cids = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        List<CartVO> list = mapper.findByCids(cids);
        System.err.println("count=" + list.size());
        for (CartVO item : list) {
            System.err.println(item);
        }
    }

}










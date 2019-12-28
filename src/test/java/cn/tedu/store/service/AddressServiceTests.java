package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTests {

    @Autowired
    private IAddressService service;

    @Test
    public void addnew() {
        try {
            Integer uid = 1;
            String username = "超级管理员";
            Address address = new Address();
            address.setName("小张同学");
            service.addnew(uid, username, address);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getByUid() {
        Integer uid = 14;
        List<Address> list = service.getByUid(uid);
        System.err.println("count=" + list.size());
        for (Address item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void setDefault() {
        try {
            Integer aid = 18;
            Integer uid = 14;
            String username = "超级管理员";
            service.setDefault(aid, uid, username);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void delete() {
        try {
            Integer aid = 17;
            Integer uid = 14;
            String username = "超级管理员";
            service.delete(aid, uid, username);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getByAid() {
        try {
            Integer aid = 30;
            Address result = service.getByAid(aid);
            System.err.println(result);
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

}









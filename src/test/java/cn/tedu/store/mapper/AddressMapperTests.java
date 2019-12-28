package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTests {

    @Autowired
    private AddressMapper mapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(1);
        address.setName("小刘同学");
        Integer rows = mapper.insert(address);
        System.err.println("rows=" + rows);
    }

    @Test
    public void deleteByAid() {
        Integer aid = 18;
        Integer rows = mapper.deleteByAid(aid);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updateNonDefault() {
        Integer uid = 14;
        Integer rows = mapper.updateNonDefault(uid);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updateDefault() {
        Integer aid = 19;
        String modifiedUser = "ROOT";
        Date modifiedTime = new Date();
        Integer rows = mapper.updateDefault(aid, modifiedUser, modifiedTime);
        System.err.println("rows=" + rows);
    }

    @Test
    public void countByUid() {
        Integer uid = 1;
        Integer count = mapper.countByUid(uid);
        System.err.println("count=" + count);
    }

    @Test
    public void findByUid() {
        Integer uid = 14;
        List<Address> list = mapper.findByUid(uid);
        System.err.println("count=" + list.size());
        for (Address item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void findByAid() {
        Integer aid = 19;
        Address address = mapper.findByAid(aid);
        System.err.println(address);
    }

    @Test
    public void findLastModified() {
        Integer uid = 14;
        Address address = mapper.findLastModified(uid);
        System.err.println(address);
    }

}










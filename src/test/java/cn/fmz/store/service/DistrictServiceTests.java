package cn.fmz.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fmz.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTests {

    @Autowired
    private IDistrictService service;

    @Test
    public void findByCode() {
        String code = "130000";
        District data = service.getByCode(code);
        System.err.println(data);
    }

    @Test
    public void getByParent() {
        String parent = "86";
        List<District> list = service.getByParent(parent);
        System.err.println("count=" + list.size());
        for (District item : list) {
            System.err.println(item);
        }
    }

}









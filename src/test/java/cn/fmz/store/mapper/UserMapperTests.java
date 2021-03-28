package cn.fmz.store.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fmz.store.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

    @Autowired
    private UserMapper mapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("update");
        user.setPassword("1234");
        System.err.println(user); // uid=null
        Integer rows = mapper.insert(user);
        System.err.println("rows=" + rows);
        System.err.println(user); // uid=2
    }

    @Test
    public void updateInfo() {
        Integer uid = 14;
        String modifiedUser = "超级管理员";
        Date modifiedTime = new Date();

        User user = new User();
        user.setUid(uid);
        user.setPhone("13800138888");
        // user.setEmail("root@fmz.cn");
        // user.setGender(1);
        user.setModifiedUser(modifiedUser);
        user.setModifiedTime(modifiedTime);
        Integer rows = mapper.updateInfo(user);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updateAvatar() {
        Integer uid = 14;
        String avatar = "1234";
        String modifiedUser = "超级管理员";
        Date modifiedTime = new Date();
        Integer rows = mapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updatePassword() {
        Integer uid = 17;
        String password = "1234";
        String modifiedUser = "超级管理员";
        Date modifiedTime = new Date();
        Integer rows = mapper.updatePassword(uid, password, modifiedUser, modifiedTime);
        System.err.println("rows=" + rows);
    }

    @Test
    public void findByUsername() {
        String username = "spring1";
        User user = mapper.findByUsername(username);
        System.err.println(user);
    }

    @Test
    public void findByUid() {
        Integer uid = 16;
        User user = mapper.findByUid(uid);
        System.err.println(user);
    }

}








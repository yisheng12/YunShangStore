package cn.fmz.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fmz.store.entity.User;
import cn.fmz.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private IUserService service;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("digest");
            user.setPassword("1234");
            service.reg(user);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
        }
    }

    @Test
    public void login() {
        try {
            String username = "root";
            String password = "1234";
            User result = service.login(username, password);
            System.err.println(result);
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void changeInfo() {
        try {
            Integer uid = 16;
            String username = "root";
            User user = new User();
            user.setGender(0);
            user.setEmail("mybatis@163.com");
            user.setPhone("13100131000");
            service.changeInfo(uid, username, user);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void changeAvatar() {
        try {
            Integer uid = 14;
            String username = "root";
            String avatar = "这是头像的路径";
            service.changeAvatar(uid, username, avatar);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void changePassword() {
        try {
            Integer uid = 16;
            String username = "root";
            String oldPassword = "8888";
            String newPassword = "1234";
            service.changePassword(uid, username, oldPassword, newPassword);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getByUid() {
        try {
            Integer uid = 16;
            User user = service.getByUid(uid);
            System.err.println(user);
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

}









package cn.fmz.store.service.impl;

import cn.fmz.store.entity.User;
import cn.fmz.store.mapper.UserMapper;
import cn.fmz.store.service.IUserService;
import cn.fmz.store.service.ex.*;
import cn.fmz.store.util.PassUtils;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.UUID;

/**
 * 处理用户数据的业务层实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        // 根据参数user中的username查询用户数据
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        System.err.println("user is " + user);

        // 判断查询结果是否不为null
        if (!ObjectUtils.isEmpty(result)) {
            // 用户名已经被占用，不允许注册，抛出UsernameDuplicateException
            throw new UsernameDuplicateException("您尝试注册的用户名(" + username + ")已经被占用");
        }

        // 用户名没有被占用，允许注册
        // 生成盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 基于原密码和盐值进行加密处理
        String md5Password = PassUtils.getMd5Password(user.getPassword(), salt);
        // 补全数据：将盐值、新密码设置到salt、password属性
        user.setSalt(salt);
        user.setPassword(md5Password);
        // 补全数据：isDelete
        user.setIsDelete(0);
        // 补全数据：4项日志
        Date now = new Date();
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);
        // 执行注册，获取返回的受影响的行数
        Integer rows = userMapper.insert(user);
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 抛出InsertException
            throw new InsertException("存储用户数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public User login(String username, String password) {
        // 根据参数username查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否为null
        if (ObjectUtils.isEmpty(result)) {
            // 是：用户名不存在，抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (StringUtils.equals("1", String.valueOf(result.getIsDelete()))) {
            // 是：用户数据标记为已删除，抛出UserNotFoundException
            throw new UserNotFoundException("用户数据已删除");
        }

        // 从查询结果中取出盐值
        String salt = result.getSalt();
        // 将参数password结合盐值进行加密，得到md5Password
        String md5Password = PassUtils.getMd5Password(password, salt);
        // 判断查询结果中的密码与md5Password是否不匹配
        if (!StringUtils.equals(result.getPassword(), md5Password)) {
            // 是：密码错误，抛出PasswordNotMatchException
            throw new PasswordNotMatchException("密码错误");
        }

        // 创建返回结果的user对象
        User user = new User();
        // 将查询结果中的uid、username、avatar封装到返回结果中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        System.err.println("login.user is " + user);
        // 返回结果
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (ObjectUtils.isEmpty(result)) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("尝试访问的用户数据不存在[1]");
        }

        // 判断查询结果中的isDelete是否为1
        if (1 == result.getIsDelete()) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("尝试访问的用户数据不存在[2]");
        }

        // 将参数uid封装到参数user的uid属性中
        user.setUid(uid);
        // 将参数username封装到参数user的modifiedUser属性中
        user.setModifiedUser(username);
        // 创建Date对象封装到参数user的modifiedTime属性中
        user.setModifiedTime(new Date());

        // 执行更新用户数据，并获取返回值
        Integer rows = userMapper.updateInfo(user);
        // 判断返回的受影响的行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (ObjectUtils.isEmpty(result)) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("尝试访问的用户数据不存在[1]");
        }

        // 判断查询结果中的isDelete是否为1
        if (1 == result.getIsDelete()) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("尝试访问的用户数据不存在[2]");
        }

        // 执行更新头像
        Integer rows = userMapper.updateAvatar(uid, avatar, username, new Date());
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        // 打桩：输出类与方法名称，输出方法的4个参数，格式为：参数名=参数值
        System.err.println("UserServiceImpl.changePassword()");
        System.err.println("参数uid=" + uid);
        System.err.println("参数username=" + username);
        System.err.println("参数oldPassword=" + oldPassword);
        System.err.println("参数newPassword=" + newPassword);

        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (ObjectUtils.isEmpty(result)) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("尝试访问的用户数据不存在[1]");
        }

        // 判断查询结果中的isDelete是否为1
        if (1 == result.getIsDelete()) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("尝试访问的用户数据不存在[2]");
        }

        // 从查询结果中取出盐值
        String salt = result.getSalt();
        // 打桩：显示盐值
        System.err.println("数据库中的盐值=" + salt);
        // 根据参数oldPassword结合盐值加密得到oldMd5Password
        String oldMd5Password = PassUtils.getMd5Password(oldPassword, salt);
        // 打桩：oldMd5Password
        System.err.println("原密码加密后=" + oldMd5Password);
        // 打桩：查询结果中的密码
        System.err.println("数据库中的原密码=" + result.getPassword());
        // 判断查询结果中的密码与oldMd5Password是否不一致
        if (!StringUtils.equals(result.getPassword(), oldMd5Password)) {
            // 是：抛出PasswordNotMatchException
            throw new PasswordNotMatchException("原密码错误");
        }

        // 根据参数newPassword结合盐值加密得到newMd5Password
        String newMd5Password = PassUtils.getMd5Password(newPassword, salt);
        // 打桩：newMd5Password
        System.err.println("新密码加密后=" + newMd5Password);
        // 执行更新密码：updatePassword(uid,newMd5Password,username,new Date())，并获取返回的受影响的行数
        Integer rows = userMapper.updatePassword(uid, newMd5Password, username, new Date());
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (ObjectUtils.isEmpty(result)) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("尝试访问的用户数据不存在[1]");
        }

        // 判断查询结果中的isDelete是否为1
        if (1 == result.getIsDelete()) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("尝试访问的用户数据不存在[2]");
        }

        // 创建新的User对象作为返回值
        User user = new User();
        // 将查询结果中的uid / username / phone / gender / email封装到以上新创建的对象中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        // 返回新创建的对象
        return user;
    }

}










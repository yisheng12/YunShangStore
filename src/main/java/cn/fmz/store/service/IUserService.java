package cn.fmz.store.service;

import cn.fmz.store.entity.User;

/**
 * 处理用户数据的业务层接口
 */
public interface IUserService {

    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    void reg(User user);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 成功登录的用户的数据
     */
    User login(String username, String password);

    /**
     * 修改用户资源
     *
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     * @param user     封装了用户的新资料的对象
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 修改头像
     *
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     * @param avatar   新头像的路径
     */
    void changeAvatar(Integer uid, String username, String avatar);

    /**
     * 修改密码
     *
     * @param uid         当前登录的用户的id
     * @param username    当前登录的用户名
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid, String username, String oldPassword, String newPassword);

    /**
     * 根据用户id查询用户信息
     *
     * @param uid 当前登录的用户的id
     * @return 匹配的用户信息，如果没有匹配的数据，将抛出相关异常
     */
    User getByUid(Integer uid);

}







package cn.fmz.store.mapper;

import cn.fmz.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 处理用户数据的持久层接口
 */
public interface UserMapper {

    /**
     * 插入用户数据
     *
     * @param user 用户数据
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 更新个人资料
     *
     * @param user 封装了需要更新的数据及用户id的对象
     * @return 受影响的行数
     */
    Integer updateInfo(User user);

    /**
     * 更新头像
     *
     * @param uid          用户id
     * @param avatar       新头像的路径
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateAvatar(
            @Param("uid") Integer uid,
            @Param("avatar") String avatar,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 更新密码
     *
     * @param uid          用户id
     * @param password     新密码
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updatePassword(
            @Param("uid") Integer uid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户名查询用户数据详情
     *
     * @param username 用户名
     * @return 匹配的用户数据详情，如果没有匹配的数据，则返回null
     */
    User findByUsername(String username);

    /**
     * 根据用户id查询用户数据详情
     *
     * @param uid 用户id
     * @return 匹配的用户数据详情，如果没有匹配的数据，则返回null
     */
    User findByUid(Integer uid);

    /**
     * 根据用户名查询用户是否存在
     *
     * @param username 用户名
     * @return 匹配的用户数据
     */
    Integer checkIfExistByUsername(String username);
}











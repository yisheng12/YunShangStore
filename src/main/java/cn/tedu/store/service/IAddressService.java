package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;

/**
 * 处理收货地址数据的业务层接口
 */
public interface IAddressService {

    /**
     * 增加新的收货地址
     *
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     * @param address  收货地址数据
     */
    void addnew(Integer uid, String username, Address address);

    /**
     * 查询某用户的收货地址列表
     *
     * @param uid 用户的id
     * @return 该用户的收货地址列表
     */
    List<Address> getByUid(Integer uid);

    /**
     * 将指定用户的某条收货地址设置为默认
     *
     * @param aid      收货地址id
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除收货地址
     *
     * @param aid      收货地址数据id
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     */
    void delete(Integer aid, Integer uid, String username);

    /**
     * 根据收货地址数据的id查询详情
     *
     * @param aid 收货地址数据的id
     * @return 匹配的收货地址数据的详情
     */
    Address getByAid(Integer aid);

}






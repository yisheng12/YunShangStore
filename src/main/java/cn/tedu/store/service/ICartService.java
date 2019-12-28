package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层接口
 */
public interface ICartService {

    /**
     * 将商品添加到购物车
     *
     * @param pid      商品id
     * @param num      商品数量
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     */
    void addToCart(Integer pid, Integer num, Integer uid, String username);

    /**
     * 查询某用户的购物车数据的列表
     *
     * @param uid 用户的id
     * @return 该用户的购物车数据的列表
     */
    List<CartVO> getByUid(Integer uid);

    /**
     * 将购物车中的商品的数量增加1
     *
     * @param cid      购物车数据的id
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 根据若干个购物车数据id查询详情列表
     *
     * @param cids 若干个购物车数据id
     * @param uid  当前登录的用户的id
     * @return 匹配的购物车数据的列表
     */
    List<CartVO> getByCids(Integer[] cids, Integer uid);

    /**
     * 根据购物车数据id批量删除数据
     *
     * @param cids 购物车数据id
     * @param uid  当前登录的用户的id
     */
    void delete(Integer[] cids, Integer uid);
}





package cn.tedu.store.service;

import cn.tedu.store.entity.Order;

/**
 * 处理订单数据和订单商品数据的业务层接口
 */
public interface IOrderService {

    /**
     * 创建订单
     *
     * @param aid      收货地址数据的id
     * @param cids     购物车的数据的id
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 成功创建的订单对象
     */
    Order createOrder(Integer aid, Integer[] cids, Integer uid, String username);

    /**
     * 关闭未支付的订单，仅处于未支付状态的订单会被处理
     *
     * @param oid 订单的id
     */
    void closeIfUnpaid(Integer oid);

}

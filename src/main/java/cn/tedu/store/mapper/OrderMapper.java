package cn.tedu.store.mapper;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;

/**
 * 处理订单数据和订单商品数据的持久层接口
 */
public interface OrderMapper {

    /**
     * 插入订单数据
     *
     * @param order 订单数据
     * @return 受影响的行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单商品数据
     *
     * @param orderItem 订单商品数据
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);

    /**
     * 更新订单状态
     *
     * @param oid    订单id
     * @param status 新的状态
     * @return 受影响的行数
     */
    Integer updateStatus(
            @Param("oid") Integer oid,
            @Param("status") Integer status);

    /**
     * 根据订单id查询订单详情
     *
     * @param oid 订单id
     * @return 匹配的订单详情，如果没有匹配的数据，则返回null
     */
    Order findByOid(Integer oid);

}

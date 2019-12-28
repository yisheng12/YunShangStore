package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.OrderNotFoundException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

/**
 * 处理订单数据和订单商品数据的业务层实现类
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IProductService productService;

    @Override
    @Transactional
    public Order createOrder(Integer aid, Integer[] cids, Integer uid, String username) {
        // 创建当前时间对象now
        Date now = new Date();

        // 调用cartService的getByCids(cids, uid)查询得到List<CartVO>，名为carts
        List<CartVO> carts = cartService.getByCids(cids, uid);
        // 声明变量
        Long totalPrice = 0L;
        // 遍历carts
        for (CartVO cart : carts) {
            // 使用totalPrice累加realPrice乘num
            totalPrice += cart.getRealPrice() * cart.getNum();
        }

        // 创建Order对象
        Order order = new Order();
        // 补全数据：uid
        order.setUid(uid);
        // 调用addressService.getByAid(aid)查询收货地址详情
        Address address = addressService.getByAid(aid);
        // 补全数据：recv_name, recv_phone, recv_province, recv_city, recv_area, recv_address
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        // 补全数据：total_price -> totalPrice
        order.setTotalPrice(totalPrice);
        // 补全数据：status -> 0
        order.setStatus(0);
        // 补全数据：order_time -> now
        // 补全数据：pay_time -> null
        order.setOrderTime(now);
        // 补全数据：日志 -> username, now
        order.setCreatedUser(username);
        order.setCreatedTime(now);
        order.setModifiedUser(username);
        order.setModifiedTime(now);
        // 插入订单数据：insertOrder(Order order)
        insertOrder(order);

        // 循环插入订单商品数据
        // 遍历carts
        for (CartVO cart : carts) {
            // 创建OrderItem对象
            OrderItem orderItem = new OrderItem();
            // 补全数据：oid -> order.getOid();
            orderItem.setOid(order.getOid());
            // 补全数据：pid, title, image, price(realPrice), num
            orderItem.setPid(cart.getPid());
            orderItem.setTitle(cart.getTitle());
            orderItem.setImage(cart.getImage());
            orderItem.setPrice(cart.getRealPrice());
            orderItem.setNum(cart.getNum());
            // 补全数据：日志 -> username, now
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(now);
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(now);
            // 插入订商品数据insertOrderItem(OrderItem orderItem)
            insertOrderItem(orderItem);
            // 其它细节：减少对应商品的库存 > update t_product set num=? where pid=?
            productService.reduceNum(cart.getPid(), cart.getNum());
        }

        // 其它细节：删除购物车中对应的数据 > delete from t_cart where cid in (?,?,?)
        cartService.delete(cids, uid);
        // 其它细节：倒计时15分钟，如果未支付，关闭订单并归还库存 > update t_order set status=3 where oid=?; update t_product set num=? where pid=?
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(15 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                closeIfUnpaid(order.getOid());
                for (CartVO cart : carts) {
                    productService.addNum(cart.getPid(), cart.getNum());
                }
            }
        }.start();

        // 返回order
        return order;
    }

    @Override
    public void closeIfUnpaid(Integer oid) {
        // 根据oid查询订单数据
        Order result = findByOid(oid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：OrderNotFoundException(需创建)
            throw new OrderNotFoundException("尝试访问的订单数据不存在");
        }

        // 判断查询结果中的status是否为0
        if (result.getStatus().equals(0)) {
            // 是：调用私有方法将status更新为3
            updateStatus(oid, 3);
        }
    }

    /**
     * 插入订单数据
     *
     * @param order 订单数据
     */
    private void insertOrder(Order order) {
        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException(
                    "插入订单数据时出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 插入订单商品数据
     *
     * @param orderItem 订单商品数据
     */
    private void insertOrderItem(OrderItem orderItem) {
        Integer rows = orderMapper.insertOrderItem(orderItem);
        if (rows != 1) {
            throw new InsertException(
                    "插入订单商品数据时出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 更新订单状态
     *
     * @param oid    订单id
     * @param status 新的状态
     */
    private void updateStatus(Integer oid, Integer status) {
        Integer rows = orderMapper.updateStatus(oid, status);
        if (rows != 1) {
            throw new UpdateException("更新订单状态时出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 根据订单id查询订单详情
     *
     * @param oid 订单id
     * @return 匹配的订单详情，如果没有匹配的数据，则返回null
     */
    private Order findByOid(Integer oid) {
        return orderMapper.findByOid(oid);
    }


}

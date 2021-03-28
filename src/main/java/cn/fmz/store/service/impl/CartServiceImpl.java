package cn.fmz.store.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fmz.store.entity.Cart;
import cn.fmz.store.entity.Product;
import cn.fmz.store.mapper.CartMapper;
import cn.fmz.store.service.ICartService;
import cn.fmz.store.service.IProductService;
import cn.fmz.store.service.ex.AccessDeniedException;
import cn.fmz.store.service.ex.CartNotFoundException;
import cn.fmz.store.service.ex.DeleteException;
import cn.fmz.store.service.ex.InsertException;
import cn.fmz.store.service.ex.ProductCountLimitException;
import cn.fmz.store.service.ex.UpdateException;
import cn.fmz.store.vo.CartVO;

/**
 * 处理购物车数据的业务层实现类
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private IProductService productService;

    @Override
    public void addToCart(Integer pid, Integer num, Integer uid, String username) {
        // 通过参数uid和pid进行查询
        Cart result = findByUidAndPid(uid, pid);
        // 创建当前时间对象
        Date now = new Date();
        // 判断查询结果是否为null
        if (result == null) {
            // 是：执行插入数据
            // -- 创建Cart对象
            Cart cart = new Cart();
            // -- 调用productService.getById(pid)查询价格，封装到Cart对象中
            Product product = productService.getById(pid);
            cart.setPrice(product.getPrice());
            // -- 将uid、pid、num封装到Cart对象中
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(num);
            // -- 封装日志数据
            cart.setCreatedUser(username);
            cart.setCreatedTime(now);
            cart.setModifiedUser(username);
            cart.setModifiedTime(now);
            // -- 执行插入数据
            addnew(cart);
        } else {
            // 否：执行修改数量
            // -- 从查询结果中取出cid
            Integer cid = result.getCid();
            // -- 从查询结果中取出num，与参数num相加，作为后续更新时的值
            Integer newNum = result.getNum() + num;
            // -- 执行修改数量
            updateNum(cid, newNum, username, now);
        }
    }

    @Override
    public List<CartVO> getByUid(Integer uid) {
        return findByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        // 根据参数cid查询购物车数据
        Cart result = findByCid(cid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：CartNotFoundException
            throw new CartNotFoundException(
                    "尝试访问的购物车数据不存在，可能已经被删除");
        }

        // 判断查询结果中的uid与参数uid是否不同
        if (!result.getUid().equals(uid)) {
            // 是：AccessDeniedException
            throw new AccessDeniedException("非法访问");
        }

        // 从查询结果中获取原数量，并加1，得到新数量
        Integer newNum = result.getNum() + 1;
        // 判断新数量是否超出上限(假设是99)
        if (newNum > 99) {
            // 是：ProductCountLimitException
            throw new ProductCountLimitException(
                    "期望购买的数量达到上限");
        }

        // 执行更新
        updateNum(cid, newNum, username, new Date());

        // 返回新数量
        return newNum;
    }

    @Override
    public List<CartVO> getByCids(Integer[] cids, Integer uid) {
        // 调用私有方法查询数据结果
        List<CartVO> list = findByCids(cids);

        // 判断查询结果中每条数据的归属是否正确，如果归属错误，应该从查询结果中移除
        Iterator<CartVO> it = list.iterator();
        while (it.hasNext()) {
            CartVO cart = it.next();
            if (!cart.getUid().equals(uid)) {
                it.remove();
            }
        }

        // 返回查询结果
        return list;
    }

    @Override
    public void delete(Integer[] cids, Integer uid) {
        // 根据参数cids, uid调用公有的getByCids()方法，得到List<CartVO>
        List<CartVO> result = getByCids(cids, uid);
        // 创建新的数组，长度就是以上查询到的List的长度
        Integer[] newCids = new Integer[result.size()];
        // 遍历List，为数组中的各元素赋值，得到正确的cid的数组
        for (int i = 0; i < newCids.length; i++) {
            newCids[i] = result.get(i).getCid();
        }
        // 调用私有的deleteByCids()执行删除
        deleteByCids(newCids);
    }

    /**
     * 插入购物车数据
     *
     * @param cart 购物车数据
     */
    private void addnew(Cart cart) {
        Integer rows = cartMapper.addnew(cart);
        if (rows != 1) {
            throw new InsertException(
                    "插入购物车数据时出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 根据数据id批量删除数据
     *
     * @param cids 数据id
     */
    private void deleteByCids(Integer[] cids) {
        Integer rows = cartMapper.deleteByCids(cids);
        if (!rows.equals(cids.length)) {
            throw new DeleteException(
                    "删除数据时出现未知错误！请联系系统管理员");
        }
    }

    /**
     * 修改购物车中商品的数量
     *
     * @param cid          购物车数据id
     * @param num          新的数量
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     */
    private void updateNum(Integer cid, Integer num,
                           String modifiedUser, Date modifiedTime) {
        Integer rows = cartMapper.updateNum(cid, num, modifiedUser, modifiedTime);
        if (rows != 1) {
            throw new UpdateException(
                    "更新商品数量时出现未知错误！请联系系统管理员");
        }
    }

    /**
     * 根据购物车数据id查询数据详情
     *
     * @param cid 购物车数据id
     * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
     */
    private Cart findByCid(Integer cid) {
        return cartMapper.findByCid(cid);
    }

    /**
     * 根据用户id和商品id查询购物车中的数据
     *
     * @param uid 用户id
     * @param pid 商品id
     * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
     */
    private Cart findByUidAndPid(Integer uid, Integer pid) {
        return cartMapper.findByUidAndPid(uid, pid);
    }

    /**
     * 查询某用户的购物车数据的列表
     *
     * @param uid 用户的id
     * @return 该用户的购物车数据的列表
     */
    private List<CartVO> findByUid(Integer uid) {
        return cartMapper.findByUid(uid);
    }

    /**
     * 根据若干个购物车数据id查询详情列表
     *
     * @param cids 若干个购物车数据id
     * @return 匹配的购物车数据的列表
     */
    private List<CartVO> findByCids(Integer[] cids) {
        return cartMapper.findByCids(cids);
    }


}

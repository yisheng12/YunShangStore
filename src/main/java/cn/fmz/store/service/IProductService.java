package cn.fmz.store.service;

import java.util.List;

import cn.fmz.store.entity.Product;

/**
 * 处理商品数据的业务层接口
 */
public interface IProductService {

    /**
     * 获取热销排行的前4个商品的列表
     *
     * @return 热销排行的前4个商品的列表
     */
    List<Product> getHotList();

    /**
     * 根据商品id查询商品详情
     *
     * @param id 商品id
     * @return 匹配的商品的详情，如果没有匹配的数据，则返回null
     */
    Product getById(Integer id);

    /**
     * 减少商品的库存
     *
     * @param pid    商品的id
     * @param amount 减少的数量
     */
    void reduceNum(Integer pid, Integer amount);

    /**
     * 增加商品的库存
     *
     * @param pid    商品的id
     * @param amount 增加的数量
     */
    void addNum(Integer pid, Integer amount);

}






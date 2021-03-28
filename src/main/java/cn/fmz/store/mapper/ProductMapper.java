package cn.fmz.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.fmz.store.entity.Product;

/**
 * 处理商品数据的持久层接口
 */
public interface ProductMapper {

    /**
     * 修改商品的库存
     *
     * @param pid 商品的id
     * @param num 新的库存值
     * @return 受影响的行数
     */
    Integer updateNum(@Param("pid") Integer pid,
                      @Param("num") Integer num);

    /**
     * 获取热销排行的前4个商品的列表
     *
     * @return 热销排行的前4个商品的列表
     */
    List<Product> findHotList();

    /**
     * 根据商品id查询商品详情
     *
     * @param id 商品id
     * @return 匹配的商品的详情，如果没有匹配的数据，则返回null
     */
    Product findById(Integer id);

}










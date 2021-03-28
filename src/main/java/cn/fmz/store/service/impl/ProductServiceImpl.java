package cn.fmz.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fmz.store.entity.Product;
import cn.fmz.store.mapper.ProductMapper;
import cn.fmz.store.service.IProductService;
import cn.fmz.store.service.ex.OutOfStockException;
import cn.fmz.store.service.ex.ProductNotFoundException;
import cn.fmz.store.service.ex.UpdateException;

/**
 * 处理商品数据的业务层实现类
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getHotList() {
        List<Product> list = findHotList();
        for (Product product : list) {
            product.setCategoryId(null);
            product.setItemType(null);
            product.setSellPoint(null);
            product.setNum(null);
            product.setStatus(null);
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public Product getById(Integer id) {
        Product product = findById(id);
        if (product == null) {
            throw new ProductNotFoundException(
                    "尝试访问的商品数据不存在");
        }

        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);

        return product;
    }

    @Override
    public void reduceNum(Integer pid, Integer amount) {
        // 根据参数pid查询数据
        Product result = findById(pid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：ProductNotFoundException
            throw new ProductNotFoundException(
                    "尝试访问的商品数据不存在");
        }

        // if (!result.getStatus().equals(1))

        // 判断查询结果中的库存是否小于参数amount
        if (result.getNum() < amount) {
            // 是：OutOfStockException(需创建)
            throw new OutOfStockException(
                    "商品库存不足");
        }

        // 将查询结果中的库存减去参数amount得到新的库存值
        Integer num = result.getNum() - amount;
        // 更新库存
        updateNum(pid, num);
    }

    @Override
    public void addNum(Integer pid, Integer amount) {
        // 根据参数pid查询数据
        Product result = findById(pid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：ProductNotFoundException
            throw new ProductNotFoundException(
                    "尝试访问的商品数据不存在");
        }

        // if (!result.getStatus().equals(1))

        // 将查询结果中的库存加上参数amount得到新的库存值
        Integer num = result.getNum() + amount;

        // 更新库存
        updateNum(pid, num);
    }

    /**
     * 获取热销排行的前4个商品的列表
     *
     * @return 热销排行的前4个商品的列表
     */
    private List<Product> findHotList() {
        return productMapper.findHotList();
    }

    /**
     * 根据商品id查询商品详情
     *
     * @param id 商品id
     * @return 匹配的商品的详情，如果没有匹配的数据，则返回null
     */
    private Product findById(Integer id) {
        return productMapper.findById(id);
    }

    /**
     * 修改商品的库存
     *
     * @param pid 商品的id
     * @param num 新的库存值
     * @return 受影响的行数
     */
    private void updateNum(Integer pid, Integer num) {
        Integer rows = productMapper.updateNum(pid, num);
        if (rows != 1) {
            throw new UpdateException("更新商品库存时出现未知错误，请联系系统管理员");
        }
    }


}

package cn.fmz.store.entity;

import lombok.Data;

/**
 * 订单商品数据的实体类
 */
@Data
public class OrderItem extends BaseEntity {

    private static final long serialVersionUID = -8879247924788259070L;

    private Integer id;
    private Integer oid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private String image;

}

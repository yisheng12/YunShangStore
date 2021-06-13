package cn.fmz.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 购物车数据的实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Cart extends BaseEntity {

    private static final long serialVersionUID = -9051846958681813039L;

    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Integer num;
    private Long price;
}

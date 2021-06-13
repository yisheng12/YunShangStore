package cn.fmz.store.entity;

import lombok.Data;

/**
 * 商品数据的实体类
 */
@Data
public class Product extends BaseEntity {

    private static final long serialVersionUID = -199568590252555336L;

    private Integer id;
    private Integer categoryId;
    private String itemType;
    private String title;
    private String sellPoint;
    private Long price;
    private Integer num;
    private String image;
    private Integer status;
    private Integer priority;

}

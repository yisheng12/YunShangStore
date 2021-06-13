package cn.fmz.store.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 购物车数据的VO类
 */
@Data
public class CartVO implements Serializable {

    private static final long serialVersionUID = 461318594743947337L;

    private Integer cid;
    private Integer pid;
    private Integer uid;
    private Long price;
    private Integer num;
    private Long realPrice;
    private String title;
    private String image;

}
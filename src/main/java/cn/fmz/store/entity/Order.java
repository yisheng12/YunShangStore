package cn.fmz.store.entity;

import lombok.Data;

import java.util.Date;

/**
 * 订单数据的实体类
 */
@Data
public class Order extends BaseEntity {

    private static final long serialVersionUID = -3216224344757796927L;

    private Integer oid;
    private Integer uid;
    private String recvName;
    private String recvPhone;
    private String recvProvince;
    private String recvCity;
    private String recvArea;
    private String recvAddress;
    private Long totalPrice;
    private Integer status;
    private Date orderTime;
    private Date payTime;

}

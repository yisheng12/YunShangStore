package cn.fmz.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收货地址数据的实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Address extends BaseEntity {

    private static final long serialVersionUID = 6946915401608396201L;

    private Integer aid;
    private Integer uid;
    private String name;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String areaCode;
    private String areaName;
    private String zip;
    private String address;
    private String phone;
    private String tel;
    private String tag;
    private Integer isDefault;
}

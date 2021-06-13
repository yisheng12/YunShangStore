package cn.fmz.store.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 省/市/区数据的实体类
 */
@Data
public class District implements Serializable {

    private static final long serialVersionUID = 3674364250490127944L;

    private Integer id;
    private String parent;
    private String code;
    private String name;

}

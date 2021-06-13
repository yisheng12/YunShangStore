package cn.fmz.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类的基类
 */
@Data
abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -3122958702938259476L;

    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;
}

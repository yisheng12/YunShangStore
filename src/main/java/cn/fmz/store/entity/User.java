package cn.fmz.store.entity;

import lombok.Data;

/**
 * 用户数据的实体类
 */
@Data
public class User extends BaseEntity {

    private static final long serialVersionUID = -1197587481888673428L;

    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private Integer gender;
    private String phone;
    private String email;
    private String avatar;
    private Integer isDelete;

}

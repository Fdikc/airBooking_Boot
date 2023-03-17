package edu.bzu.fdick.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */

    @TableField(select = false,fill = FieldFill.INSERT)
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * Email
     */
    private String email;

    /**
     * 城市
     */
    private String city;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date created;

    /**
     * 更新时间
     */

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updated;

    /**
     * 状态
     */
    private Object statu;

    @TableField(exist = false)
    private SysRole sysRole;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
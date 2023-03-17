package edu.bzu.fdick.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName order_user
 */
@TableName(value ="order_user")
@Data
public class Order implements Serializable {
    /**
     * 订单id
     */
    @TableId(type=IdType.INPUT)
    private String id;

    /**
     * 订单名
     */
    private String orderName;

    /**
     * 用户
     */
    private Integer userId;

    /**
     * 航班id
     */
    private Integer flightId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 0头等舱 1经济舱
     */
    private Integer grade;

    /**
     * 价格
     */
    private Double price;

//支付状态
    private Integer statu;



    /**
     * 创建时间
     */

    @TableField(fill = FieldFill.INSERT)
    private Date created;

    @TableField(exist = false)
    private Flight flight;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
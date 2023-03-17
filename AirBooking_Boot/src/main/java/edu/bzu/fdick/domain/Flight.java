package edu.bzu.fdick.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName flight
 */
@TableName(value ="flight")
@Data
public class Flight implements Serializable {
    /**
     * 航班ID
     */
    @TableId(type = IdType.AUTO)
    private Integer flightId;

    /**
     * 航班号
     */
    private String flightNumber;

    /**
     * 航班起点
     */
    private String flightStartPlace;

    /**
     * 航班终点
     */
    private String flightEndPlace;

    /**
     * 航班始发机场
     */
    private String flightStartAirport;

    /**
     * 航班到达机场
     */
    private String flightEndAirport;

    /**
     * 航班起飞时间
     */
    //
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date flightStartTime;

    /**
     * 航班到达时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date flightEndTime;

    /**
     * 头等舱价格
     */
    private Double flightHighPrice;

    /**
     * 头等舱座位数
     */
    private Integer flightHighNumber;


    /**
     * 经济舱价格
     */
    private Double flightBasePrice;

    /**
     * 经济舱座位数
     */
    private Integer flightBaseNumber;

    /**
     * 版本
     */
    @Version
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
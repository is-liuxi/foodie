package com.liuxi.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/22 21:16
 */
@Data
public class CenterOrderVo {
    private String orderId;
    private Integer payMethod;
    private Integer realPayAmount;
    private Integer postAmount;
    private Integer orderStatus;
    private Integer isComment;
    private Date createdTime;
    List<CenterSubOrderVo> subOrderItemList;
}

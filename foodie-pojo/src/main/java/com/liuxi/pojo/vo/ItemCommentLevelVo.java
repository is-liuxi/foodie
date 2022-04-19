package com.liuxi.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/19 0:29
 */
@Data
public class ItemCommentLevelVo{
    /**
     * 评论类别总数
     */
    private Integer commentLevelTotal;
    /**
     * 评论类别
     */
    private String commentLevel;
}

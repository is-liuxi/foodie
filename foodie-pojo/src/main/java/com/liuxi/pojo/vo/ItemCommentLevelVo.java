package com.liuxi.pojo.vo;

import lombok.Data;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/19 0:29
 */
@Data
public class ItemCommentLevelVo {
    /**
     * 评论类别总数
     */
    private int totalCounts;
    /**
     * 评论类别个数
     */
    private int goodCounts;
    private int normalCounts;
    private int badCounts;
}

package com.liuxi.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/19 0:29
 */
@Data
public class ItemCommentVo {
    private String userFace;
    private String nickname;
    private Integer commentLevel;
    private Date createdTime;
    private String content;
    private String specName;
}

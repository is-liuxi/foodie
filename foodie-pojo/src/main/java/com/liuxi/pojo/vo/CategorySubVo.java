package com.liuxi.pojo.vo;

import lombok.Data;

/**
 * <p>
 * 三级分类 VO
 * </P>
 * @author liu xi
 * @date 2022/4/18 19:54
 */
@Data
public class CategorySubVo {
    private Integer subId;
    private String subName;
    private String subType;
    private Integer subFatherId;
}

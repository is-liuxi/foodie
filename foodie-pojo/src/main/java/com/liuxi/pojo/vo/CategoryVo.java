package com.liuxi.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 二级分类 VO
 * </P>
 * @author liu xi
 * @date 2022/4/18 19:52
 */
@Data
public class CategoryVo {
    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;
    private List<CategorySubVo> subCatList;
}

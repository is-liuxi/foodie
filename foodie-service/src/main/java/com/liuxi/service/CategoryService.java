package com.liuxi.service;

import com.liuxi.pojo.Category;
import com.liuxi.pojo.vo.CategoryVo;
import com.liuxi.pojo.vo.NewItemsVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/18 19:17
 */
public interface CategoryService {

    /**
     * 查询父级分类
     * @param fatherId
     * @return
     */
    List<Category> queryCategoryRootList(Integer fatherId);

    /**
     * 查询子分类
     * @param rootId
     * @return
     */
    List<CategoryVo> queryCategoryChildrenList(Integer rootId);

    /**
     * 查询最新的六个商品
     * @param params
     * @return
     */
    List<NewItemsVo> querySixNewItemsLazy(Map<String, Object> params);
}

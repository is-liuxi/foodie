package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.Category;
import com.liuxi.pojo.vo.CategoryVo;
import com.liuxi.pojo.vo.ItemNewVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/18 18:01
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询父级分类
     * @param type
     * @return
     */
    List<Category> queryCategoryRootList(Integer type);

    /**
     * 查询子分类
     * @param rootId
     * @return
     */
    List<CategoryVo> queryCategoryChildrenList(Integer rootId);

    /**
     * 查询最新的六个商品
     * @param map
     * @return
     */
    List<ItemNewVo> querySixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}

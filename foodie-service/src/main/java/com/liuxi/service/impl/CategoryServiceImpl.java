package com.liuxi.service.impl;

import com.liuxi.mapper.CategoryMapper;
import com.liuxi.pojo.Category;
import com.liuxi.pojo.vo.CategoryVo;
import com.liuxi.pojo.vo.ItemNewVo;
import com.liuxi.service.CategoryService;
import com.liuxi.util.common.JsonUtils;
import com.liuxi.util.common.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.liuxi.util.common.ConstantUtils.CATEGORY_REDIS_KEY;
import static com.liuxi.util.common.ConstantUtils.SUB_CATEGORY_REDIS_KEY;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 19:17
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<Category> queryCategoryRootList(Integer type) {
        // 从缓存中返回
        String categoryCache = RedisUtils.get(CATEGORY_REDIS_KEY);
        if (StringUtils.isNotBlank(categoryCache)) {
            return JsonUtils.jsonToList(categoryCache, Category.class);
        }

        List<Category> categories = categoryMapper.queryCategoryRootList(type);
        RedisUtils.set(CATEGORY_REDIS_KEY, JsonUtils.writeValueAsString(categories));
        return categories;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<CategoryVo> queryCategoryChildrenList(Integer rootId) {
        Boolean exist = RedisUtils.hasKey(SUB_CATEGORY_REDIS_KEY + rootId);
        if (exist) {
            String cacheValue = RedisUtils.get(SUB_CATEGORY_REDIS_KEY + rootId);
            return JsonUtils.jsonToList(cacheValue, CategoryVo.class);
        }

        List<CategoryVo> categoryVoList = categoryMapper.queryCategoryChildrenList(rootId);
        RedisUtils.set(SUB_CATEGORY_REDIS_KEY + rootId, JsonUtils.writeValueAsString(categoryVoList));
        return categoryVoList;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<ItemNewVo> querySixNewItemsLazy(Map<String, Object> params) {
        return categoryMapper.querySixNewItemsLazy(params);
    }
}

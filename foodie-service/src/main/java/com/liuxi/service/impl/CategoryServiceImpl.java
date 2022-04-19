package com.liuxi.service.impl;

import com.liuxi.mapper.CategoryMapper;
import com.liuxi.pojo.Category;
import com.liuxi.pojo.vo.CategoryVo;
import com.liuxi.pojo.vo.NewItemsVo;
import com.liuxi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
        return categoryMapper.queryCategoryRootList(type);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<CategoryVo> queryCategoryChildrenList(Integer rootId) {
        return categoryMapper.queryCategoryChildrenList(rootId);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<NewItemsVo> querySixNewItemsLazy(Map<String, Object> params) {
        return categoryMapper.querySixNewItemsLazy(params);
    }
}

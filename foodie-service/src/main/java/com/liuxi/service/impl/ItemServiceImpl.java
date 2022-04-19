package com.liuxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxi.mapper.*;
import com.liuxi.pojo.Items;
import com.liuxi.pojo.ItemsImg;
import com.liuxi.pojo.ItemsParam;
import com.liuxi.pojo.ItemsSpec;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.ItemCommentLevelVo;
import com.liuxi.pojo.vo.ItemCommentsVo;
import com.liuxi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 23:52
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemImgMapper itemImgMapper;
    @Autowired
    private ItemSpecMapper itemSpecMapper;
    @Autowired
    private ItemParamMapper itemParamMapper;
    @Autowired
    private ItemCommentMapper itemCommentMapper;

    @Override
    public Items queryItemByItemId(String itemId) {
        return itemMapper.selectById(itemId);
    }

    @Override
    public List<ItemsImg> queryItemImgByItemId(String itemId) {
        LambdaQueryWrapper<ItemsImg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ItemsImg::getItemId, itemId);
        return itemImgMapper.selectList(queryWrapper);
    }

    @Override
    public List<ItemsSpec> queryItemSpecByItemId(String itemId) {
        LambdaQueryWrapper<ItemsSpec> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ItemsSpec::getItemId, itemId);
        return itemSpecMapper.selectList(queryWrapper);
    }

    @Override
    public ItemsParam queryItemParamByItemId(String itemId) {
        LambdaQueryWrapper<ItemsParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ItemsParam::getItemId, itemId);
        return itemParamMapper.selectOne(queryWrapper);
    }

    @Override
    public PageResult<ItemCommentsVo> queryItemCommentPage(String itemId, Integer level, int page, int pageSize) {
        // 开始记录行
        int startNum = (page - 1) * pageSize;
        List<ItemCommentsVo> resultPage = itemCommentMapper.queryCommentsPage(itemId, level, startNum, pageSize);
        // 总条数
        long records = this.queryCommentsPageCount(itemId, level);
        // 总页数
        long pageSizeResult = records / pageSize;
        long total = (pageSizeResult == 0) ? pageSizeResult : pageSizeResult + 1;
        return new PageResult<>(page, total, records, resultPage);
    }

    @Override
    public long queryCommentsPageCount(String itemId, Integer level) {
        return itemCommentMapper.queryCommentsPageCount(itemId, level);
    }

    @Override
    public Map<String, Integer> queryCommentLevel(String itemId) {
        List<ItemCommentLevelVo> commentList = itemCommentMapper.queryCommentLevel(itemId);
        Map<String, Integer> map = new HashMap<>(8);
        Integer totalCounts = 0;
        for (ItemCommentLevelVo comment : commentList) {
            Integer commentLevelTotal = comment.getCommentLevelTotal();
            totalCounts += commentLevelTotal;
            map.put(comment.getCommentLevel(), commentLevelTotal);
        }
        // 总评论
        map.put("totalCounts", totalCounts);
        return map;
    }
}

package com.liuxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxi.mapper.*;
import com.liuxi.pojo.Items;
import com.liuxi.pojo.ItemsImg;
import com.liuxi.pojo.ItemsParam;
import com.liuxi.pojo.ItemsSpec;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.ItemCommentLevelVo;
import com.liuxi.pojo.vo.ItemCommentVo;
import com.liuxi.pojo.vo.ItemSearchVo;
import com.liuxi.service.ItemService;
import com.liuxi.util.enums.SortEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PageResult<ItemCommentVo> queryItemCommentPage(String itemId, Integer level, int page, int pageSize) {
        // 开始记录行
        int startNum = (page - 1) * pageSize;
        List<ItemCommentVo> resultPage = itemCommentMapper.queryCommentsPage(itemId, level, startNum, pageSize);
        resultPage.forEach(item -> {
            String nickname = item.getNickname();
            // 昵称脱敏
            String desensitization = desensitization(nickname);
            item.setNickname(desensitization);
        });
        // 总条数
        long records = this.queryCommentsPageCount(itemId, level);
        // 总页数
        long pageSizeResult = records % pageSize;
        long total = (pageSizeResult == 0) ? records / pageSize : (records / pageSize) + 1;
        return new PageResult<>(page, total, records, resultPage);
    }

    @Override
    public long queryCommentsPageCount(String itemId, Integer level) {
        return itemCommentMapper.queryCommentsPageCount(itemId, level);
    }

    @Override
    public ItemCommentLevelVo queryCommentLevel(String itemId) {
        return itemCommentMapper.queryCommentLevel(itemId);
    }

    @Override
    public PageResult<ItemSearchVo> searchByKeWords(String keywords, String sort, int page, int pageSize) {
        // 当前页
        int currentPage = (page - 1) * pageSize;
        //【k：默认排序，c：销量排序，p：价格排序】
        if (SortEnum.SELL_COUNT.type.equals(sort)) {
            sort = "sellCounts";
        } else if (SortEnum.PRICE.type.equals(sort)) {
            sort = "price";
        } else {
            sort = "itemName";
        }
        List<ItemSearchVo> searchItemList = itemMapper.searchItemByKeyWord(keywords, sort, currentPage, pageSize);

        // 查询商品数量
        LambdaQueryWrapper<Items> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Items::getItemName, keywords);
        // 总记录
        Long records = itemMapper.selectCount(queryWrapper);
        // 总页数
        long total = records % pageSize;
        total = (total == 0) ? records / pageSize : (records / pageSize) + 1;
        return new PageResult<>(page, total, records, searchItemList);
    }

    @Override
    public PageResult<ItemSearchVo> searchByCatItems(Integer catId, String sort, int page, int pageSize) {
        // limit 分页从零开始
        int currentPage = (page - 1) * pageSize;
        List<ItemSearchVo> itemList = itemMapper.searchByCatId(catId, sort, currentPage, pageSize);
        // 查询总条数
        long count = itemMapper.selectCount(new LambdaQueryWrapper<>(Items.class).eq(Items::getCatId, catId));
        long pageTotal = count % pageSize;
        pageTotal = (pageTotal == 0) ? (count / pageSize) : (count / pageSize) + 1;
        return new PageResult<>(page, pageTotal, count, itemList);
    }

    /**
     * 数据脱敏
     * @param str
     * @return
     */
    private String desensitization(String str) {
        String[] strArr = str.split("");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (i == 0 || (i == strArr.length - 1)) {
                result.append(strArr[i]);
                continue;
            }
            result.append("*");
        }
        return result.toString();
    }
}

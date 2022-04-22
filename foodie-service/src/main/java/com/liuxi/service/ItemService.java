package com.liuxi.service;

import com.liuxi.pojo.*;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.ItemCommentVo;
import com.liuxi.pojo.vo.ItemSearchVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 23:52
 */
public interface ItemService {

    /**
     * 根据 id 查询商品
     * @param itemId
     * @return
     */
    Items queryItemByItemId(String itemId);

    /**
     * 根据商品 id 查询商品图片
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgByItemId(String itemId);

    /**
     * 根据商品ID 查询商品规格
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecByItemId(String itemId);

    /**
     * 根据商品Id 查询商品参数
     * @param itemId
     * @return
     */
    ItemsParam queryItemParamByItemId(String itemId);

    /**
     * 根据商品id查询商品评论
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<ItemCommentVo> queryItemCommentPage(String itemId, Integer level, int page, int pageSize);

    /**
     * 根据商品id查询商品评论总数
     * @param itemId
     * @param level
     * @return
     */
    long queryCommentsPageCount(String itemId, Integer level);

    /**
     * 查询各个评论总数
     * @param itemId
     * @return
     */
    Map<String, Integer> queryCommentLevel(String itemId);

    /**
     * 根据用户 id 查询评论
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<ItemCommentVo> queryCommentByUserId(String userId, int page, int pageSize);

    /**
     * 商品搜索
     * @param keywords 搜索关键字
     * @param sort     排序 【k：默认排序，c：销量排序，p：价格排序】
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<ItemSearchVo> searchByKeWords(String keywords, String sort, int page, int pageSize);

    /**
     * 根据商品类别搜索
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<ItemSearchVo> searchByCatItems(Integer catId, String sort, int page, int pageSize);

}

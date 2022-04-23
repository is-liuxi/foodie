package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.ItemsComments;
import com.liuxi.pojo.vo.ItemCommentLevelVo;
import com.liuxi.pojo.vo.ItemCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/19 0:42
 */
public interface ItemCommentMapper extends BaseMapper<ItemsComments> {

    /**
     * 查询各个评论总数
     * @param itemId
     * @return
     */
    ItemCommentLevelVo queryCommentLevel(String itemId);

    /**
     * 根据商品id查询商品评论
     * @param itemId
     * @param commentLevel
     * @param page
     * @param pageSize
     * @return
     */
    List<ItemCommentVo> queryCommentsPage(@Param("itemId") String itemId, @Param("commentLevel") Integer commentLevel,
                                          @Param("page") long page, @Param("pageSize") long pageSize);

    /**
     * 根据商品id查询商品评论总数
     * @param itemId
     * @param commentLevel
     * @return
     */
    long queryCommentsPageCount(@Param("itemId") String itemId, @Param("commentLevel") Integer commentLevel);

    /**
     * 查询用户所有商品评价
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    List<ItemCommentVo> queryItemCommentByUserId(@Param("userId") String userId, @Param("page") long page, @Param("pageSize") long pageSize);

    /**
     * 用户评价数量
     * @param userId
     * @return
     */
    int queryItemCommentByUserIdCount(@Param("userId") String userId);
}

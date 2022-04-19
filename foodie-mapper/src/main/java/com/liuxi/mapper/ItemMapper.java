package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.Items;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.SearchItemsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品
 * </P>
 * @author LiuXi
 * @date 2022/4/18 23:51
 */
public interface ItemMapper extends BaseMapper<Items> {

    /**
     * 商品搜索
     * @param keywords 搜索关键字
     * @param sort 排序 【k：默认排序，c：销量排序，p：价格排序】
     * @param page
     * @param pageSize
     * @return
     */
    List<SearchItemsVo> searchItemByKeyWord(@Param("keywords") String keywords, @Param("sort") String sort,
                               @Param("page") int page, @Param("pageSize") int pageSize);

    /**
     * 根据商品类别搜索
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    List<SearchItemsVo> searchByCatId(@Param("catId") Integer catId, @Param("sort") String sort,
                                            @Param("page") int page, @Param("pageSize") int pageSize);
}

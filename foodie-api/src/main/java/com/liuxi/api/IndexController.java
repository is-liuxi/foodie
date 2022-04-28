package com.liuxi.api;

import com.liuxi.pojo.Carousel;
import com.liuxi.pojo.Category;
import com.liuxi.pojo.vo.CategoryVo;
import com.liuxi.pojo.vo.ItemNewVo;
import com.liuxi.service.CarouselService;
import com.liuxi.service.CategoryService;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 16:36
 */
@RestController
@RequestMapping("index")
@Api(tags = "首页", description = "首页操作")
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("carousel/list")
    @ApiOperation(value = "首页轮播图", notes = "查询全部的轮播图")
    public ResultJsonResponse list() {
        List<Carousel> carouselList = carouselService.queryCarousel();
        return ResultJsonResponse.ok(carouselList);
    }

    @GetMapping("category")
    @ApiOperation(value = "查询父级分类", notes = "查询大分类")
    public ResultJsonResponse cats() {
        List<Category> list = categoryService.queryCategoryRootList(1);
        return ResultJsonResponse.ok(list);
    }

    @GetMapping("subCategory/{rootId}")
    @ApiOperation(value = "查询子分类", notes = "查询二三级分类")
    public ResultJsonResponse subCats(@ApiParam(name = "rootId", value = "一级分类ID", required = true)
                                      @PathVariable("rootId") Integer rootId) {
        List<CategoryVo> categoryList = categoryService.queryCategoryChildrenList(rootId);
        return ResultJsonResponse.ok(categoryList);
    }

    @GetMapping("sixNewItems/{rootId}")
    @ApiOperation(value = "查询推荐商品", notes = "查询热门的八个商品")
    public ResultJsonResponse sixNewItems(@ApiParam(name = "rootId", value = "一级分类ID", required = true)
                                          @PathVariable("rootId") Integer rootId) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("rootCartId", rootId);
        List<ItemNewVo> itemList = categoryService.querySixNewItemsLazy(map);
        return ResultJsonResponse.ok(itemList);
    }
}

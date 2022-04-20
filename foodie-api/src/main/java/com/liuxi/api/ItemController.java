package com.liuxi.api;

import com.liuxi.pojo.Items;
import com.liuxi.pojo.ItemsImg;
import com.liuxi.pojo.ItemsParam;
import com.liuxi.pojo.ItemsSpec;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.ShopCartVo;
import com.liuxi.pojo.vo.ItemCommentVo;
import com.liuxi.pojo.vo.ItemSearchVo;
import com.liuxi.service.ItemService;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 23:54
 */
@RestController
@RequestMapping("items")
@Api(description = "商品操作", tags = "商品详情页")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("info/{itemId}")
    @ApiOperation(value = "商品详情页", notes = "传入商品id查询商品详情")
    public ResultJsonResponse itemInfo(@PathVariable("itemId") String itemId) {
        Items items = itemService.queryItemByItemId(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgByItemId(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecByItemId(itemId);
        ItemsParam itemsParam = itemService.queryItemParamByItemId(itemId);
        Map<String, Object> resultMap = new HashMap<>(8);
        resultMap.put("item", items);
        resultMap.put("itemImgList", itemsImgList);
        resultMap.put("itemSpecList", itemsSpecList);
        resultMap.put("itemParams", itemsParam);
        return ResultJsonResponse.ok(resultMap);
    }

    @GetMapping("comments")
    @ApiOperation(value = "商品评论", notes = "商品详情")
    public ResultJsonResponse comments(@RequestParam("itemId") String itemId,
                                       @RequestParam(value = "level", required = false) Integer level,
                                       @RequestParam("page") int page,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageResult<ItemCommentVo> result = itemService.queryItemCommentPage(itemId, level, page, pageSize);
        return ResultJsonResponse.ok(result);
    }

    @GetMapping("commentLevel/{itemId}")
    @ApiOperation(value = "查询商品评论总数", notes = "查询商品各种级别评论总数")
    public ResultJsonResponse commentLevel(@PathVariable String itemId) {
        return ResultJsonResponse.ok(itemService.queryCommentLevel(itemId));
    }

    @GetMapping("search")
    @ApiOperation(value = "商品搜索", notes = "商品搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywords", value = "搜索关键字", required = true, dataType = "string"),
            @ApiImplicitParam(name = "sort", value = "显示排序【k：默认排序，c：销量排序，p：价格排序】", required = true, dataType = "string"),
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面显示大小", required = true, dataType = "int")
    })
    public ResultJsonResponse search(@RequestParam("keywords") String keywords,
                                     @RequestParam(value = "sort", required = false) String sort,
                                     @RequestParam("page") int page,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return ResultJsonResponse.errorMsg("查询关键字不能为空");
        }
        PageResult<ItemSearchVo> itemList = itemService.searchByKeWords(keywords, sort, page, pageSize);
        return ResultJsonResponse.ok(itemList);
    }

    @GetMapping("catItems")
    @ApiOperation(value = "商品分类搜索", notes = "商品分类搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywords", value = "搜索关键字", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "sort", value = "显示排序【k：默认排序，c：销量排序，p：价格排序】", required = true, dataType = "string"),
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面显示大小", required = true, dataType = "int")
    })
    public ResultJsonResponse searchByCatItems(@RequestParam("catId") Integer catId,
                                               @RequestParam(value = "sort", required = false) String sort,
                                               @RequestParam("page") int page,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        if (catId == null) {
            return ResultJsonResponse.errorMsg("分类id不能为空");
        }
        PageResult<ItemSearchVo> itemList = itemService.searchByCatItems(catId, sort, page, pageSize);
        return ResultJsonResponse.ok(itemList);
    }
}

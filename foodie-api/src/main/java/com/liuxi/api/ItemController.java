package com.liuxi.api;

import com.liuxi.pojo.*;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.ItemCommentsVo;
import com.liuxi.service.ItemService;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
                                       @RequestParam("pageSize") int pageSize) {
        PageResult<ItemCommentsVo> result = itemService.queryItemCommentPage(itemId, level, page, pageSize);
        return ResultJsonResponse.ok(result);
    }

    @GetMapping("commentLevel/{itemId}")
    @ApiOperation(value = "查询商品评论总数", notes = "查询商品各种级别评论总数")
    public ResultJsonResponse commentLevel(@PathVariable String itemId) {
        return ResultJsonResponse.ok(itemService.queryCommentLevel(itemId));
    }
}

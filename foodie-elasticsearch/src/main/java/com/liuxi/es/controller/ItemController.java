package com.liuxi.es.controller;

import com.liuxi.es.pojo.EsItems;
import com.liuxi.es.service.ItemEsService;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.ItemSearchVo;
import com.liuxi.service.ItemService;
import com.liuxi.util.common.ResultJsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/29 9:47
 */
@RestController
@RequestMapping("items")
public class ItemController {

    @Autowired
    private ItemEsService itemEsService;

    @GetMapping("es/search")
    public ResultJsonResponse search(@RequestParam("keywords") String keywords,
                                     @RequestParam(value = "sort", required = false) String sort,
                                     @RequestParam("page") int page,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return ResultJsonResponse.errorMsg("查询关键字不能为空");
        }

        PageResult<EsItems> itemList = itemEsService.esSearchByKeWords(keywords, sort, page, pageSize);
        return ResultJsonResponse.ok(itemList);
    }
}

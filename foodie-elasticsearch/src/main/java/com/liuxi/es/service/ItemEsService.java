package com.liuxi.es.service;

import com.liuxi.es.pojo.EsItems;
import com.liuxi.pojo.page.PageResult;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/29 9:50
 */
public interface ItemEsService {

    /**
     * 使用 ES 搜索
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<EsItems> esSearchByKeWords(String keywords, String sort, int page, int pageSize);
}

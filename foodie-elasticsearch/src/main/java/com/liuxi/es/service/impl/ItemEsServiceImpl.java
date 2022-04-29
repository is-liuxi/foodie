package com.liuxi.es.service.impl;

import com.liuxi.es.pojo.EsItems;
import com.liuxi.es.service.ItemEsService;
import com.liuxi.pojo.page.PageResult;
import org.apache.commons.collections.MapUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/29 9:52
 */
@Service
public class ItemEsServiceImpl implements ItemEsService {

    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public PageResult<EsItems> esSearchByKeWords(String keywords, String sort, int page, int pageSize) {
        // ES 分页从零开始
        page--;

        // 排序的字段
        // SortBuilder<FieldSortBuilder> itemNameSort = new FieldSortBuilder(sort).order(SortOrder.DESC);

        // 分页参数
        Pageable pageable = PageRequest.of(page, pageSize);
        String itemNameField = "itemName";
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                // 参数：查询的字段，关键字
                .withQuery(QueryBuilders.matchQuery(itemNameField, keywords))
                // 设置高亮显示的字段
                .withHighlightFields(new HighlightBuilder.Field(itemNameField))
                // 排序
                // .withSort(itemNameSort)
                // 分页
                .withPageable(pageable)
                .build();

        AggregatedPage<EsItems> pageItems = template.queryForPage(searchQuery, EsItems.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<EsItems> esItemsList = new ArrayList<>();

                // 拿到搜索到的结果集
                SearchHits hits = response.getHits();
                for (SearchHit hit : hits) {
                    HighlightField highlightField = hit.getHighlightFields().get("itemName");
                    // 拿到高亮显示的结果
                    String descriptionValue = highlightField.getFragments()[0].toString();

                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    String itemId = MapUtils.getString(sourceAsMap, "itemId");
                    String imgUrl = MapUtils.getString(sourceAsMap, "imgUrl");
                    Integer price = MapUtils.getInteger(sourceAsMap, "price");
                    Integer sellCounts = MapUtils.getInteger(sourceAsMap, "sellCounts");

                    EsItems esItems = new EsItems();
                    esItems.setItemName(descriptionValue);
                    esItems.setItemId(itemId);
                    esItems.setImgUrl(imgUrl);
                    esItems.setPrice(price);
                    esItems.setSellCounts(sellCounts);
                    esItemsList.add(esItems);
                }

                return new AggregatedPageImpl<>((List<T>) esItemsList, pageable, response.getHits().totalHits);
            }
        });

        return new PageResult<>(page + 1, pageItems.getTotalPages(), pageItems.getTotalElements(), pageItems.getContent());
    }
}

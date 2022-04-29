package com.liuxi.es;

import com.liuxi.es.pojo.Stu;
import org.apache.commons.collections.MapUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/28 19:16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsDocumentHardTest {

    @Autowired
    private ElasticsearchTemplate template;

    /**
     * 分页搜索
     */
    @Test
    public void searchStuDoc() {
        // 分页参数
        Pageable pageable = PageRequest.of(0, 2);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                // 参数：查询的字段，关键字
                .withQuery(QueryBuilders.matchQuery("description", "四"))
                // 分页
                .withPageable(pageable)
                .build();

        AggregatedPage<Stu> pagedStu = template.queryForPage(searchQuery, Stu.class);

        System.out.println("总页数：" + pagedStu.getTotalPages());
        List<Stu> stuList = pagedStu.getContent();
        System.out.println(stuList);
    }

    /**
     * 高亮显示
     */
    @Test
    public void highlightStuDoc() {
        String preTag = "<font color='red'>";
        String postTag = "</font>";

        // 排序的字段
        SortBuilder moneySort = new FieldSortBuilder("money").order(SortOrder.DESC);
        SortBuilder ageSort = new FieldSortBuilder("age").order(SortOrder.DESC);

        // 分页参数
        Pageable pageable = PageRequest.of(0, 2);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                // 参数：查询的字段，关键字
                .withQuery(QueryBuilders.matchQuery("description", "四"))
                // 设置高亮显示的字段
                .withHighlightFields(new HighlightBuilder.Field("description")
                        .preTags(preTag).postTags(postTag))
                // 排序
                .withSort(moneySort)
                .withSort(ageSort)
                // 分页
                .withPageable(pageable)
                .build();

        AggregatedPage<Stu> pagedStu = template.queryForPage(searchQuery, Stu.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<Stu> stuList = new ArrayList<>();

                // 拿到搜索到的结果集
                SearchHits hits = response.getHits();
                for (SearchHit hit : hits) {
                    HighlightField highlightField = hit.getHighlightFields().get("description");
                    // 拿到高亮显示的结果
                    String descriptionValue = highlightField.getFragments()[0].toString();

                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    Long stuId = MapUtils.getLong(sourceAsMap, "stuId");
                    String name = MapUtils.getString(sourceAsMap, "name");
                    Integer age = MapUtils.getInteger(sourceAsMap, "age");
                    Float money = MapUtils.getFloat(sourceAsMap, "money");
                    String sign = MapUtils.getString(sourceAsMap, "sign");

                    Stu stu = new Stu();
                    stu.setDescription(descriptionValue);
                    stu.setStuId(stuId);
                    stu.setName(name);
                    stu.setAge(age);
                    stu.setMoney(money);
                    stu.setSign(sign);
                    stuList.add(stu);
                }

                if (stuList.size() > 0) {
                    return new AggregatedPageImpl<>((List<T>) stuList);
                }
                return null;
            }
        });

        System.out.println("总页数：" + pagedStu.getTotalPages());
        List<Stu> stuList = pagedStu.getContent();
        System.out.println(stuList);
    }
}

package com.liuxi.es;

import com.liuxi.es.pojo.Stu;
import org.elasticsearch.action.index.IndexRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * ES 中的 document 类似 MySQL 中的记录
 * </P>
 * @author liu xi
 * @date 2022/4/28 19:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsDocumentBasicTest {

    @Autowired
    private ElasticsearchTemplate template;

    /**
     * 文档值更新
     */
    @Test
    public void updateStuDoc() {
        // 更新的字段、值
        Map<String, Object> sourceMap = new HashMap<String, Object>() {
            {
                put("money", 20.2F);
                put("sign", "哒哒哒");
                put("description", "张三李四王五赵六");
            }
        };
        IndexRequest request = new IndexRequest();
        request.source(sourceMap);

        UpdateQuery updateQuery = new UpdateQueryBuilder()
                // withClass() 数据所在的索引
                .withClass(Stu.class)
                // 更新 id 为 1002 的记录
                .withId("1001")
                .withIndexRequest(request)
                .build();
        template.update(updateQuery);
    }

    /**
     * 根据 id 查询
     */
    @Test
    public void getStuDocById() {
        GetQuery query = new GetQuery();
        query.setId("1002");

        Stu stu = template.queryForObject(query, Stu.class);
        System.out.println(stu);
    }

    /**
     * 根据 id 删除，返回主键
     */
    @Test
    public void deleteStuDocById() {
        String result = template.delete(Stu.class, "1002");
        System.out.println(result);
    }
}

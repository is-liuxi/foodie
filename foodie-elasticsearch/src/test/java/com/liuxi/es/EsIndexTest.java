package com.liuxi.es;

import com.liuxi.es.pojo.Stu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * ES 中的 index 类似 MySQL 中的表或者数据库
 * </P>
 * @author liu xi
 * @date 2022/4/28 18:44
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsIndexTest {

    @Autowired
    private ElasticsearchTemplate template;

    /**
     * 创建索引
     */
    @Test
    public void createIndex() {
        Stu stu = new Stu();
        stu.setStuId(1002L);
        stu.setName("李四");
        stu.setAge(22);
        stu.setMoney(18.8F);
        stu.setSign("good good");
        stu.setDescription("your good");

        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        template.index(indexQuery);
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() {
        template.deleteIndex("ik_person");
    }

}

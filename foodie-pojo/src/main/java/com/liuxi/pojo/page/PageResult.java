package com.liuxi.pojo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/19 0:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    /**
     * 当前页
     */
    private long page;
    /**
     * 总页数
     */
    private long total;
    /**
     * 总记录
     */
    private long records;
    /**
     * 数据列表
     */
    private List<T> rows;
}

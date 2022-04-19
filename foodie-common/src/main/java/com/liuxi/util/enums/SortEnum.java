package com.liuxi.util.enums;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/19 23:53
 */
public enum SortEnum {
    DEFAULT("k", "默认排序"),
    SELL_COUNT("c", "销量排序"),
    PRICE("p", "价格排序");
    public String type;
    public String value;

    SortEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }
}

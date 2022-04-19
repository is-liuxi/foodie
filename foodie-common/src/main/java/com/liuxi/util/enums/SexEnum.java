package com.liuxi.util.enums;

/**
 * <p>
 * 性别枚举
 * </P>
 * @author liu xi
 * @date 2022/4/17 6:00
 */
public enum SexEnum {
    WOMAN(0, "女"),
    MAN(1, "男"),
    SECRET(2, "保密");

    public final Integer type;
    public final String value;

    SexEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}

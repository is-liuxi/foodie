package com.liuxi.util.enums;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 16:53
 */
public enum YesOrNo {
    NO(0, "否"),
    YES(1, "是");
    public final int type;
    public final String value;

    YesOrNo(int type, String value) {
        this.type = type;
        this.value = value;
    }
}

package com.liuxi.util.enums;

/**
 * <p>
 * 支付方式
 * </P>
 * @author liu xi
 * @date 2022/4/20 23:56
 */
public enum PayMethodEnum {
    ALI_PAY(1, "微信支付"),
    WE_CHAT_PAY(2, "微信支付");
    public Integer type;
    public String value;

    PayMethodEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}

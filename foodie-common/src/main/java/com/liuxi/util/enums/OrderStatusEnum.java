package com.liuxi.util.enums;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/21 1:37
 */
public enum OrderStatusEnum {
    WAITING_PAYMENT(10, "待付款"),
    PAID(20, "已付款"),
    SEED_GOODS(30, "已发货"),
    TRAN_SUCCESS(40, "交易成功"),
    TRAN_FAIL(50, "交易失败");

    public Integer type;
    public String value;

    OrderStatusEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}

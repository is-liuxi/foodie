package com.liuxi.util.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

/**
 * <p>
 *  统一返回值
 * </P>
 * @author liu xi
 * @date 2022/4/17 4:42
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultJsonResponse {

    /**
     * 定义 jackson 对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 定义业务状态
     */
    private int status = 200;
    /**
     * 响应信息
     */
    private String msg = "ok";
    /**
     * 响应数据
     */
    private Object data;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String ok;

    public ResultJsonResponse(Object data) {
        this.data = data;
    }

    public ResultJsonResponse(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回
     * @param data
     * @return
     */
    public static ResultJsonResponse ok(Integer status, String msg, Object data) {
        return new ResultJsonResponse(status, msg, data);
    }

    public static ResultJsonResponse ok(Integer status, String msg, Object data, String ok) {
        return new ResultJsonResponse(status, msg, data, ok);
    }

    public static ResultJsonResponse ok(Object data) {
        return new ResultJsonResponse(data);
    }

    public static ResultJsonResponse ok() {
        return new ResultJsonResponse();
    }

    /**
     * 失败返回
     * @param msg
     * @return
     */
    public static ResultJsonResponse errorMsg(String msg) {
        return new ResultJsonResponse(500, msg, null);
    }

    public static ResultJsonResponse errorMap(Object data) {
        return new ResultJsonResponse(501, "error", data);
    }

    public static ResultJsonResponse errorTokenMsg(String msg) {
        return new ResultJsonResponse(502, msg, null);
    }

    public static ResultJsonResponse errorException(String msg) {
        return new ResultJsonResponse(555, msg, null);
    }

    public static ResultJsonResponse errorUserQQ(String msg) {
        return new ResultJsonResponse(556, msg, null);
    }
}

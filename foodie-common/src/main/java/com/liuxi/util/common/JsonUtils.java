package com.liuxi.util.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/28 7:10
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换为 json 字符串
     * @param data
     * @return
     */
    public static String writeValueAsString(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 json 结果集转换为对象
     * @param jsonData
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T readValue(String jsonData, Class<T> valueType) {
        try {
            return MAPPER.readValue(jsonData, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 json 数据转换成 List 对象
     * @param jsonData
     * @param pojoType
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> pojoType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, pojoType);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

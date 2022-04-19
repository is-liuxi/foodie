package com.liuxi.exception;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/17 4:29
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String, Object> parameterException(MissingServletRequestParameterException ex) {
        // 大于 负载因子*0.75 开始扩容
        Map<String, Object> map = new HashMap<>(3);
        map.put("errMsg", ex.getMessage());
        map.put("errCode", 400);
        return map;
    }
}

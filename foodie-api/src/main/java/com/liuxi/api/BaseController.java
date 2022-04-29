package com.liuxi.api;

import com.liuxi.util.common.ConstantUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/28 10:18
 */
public class BaseController {

    public Cookie updateCookie(String cacheValue) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(ConstantUtils.SHOP_CART_COOKIE_KEY, URLEncoder.encode(cacheValue, StandardCharsets.UTF_8.toString()));
        cookie.setPath("/");
        if (StringUtils.isBlank(cacheValue)) {
            cookie.setMaxAge(0);
        }
        return cookie;
    }

    public Cookie addCookie(String name, String value) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(name, URLEncoder.encode(value, StandardCharsets.UTF_8.toString()));
        cookie.setPath("/");
        return cookie;
    }
}

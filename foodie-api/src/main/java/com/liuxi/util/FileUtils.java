package com.liuxi.util;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 17:22
 */
public class FileUtils {

    /**
     * 设置上传后的路径
     * @return
     */
    public static String setFilePath(String ext) {
        LocalDateTime dateTime = LocalDateTime.now();
        return "image/" + dateTime.getYear() + "/"
                + dateTime.getMonth().getValue() + "/"
                + dateTime.getDayOfMonth() + "/"
                + System.currentTimeMillis() + "." + ext;
    }
}

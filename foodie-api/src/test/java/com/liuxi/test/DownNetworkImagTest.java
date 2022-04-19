package com.liuxi.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 22:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DownNetworkImagTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test() throws Exception {
        List<String> urls = jdbcTemplate.queryForList("select url from items_img", String.class);
        for (String pictureUrl : urls) {
            String[] arr = pictureUrl.split("/");
            String path = "D:\\project\\java\\mooc\\project\\img\\items\\" + arr[arr.length - 2];
            String fileName = arr[arr.length - 1];

            //建立图片连接
            URL url = new URL(pictureUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置超时时间
            connection.setConnectTimeout(10 * 1000);

            //如果没有文件夹则创建
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            //输出流，图片输出的目的文件
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(path + "/" + fileName));

            //输入流
            InputStream stream = connection.getInputStream();
            int len = 0;
            byte[] test = new byte[1024];
            //以流的方式上传
            while ((len = stream.read(test)) != -1) {
                fos.write(test, 0, len);
            }

            //记得关闭流，不然消耗资源
            stream.close();
            fos.close();
        }
    }

    @Test
    public void test2() {
        String pictureUrl = "http://122.152.205.72:88/foodie/bingan-1004/img1.png";
        String[] arr = pictureUrl.split("/");
        String fileName = arr[arr.length - 1];
        String dir = arr[arr.length - 2];
        System.out.println(fileName);
        System.out.println(dir);
    }
}

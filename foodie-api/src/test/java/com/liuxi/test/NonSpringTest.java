package com.liuxi.test;

import com.liuxi.util.enums.YesOrNoEnum;
import org.junit.Test;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/19 22:31
 */
public class NonSpringTest {

    @Test
    public void test() {
        System.out.println(12 / 20);
    }

    @Test
    public void test2() {
        int len = 6;
        String name = "123456789";
        System.out.println(name.substring(5));
        String[] strArr = name.split("");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (i == 0 || (i == strArr.length - 1)) {
                result.append(strArr[i]);
                continue;
            }
            result.append("*");
        }
        System.out.println(result);
    }
}

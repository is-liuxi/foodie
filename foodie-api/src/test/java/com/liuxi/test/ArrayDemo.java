package com.liuxi.test;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/5/2 15:06
 */
public class ArrayDemo {
    // 数组长度
    private int size;
    // 数组类型
    private int[] array;
    // 下标
    private int index;

    public ArrayDemo(int size, int... values) {
        this.size = size;
        this.array = values;
        index = 0;
    }

    public void print(int[] array) {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public void insert(int index, int value) {
        print(array);
        index -= 1;
        int[] newArr = new int[size * 2];
        for (int i = 0; i < array.length; i++) {
            if (i >= index) {
                newArr[i + 1] = array[i];
            } else {
                newArr[i] = array[i];
            }
        }
        newArr[index] = value;
        print(newArr);
    }

    public void delete(int index) {
        index -= 1;
        int[] newArr = new int[size];
        for (int i = 0; i < array.length; i++) {
            if (i == index) {
                newArr[i] = array[i + 1];
            } else {
                newArr[i] = array[i];
            }
        }
        print(newArr);
    }

    public static void main(String[] args) {
        ArrayDemo arrayDemo = new ArrayDemo(10, 1, 2, 3, 4, 5, 6);
        arrayDemo.insert(2, 5);
        arrayDemo.delete(2);

    }
}

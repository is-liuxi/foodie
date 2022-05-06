package com.liuxi.test;

import java.util.*;
public class CountArray {
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{1, 3, 5, 7, 7, 2, 4, 2, 1, 10};
		Map<String, Integer> map = new HashMap<>();
		for (Integer integer : arr) {
			String key = String.valueOf(integer);
			if (map.get(key) == null) {
				map.put(key, 1);
			} else {
				int count = map.get(key);
				map.put(key, ++count);
			}
		}
		System.out.println(map);
		Collection<Integer> values = map.values();
		ArrayList<Integer> integers = new ArrayList<>(values);
		System.out.println(integers);

	}
}
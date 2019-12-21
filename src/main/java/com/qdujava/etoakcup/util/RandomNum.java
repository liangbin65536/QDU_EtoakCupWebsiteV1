package com.qdujava.etoakcup.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

/**
 * 获取n个给定范围不重复的数字
 * @Author: liangbin
 * @Date: 2018/4/27 16:59
 */
@Component
public class RandomNum {
    public static ArrayList<Integer> getNum(int n, int startIndex, int endIndex){
        ArrayList<Integer> numList = new ArrayList<>();
        Random random = new Random();
        boolean[] tags = new boolean[endIndex];
        int num;
        for (int i = 0; i < n; i++) {
            do {
                // 如果产生的数相同继续循环
                num = (int) (random.nextDouble() * (endIndex-startIndex) + startIndex);
            } while (tags[num]);
            tags[num] = true;
            numList.add(num);
        }
        return numList;
    }
}

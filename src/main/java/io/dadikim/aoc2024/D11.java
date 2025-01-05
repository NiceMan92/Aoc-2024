package io.dadikim.aoc2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class D11 {

    List<Long> input = Arrays.stream("64599 31 674832 2659361 1 0 8867 321".split(" ")).map(Long::parseLong).toList();
    List<Long> input1 = Arrays.stream("125 17".split(" ")).map(Long::parseLong).toList();

    private long count(List<Long>input, int blinks) {
        // init stones
        Map<Long, Long> stones = new HashMap<>();
        for (var v: input){
            stones.put(v ,1L);
        }

        for(int i = 0; i < blinks; i++) {
            Map<Long, Long> nextStones = new HashMap<>();
            for (var current : stones.entrySet()){
                var currentKey = current.getKey();
                var currentValue = stones.get(currentKey);
                if(currentKey == 0) {
                    nextStones.compute(1L, (k, v) -> v == null ? currentValue : v + currentValue);
                } else if(String.valueOf(currentKey).length() % 2 == 0) {
                    int half = String.valueOf(currentKey).length() / 2;
                    long keyHalf1 = Long.parseLong(String.valueOf(currentKey).substring(0, half));
                    long keyHalf2 = Long.parseLong(String.valueOf(currentKey).substring(half));
                    nextStones.compute(keyHalf1, (k,v) -> v == null ? currentValue : v + currentValue );
                    nextStones.compute(keyHalf2, (k,v) -> v == null ? currentValue : v + currentValue );
                } else {
                    nextStones.compute(currentKey  * 2024, (k, v) -> v == null ? currentValue : v + currentValue);
                }
            }
            stones = nextStones;
        }
        return  stones.values().stream().mapToLong(i -> i).sum();
    }

    @Test
    void part1() {
        Assertions.assertEquals(199986, count(input, 25));
    }

    @Test
    void part2() {
        Assertions.assertEquals(236804088748754L, count(input,75));
    }
}

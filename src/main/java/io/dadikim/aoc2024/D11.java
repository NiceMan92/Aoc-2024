package io.dadikim.aoc2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class D11 {

    private String transform(String input, int blinks) {
        if (blinks == 0) {
            return input.strip();
        }
        String[] stones = input.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String stone: stones){
            if(Objects.equals(stone, "0")) {
                stringBuilder.append("1")
                        .append(" ");
            } else if(stone.length() % 2 == 0) {
                int half = stone.length() / 2;
                stringBuilder.append(stone, 0, half)
                        .append(" ")
                        .append(Long.parseLong(stone.substring(half)))
                        .append(" ");
            } else {
                stringBuilder.append(Long.parseLong(stone) * 2024).append(" ");
            }
        }
        return transform(stringBuilder.toString(), blinks - 1);
    }

    @Test
    void part1() {
        String input = "64599 31 674832 2659361 1 0 8867 321";
        String transformed = transform(input, 25);
        Assertions.assertEquals(199986, transformed.split(" ").length);
    }
}

package io.dadikim.aoc2024;

import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Fail.fail;

public class D7 {

    private Map<Long, List<Integer>> readInput(@NonNull Path filePath) throws IOException {
        Map<Long, List<Integer>> map = new IdentityHashMap<>();
        try(Stream<String> lines = Files.lines(filePath)) {
            for (String[] str : lines.map(str -> str.split(": ")).toList()){
                map.put(Long.valueOf(str[0]), Arrays.stream(str[1].split(" ")).map(Integer::valueOf).toList());
            }
        }

        return map;
    }

    enum OPERATOR {
        PLUS,
        MULTI,
        CONCATENATE
    }


    @Test
    void test1 (){
        List<List<OPERATOR>> combinations = new ArrayList<>();
        OPERATOR [] arr = {OPERATOR.PLUS, OPERATOR.MULTI, OPERATOR.CONCATENATE};
        generateCombinations(arr, 3, new ArrayList<>(), combinations);
        System.out.println(combinations);
    }

    private static void generateCombinations(OPERATOR[] arr, int r, List<OPERATOR> current, List<List<OPERATOR>> combinations) {
        if (0 == r) {
            combinations.add(new ArrayList<>(current));
            return;
        }
        for (OPERATOR op : arr) {
            current.add(op);
            generateCombinations(arr, r - 1, current, combinations);
            current.remove (current.size() - 1);
        }
    }

    @Test
    void test(){
        try {
            Map<Long, List<Integer>> longListMap = readInput(Path.of("/Users/abdelkrimhaddadi/training/Java/Aoc2024/src/main/resources/D7-input.txt"));
            System.out.println("longListMap = " + longListMap);
            long result = 0;
            for (var es : longListMap.entrySet()){
                Long key = es.getKey();
                List<Integer> list = es.getValue();
                List<List<OPERATOR>> combinations = new ArrayList<>(Double.valueOf(Math.pow(2, list.size() -1 )).intValue());
                OPERATOR [] arr = {OPERATOR.PLUS, OPERATOR.MULTI, OPERATOR.CONCATENATE};
                generateCombinations(arr, list.size() -1, new ArrayList<>(), combinations);
                //System.out.println(combinations);

                for (var opList : combinations ) {
                    Long r = calculate(list, opList);
                    if( key.equals(r) ){
                        result += key;
                        break;
                    }
                }
            }
            System.out.println("total valid values = " + result);
        } catch (IOException e) {
            fail("Something went wrong with D6-map.txt file %s", e.getMessage());
        }
    }

    private Long calculate(List<Integer> list, List<OPERATOR> opList) {
        Long total = (long) list.get(0);
        for (int i = 1; i < list.size() ; i++) {
           total =  switch (opList.get(i-1)) {
               case PLUS -> total + list.get(i);
               case MULTI -> total * list.get(i);
               case CONCATENATE -> Long.valueOf(total + String.valueOf(list.get(i)));
           };
        }
        return total;
    }
}

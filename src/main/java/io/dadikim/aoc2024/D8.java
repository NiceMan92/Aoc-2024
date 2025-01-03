package io.dadikim.aoc2024;

import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.assertj.core.api.Fail.fail;

public class D8 {

    private char[][] readInput(@NonNull Path filePath) throws IOException {
        return Files.lines(filePath)
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private void displayMatrix(char [][] matrix){
        for (char [] chars : matrix) {
            for (char aChar : chars ) {
                System.out.print(aChar + "\t");
            }
            System.out.println();
        }
    }

    private Map<Character, List<D6.Position>> findAntennasPosition(char [][] matrix) {
        Map<Character, List<D6.Position>> antennas = new HashMap<>();
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix[1].length; j++) {
                if (matrix[i][j] != '.') {
                    antennas.putIfAbsent(matrix[i][j], new ArrayList<>());
                    antennas.get(matrix[i][j]).add(new D6.Position(i,j));
                }
            }
        }
        return antennas;
    }

    private List<List<D6.Position>> generateCombinations(List<D6.Position> arr) {
        List<List<D6.Position>> list = new ArrayList<>();
        for (int i =0; i < arr.size();i++) {
            for (int j = i +1; j < arr.size();j++) {
                list.add(List.of(arr.get(i), arr.get(j)));
            }
        }
        return list;
    }


    @Test
    void test(){
        try {
            System.out.println("Part1: ----------------------");
            char[][] map = readInput(Path.of("/Users/abdelkrimhaddadi/training/Java/Aoc2024/src/main/resources/D8-map.txt"));
            displayMatrix(map);
            Map<Character, List<D6.Position>> antennasPosition = findAntennasPosition(map);
            int count = 0;
            for (var antennas : antennasPosition.entrySet()) {
                List<List<D6.Position>> combinations = generateCombinations(antennas.getValue());
                for (var combination : combinations){
                    D6.Position o1 = combination.get(0);
                    D6.Position o2 = combination.get(1);
                    var iDistance = o2.i() - o1.i();
                    var dashI1 = o1.i() - iDistance;
                    var dashI2 = o2.i() + iDistance;
                    var jDistance = o2.j() - o1.j();
                    var dashJ1 = o1.j() - jDistance;
                    var dashJ2 = o2.j() + jDistance;
                    if(jDistance > 0 ){
                        if (dashI1 >= 0 && dashJ1 >= 0){
                            if (map[dashI1][dashJ1] != '#'){
                                count++;
                                map[dashI1][dashJ1] = '#';
                            }
                        }
                        if (dashI2 < map.length && dashJ2 < map.length ){
                            if (map[dashI2][dashJ2] != '#'){
                                count++;
                                map[dashI2][dashJ2] = '#';
                            }
                        }
                    } else {
                        if (dashJ2 >= 0 && dashI2 < map.length){
                            if (map[dashI2][dashJ2] != '#'){
                                count++;
                                map[dashI2][dashJ2] = '#';
                            }
                        }
                        if (dashJ1 < map.length &&  dashI1 >= 0){
                            if (map[dashI1][dashJ1] != '#'){
                                count++;
                                map[dashI1][dashJ1] = '#';
                            }
                        }
                    }
                }
            }

            System.out.println("count = " + count);
            displayMatrix(map);

        } catch (IOException e) {
            fail("Something went wrong with D8-map.txt file %s", e.getMessage());
        }
    }
}

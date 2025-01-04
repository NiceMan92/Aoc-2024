package io.dadikim.aoc2024;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.assertj.core.api.Fail.fail;

public class D10 {

    record Position(int i, int j){}

    private Character[][] readInput(@NonNull Path filePath) throws IOException {
        return Files.lines(filePath)
                .map(String::toCharArray)
                .map(this::fromCharToCharacter)
                .toArray(Character[][]::new);
    }

    private void displayMatrix(Character [][] matrix){
        for (Character [] chars : matrix) {
            for (Character aChar : chars ) {
                System.out.print(aChar + "\t");
            }
            System.out.println();
        }
    }

    private List<Position> findStartPosition(Character [][] matrix) {
        var list = new ArrayList<Position>();
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix[1].length; j++) {
                if (matrix[i][j].equals('0')) {
                    list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    private Character [] fromCharToCharacter(char[] chars ) {
        Character [] objects = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            objects[i] = chars[i];
        }
        return objects;
    }

    private void findNinePositionsStartingFromZero(Character [][] matrix, Position currentPosition, List<Position> ninePositions) {
        if(matrix[currentPosition.i][currentPosition.j] == '9') {
            ninePositions.add(currentPosition);
            return;
        }
        Position leftPosition = new Position(currentPosition.i, currentPosition.j - 1);
        if(leftNextStep(matrix, leftPosition, currentPosition)){
                findNinePositionsStartingFromZero(matrix, leftPosition, ninePositions);
        }
        Position rightPosition = new Position(currentPosition.i, currentPosition.j + 1);
        if(rightNextStep(matrix, rightPosition, currentPosition)) {
            findNinePositionsStartingFromZero(matrix, rightPosition, ninePositions);
        }
        Position upPosition = new Position(currentPosition.i - 1, currentPosition.j);
        if(upNextStep(matrix, upPosition, currentPosition)) {
            findNinePositionsStartingFromZero(matrix, upPosition, ninePositions);
        }
        Position downPosition = new Position(currentPosition.i + 1, currentPosition.j);
        if(downNextStep(matrix, downPosition, currentPosition)) {
            findNinePositionsStartingFromZero(matrix, downPosition, ninePositions);
        }
    }

    private boolean leftNextStep(Character[][] matrix, Position leftPosition, Position currentPosition) {
        return leftPosition.j >= 0  &&
                validMove(matrix, leftPosition, currentPosition);
    }

    private boolean rightNextStep(Character[][] matrix, Position rightPosition, Position currentPosition) {
        return rightPosition.j < matrix.length  &&
                validMove(matrix, rightPosition, currentPosition);
    }

    private boolean upNextStep(Character[][] matrix, Position upPosition, Position currentPosition) {
        return upPosition.i >= 0  &&
                validMove(matrix, upPosition, currentPosition);
    }

    private boolean downNextStep(Character[][] matrix, Position downPosition, Position currentPosition) {
        return downPosition.i < matrix.length  &&
                validMove(matrix, downPosition, currentPosition);
    }

    private boolean validMove(Character[][] matrix, Position rightPosition, Position currentPosition) {
        return Character.digit(matrix[rightPosition.i][rightPosition.j], 10) - Character.digit(matrix[currentPosition.i][currentPosition.j], 10) == 1;
    }

    private int mapWalk(Character [][] map) {
        List<Position> ninePositions = new ArrayList<>();
        List<Position> startPositions = findStartPosition(map);
        int sum = 0;
        for (var startPosition : startPositions) {
            findNinePositionsStartingFromZero(map, startPosition, ninePositions);
            sum += ninePositions.size();
            ninePositions.clear();
        }
        return sum;
    }


    @Test
    void test(){
        try {
            System.out.println("Part2: ----------------------");
            Character[][] map = readInput(Path.of("/Users/abdelkrimhaddadi/training/Java/Aoc2024/src/main/resources/D10-map.txt"));
            displayMatrix(map);
            int sum = mapWalk(map);
            System.out.println("sum of the scores: " + sum);
            Assertions.assertEquals(sum, 1045 );
        } catch (IOException e) {
            fail("Something went wrong with D6-map.txt file %s", e.getMessage());
        }
    }
}

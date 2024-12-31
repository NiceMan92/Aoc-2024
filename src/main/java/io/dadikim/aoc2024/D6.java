package io.dadikim.aoc2024;

import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Fail.fail;

public class D6 {

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

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

    private Optional<Position> findStartPosition(Character [][] matrix) {
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix[1].length; j++) {
                if (matrix[i][j].equals('^')) {
                    return Optional.of(new Position(i, j));
                }
            }
        }
        return Optional.empty();
    }

    private Set<Position> findPathPositions(Character [][] matrix) {
        var paths = new HashSet<Position>();
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix[1].length; j++) {
                if (matrix[i][j].equals('x') || matrix[i][j].equals('^')) {
                    paths.add(new Position(i, j));
                }
            }
        }
        return paths;
    }

    private Character [] fromCharToCharacter(char[] chars ) {
        Character [] objects = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            objects[i] = chars[i];
        }
        return objects;
    }


    private boolean mapWalk(Character [][] matrix, Position startPosition) {
        int i = startPosition.i, j = startPosition.j;
        Direction direction = Direction.UP;
        Set<Position> dashPositions = new HashSet<>();
        while (i >= 0 && i < matrix.length  && j >= 0 && j < matrix.length ) {
            matrix[i][j] = 'x';
            switch (direction) {
                case UP -> {
                    if(i > 0 && matrix[i-1][j] == '#') {
                        var dash = new Position(i + i-1, j + j);
                        if (dashPositions.contains(dash))
                            return true;
                        else
                            dashPositions.add(dash);
                        direction = Direction.RIGHT;
                    } else i--;
                }
                case DOWN -> {
                    if(i < matrix.length -1  && matrix[i+1][j] == '#' ) {
                        var dash = new Position(i+ i+1, j+j);
                        if (dashPositions.contains(dash))
                            return true;
                        else
                            dashPositions.add(dash);
                        direction = Direction.LEFT;
                    } else i++;

                }
                case LEFT -> {
                    if(j > 0 && matrix[i][j-1] == '#' ) {
                        var dash = new Position(i + i, j + j-1);
                        if (dashPositions.contains(dash))
                            return true;
                        else
                            dashPositions.add(dash);
                        direction = Direction.UP;
                    } else j--;
                }
                case RIGHT -> {
                    if(j < matrix.length-1 && matrix[i][j+1] == '#' ) {
                        var dash = new Position(i + i, j + j+1);
                        if (dashPositions.contains(dash))
                            return true;
                        else
                            dashPositions.add(dash);
                        direction = Direction.DOWN;
                    }else j++;
                }
            }
        }
        return false;
    }


    @Test
    void test(){
        try {
            System.out.println("Part1: ----------------------");
            Character[][] map = readInput(Path.of("/Users/abdelkrimhaddadi/training/Java/Aoc2024/src/main/resources/D6-map.txt"));
            displayMatrix(map);
            Position startPosition = findStartPosition(map).orElseThrow();
            Set<Position> path1 = new HashSet<>();
            System.out.println("No circle: " + mapWalk(map, startPosition));
            displayMatrix(map);
            Set<Position> path = findPathPositions(map);
            System.out.println("positions number: " + path.size());

            System.out.println("Part2: ----------------------");
            long circle = 0;
            var currentTime = System.currentTimeMillis();
            path.remove(startPosition);
            for (var dash : path) {
                map[dash.i][dash.j] = '#';
                boolean isCircle = mapWalk(map, startPosition);
                if(isCircle)
                    circle ++;
                map[dash.i][dash.j] = '.';
            }
            System.out.println("Circles:" + circle);
            System.out.println("Execution time: " + (System.currentTimeMillis() - currentTime));

            displayMatrix(map);
        } catch (IOException e) {
            fail("Something went wrong with D6-map.txt file %s", e.getMessage());
        }
    }


}

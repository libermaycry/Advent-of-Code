package advent_of_code.utils;

import java.util.Arrays;

public class Matrix {

    public enum Direction { N, NE, E, SE, S, SW, W, NW }

    char[][] matrix;

    public Matrix(char[][] matrix) {
        this.matrix = matrix;
    }

    public int length() {
        return matrix.length;
    }

    public char[] get(int i) {
        return matrix[i];
    }

    public char get(int i, int j) {
        return matrix[i][j];
    }

    public char set(int i, int j, char c) {
        return matrix[i][j] = c;
    }

    public boolean isInside(int i, int j) {
        return !isOutside(i, j);
    }

    public boolean isOutside(int i, int j) {
        return i < 0
                || i > matrix.length - 1
                || j < 0
                || j > matrix[i].length - 1;
    }

    public boolean check(int i, int j, char c) {
        return isInside(i, j) && matrix[i][j] == c;
    }

    public Matrix copy() {
        char[][] copy = new char[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                copy[i][j] = matrix[i][j];
            }
        }
        return new Matrix(copy);
    }

    public void setAll(char c) {
        for (char[] chars : matrix) {
            Arrays.fill(chars, c);
        }
    }

    public Pair<Integer, Integer> getFirstPositionByValue(char c) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == c)
                    return Pair.of(i,j);
            }
        }
        throw new IllegalStateException();
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] chars : matrix) {
            for (char c : chars) {
                sb.append(c).append(" ");
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}

package com.eight_puzzles;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {
    private int zeroPos;
    private final int[][] blocks;
    private final int dimension;

    public Board(int[][] blocks) {
        if (blocks == null)
            throw new java.lang.IllegalArgumentException();
        this.dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zeroPos = i * dimension + j;
                }
            }
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < dimension * dimension - 1; i++)
            if (blocks[i / dimension][i % dimension] != i + 1)
                hamming++;
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0)
                    manhattan += Math.abs((blocks[i][j] - 1) / dimension - i) + Math.abs((blocks[i][j] - 1) % dimension - j);
            }
        return manhattan;
    }

    public boolean isGoal() {
        for (int i = 0; i < dimension * dimension - 1; i++)
            if (blocks[i / dimension][i % dimension] != i + 1)
                return false;
        return true;
    }

    public Board twin() {
        int[][] twinBlocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            System.arraycopy(blocks[i], 0, twinBlocks[i], 0, dimension);
        int from, to;
        from = -1;
        to = -1;
        for (int i = 0; i < dimension * dimension - 1; i++) {
            if (i != zeroPos) {
                if (from == -1) {
                    from = i;
                    continue;
                }
                to = i;
                break;
            }
        }
        swap(twinBlocks, from, to);
        return new Board(twinBlocks);
    }

    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y == this)
            return true;
        if (y.getClass().isInstance(this))
            if (((Board) y).dimension() != dimension)
                return false;
            else
                return Arrays.deepEquals(blocks, ((Board) y).blocks);
        return false;
    }

    public Iterable<Board> neighbors() {
//        mode 0: move left; mode 1: move right; mode 2: move up; mode 3: move down.
        LinkedList<Board> neighbors = new LinkedList<>();
        if (zeroPos / dimension > 0)
            neighbors.add(move(2));
        if (zeroPos / dimension < dimension - 1)
            neighbors.add(move(3));
        if (zeroPos % dimension > 0)
            neighbors.add(move(0));
        if (zeroPos % dimension < dimension - 1)
            neighbors.add(move(1));
        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder repre = new StringBuilder();
        repre.append(Integer.toString(dimension)).append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++)
                repre.append(" ").append(blocks[i][j]);
            repre.append("\n");
        }
        return repre.toString();
    }

    private Board move(int mode) {
//        mode 0: move left; mode 1: move right; mode 2: move up; mode 3: move down.
        int[][] tmpBlocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            System.arraycopy(blocks[i], 0, tmpBlocks[i], 0, dimension);
        if (mode == 0) {
            swap(tmpBlocks, zeroPos, zeroPos - 1);
        } else if (mode == 1) {
            swap(tmpBlocks, zeroPos, zeroPos + 1);
        } else if (mode == 2) {
            swap(tmpBlocks, zeroPos, zeroPos - dimension);
        } else if (mode == 3) {
            swap(tmpBlocks, zeroPos, zeroPos + dimension);
        }
        return new Board(tmpBlocks);
    }

    private void swap(int[][] inBlocks, int a, int b) {
        int tmp = inBlocks[a / dimension][a % dimension];
        inBlocks[a / dimension][a % dimension] = inBlocks[b / dimension][b % dimension];
        inBlocks[b / dimension][b % dimension] = tmp;
    }

    public static void main(String[] args) {

    }
}

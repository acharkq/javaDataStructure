import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class Board {
    final private int[][] blocks;
    final private int dimension;
    private int zero_pos;

    public Board(int[][] blocks) {
        if (blocks == null)
            throw new java.lang.IllegalArgumentException();
        this.dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zero_pos = i * dimension + j;
                }
            }
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
    }

    public int manhattan() {
    }

    public boolean isGoal() {
        for (int i = 1; i < dimension * dimension; i++)
            if (blocks[i / dimension][i % dimension] != i)
                return false;
        return true;
    }

    public Board twin() {
        Board twin = new Board(blocks);
        int tmp = twin.blocks[0][0];
        twin.blocks[0][0] = twin.blocks[dimension - 1][dimension - 2];
        twin.blocks[dimension - 1][dimension - 2] = tmp;
        return twin;
    }

    public boolean equals(Object y) {
        return Arrays.deepEquals(blocks, ((Board) y).blocks);
    }

    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<>();
        if (zero_pos == 0) {
            neighbors.add(move(1));
            neighbors.add(move(3));
        } else if (zero_pos == dimension * dimension - 1) {
            neighbors.add(move(0));
            neighbors.add(move(3));
        } else if (zero_pos / dimension == 0 || zero_pos / dimension == dimension - 1) {
            neighbors.add(move(0));
            neighbors.add(move(1));
            if (zero_pos / dimension == 0)
                neighbors.add(move(3));
            else
                neighbors.add(move(2));
        } else if (zero_pos % dimension == 0 || zero_pos % dimension == dimension - 1) {
            neighbors.add(move(2));
            neighbors.add(move(3));
            if (zero_pos % dimension == 0)
                neighbors.add(move(1));
            else
                neighbors.add(move(0));
        } else {
            for (int i = 0; i < 4; i++)
                neighbors.add(move(i));
        }
        return neighbors;
    }

    @Override
    public String toString() {
        System.out.println(dimension);
        for (int i = 0; i < dimension; i++) {
            for(int j = 0;j<dimension;j++)
                System.out.print(" " + blocks[i][j]);
        }
    }

    private Board move(int mode) {
//        mode 0: move left; mode 1: move right; mode 2: move up; mode 3: move down.
        int[][] tmpBlocks = new int[dimension][dimension];
        System.arraycopy(this.blocks, 0, tmpBlocks, 0, dimension);
        if (mode == 0) {
            swap(tmpBlocks, zero_pos, zero_pos - 1);
        } else if (mode == 1) {
            swap(tmpBlocks, zero_pos, zero_pos + 1);
        } else if (mode == 2) {
            swap(tmpBlocks, zero_pos, zero_pos - dimension);
        } else if (mode == 3) {
            swap(tmpBlocks, zero_pos, zero_pos + dimension);
        }
        return new Board(tmpBlocks);
    }

    private void swap(int blocks[][], int a, int b) {
        int tmp = blocks[a / dimension][a % dimension];
        blocks[a / dimension][a % dimension] = blocks[b / dimension][b % dimension];
        blocks[b / dimension][b % dimension] = tmp;
    }
}

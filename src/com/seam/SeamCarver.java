package com.seam;

import edu.princeton.cs.algs4.Picture;


public class SeamCarver {

    private Picture pic;
    private double[][] energyM;
    private int height;
    private int width;
    private int[][] rgbMatrix;
    private Picture transposedPic = null;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        init(picture);
    }

    private void init(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException();

        this.pic = new Picture(picture);
        this.height = this.pic.height();
        this.width = this.pic.width();


        // init rgb
        rgbMatrix = new int[width()][height()];
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                rgbMatrix[i][j] = this.pic.getRGB(i, j);

        // initialize energy matrxi
        energyM = null;
    }

    private void initEnergyM() {
        energyM = new double[width()][height()];
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                energyM[i][j] = calEnergy(i, j);
    }

    // current picture
    public Picture picture() {
        return new Picture(this.pic);
    }

    // width of current picture
    public int width() {
        return this.width;
    }

    // height of current picture
    public int height() {
        return this.height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x >= width() || x < 0 || y >= height() || y < 0)
            throw new IllegalArgumentException();
        if (this.energyM == null)
            initEnergyM();
        return this.energyM[x][y];
    }

    private double calEnergy(int x, int y) {
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return 1000.0;
        // Calculate Rx
        int deltaX = delta(rgbMatrix[x + 1][y], rgbMatrix[x - 1][y]);
        int deltaY = delta(rgbMatrix[x][y + 1], rgbMatrix[x][y - 1]);
        return Math.sqrt((double) (deltaX + deltaY));
    }

    private int delta(int rgb1, int rgb2) {
        int r = ((rgb1 >> 16) & 0xFF) - ((rgb2 >> 16) & 0xFF);
        int g = ((rgb1 >> 8) & 0xFF) - ((rgb2 >> 8) & 0xFF);
        int b = (rgb1 & 0xFF) - (rgb2 & 0xFF);
        return r * r + b * b + g * g;
    }

    private void relaxe(int x, int y, double[][] distTo, int[][] edgeTo) {
        int fr, to;
        if (x == 0) fr = x;
        else fr = x - 1;
        if (x == width() - 1) to = x;
        else to = x + 1;
        for (int i = fr; i <= to; i++)
            if (distTo[i][y - 1] + this.energyM[x][y] < distTo[x][y]) {
                distTo[x][y] = distTo[i][y - 1] + this.energyM[x][y];
                edgeTo[x][y] = i;
            }
    }

    private void transpose(boolean cache) {
        Picture tmpPic;
        if (transposedPic == null || !cache) {
            tmpPic = new Picture(height(), width());
            for (int i = 0; i < width(); i++)
                for (int j = 0; j < height(); j++)
                    tmpPic.setRGB(j, i, rgbMatrix[i][j]);
        }else tmpPic = transposedPic;
        transposedPic = this.pic;
        init(tmpPic);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose(true);
        int[] seam = findVerticalSeam();
        transpose(true);
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (energyM == null)
            initEnergyM();

        double[][] distTo; // the total energy sofar
        int[][] edgeTo;

        // initialize distTo matrix
        distTo = new double[width()][height()];
        for (int i = 0; i < width(); i++)
            for (int j = 1; j < height(); j++)
                distTo[i][j] = Double.POSITIVE_INFINITY;
        for (int i = 0; i < width(); i++)
            distTo[i][0] = this.energyM[i][0];

        //initialize edgeTo matrxi
        edgeTo = new int[width()][height()];

        for (int j = 1; j < height(); j++)
            for (int i = 0; i < width(); i++)
                relaxe(i, j, distTo, edgeTo);

        double minEnergy = -1;
        int minIndex = 0;
        for (int i = 0; i < width(); i++) {
            if (minEnergy == -1) {
                minEnergy = distTo[i][height() - 1];
            } else if (distTo[i][height() - 1] < minEnergy) {
                minEnergy = distTo[i][height() - 1];
                minIndex = i;
            }
        }
        int[] seam = new int[height()];
        seam[height() - 1] = minIndex;
        for (int j = height() - 2; j >= 0; j--)
            seam[j] = edgeTo[seam[j + 1]][j + 1];
        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        seamError(seam, height(), width());
        transpose(true);
        removeVerticalSeam(seam);
        transpose(false);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        seamError(seam, width(), height());

        Picture pic = new Picture(width() - 1, height());
        for (int i = 0; i < width() - 1; i++)
            for (int j = 0; j < height(); j++)
                if (i >= seam[j]) {
                    pic.setRGB(i, j, rgbMatrix[i + 1][j]);
                } else pic.setRGB(i, j, rgbMatrix[i][j]);
        init(pic);
        this.transposedPic = null;
    }

    private void seamError(int[] seam, int range1, int range2) {
        if (range1 <= 1)
            throw new IllegalArgumentException();

        if (seam == null || seam.length != range2)
            throw new IllegalArgumentException();

        for (int i = 0; i < range2; i++) {
            if (seam[i] < 0 || seam[i] >= range1)
                throw new IllegalArgumentException();
            if (i < range2 - 1)
                if (Math.abs(seam[i + 1] - seam[i]) > 1)
                    throw new IllegalArgumentException();
        }
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

}
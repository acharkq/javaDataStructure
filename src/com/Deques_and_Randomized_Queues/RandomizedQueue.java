package com.Deques_and_Randomized_Queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int len;
    private int capacity;
    private Item[] randQueue;
    final private int minCapacity = 20;

    public RandomizedQueue() {
        len = 0;
        capacity = minCapacity;
        randQueue = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return len == 0;
    }

    public int size() {
        return len;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException();
        if (len >= capacity) {
            capacity *= 2;
            Item[] randQueueTmp = (Item[]) new Object[capacity];
            System.arraycopy(randQueue, 0, randQueueTmp, 0, len);
            randQueue = randQueueTmp;
        }
        randQueue[len++] = item;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int rand = StdRandom.uniform(len);
        Item item = randQueue[rand];
        if (rand != len - 1)
            randQueue[rand] = randQueue[len - 1];
        randQueue[len - 1] = null;
        if (--len <= capacity / 4 && capacity >= minCapacity) {
            capacity /= 2;
            Item[] randQueueTmp = (Item[]) new Object[capacity];
            System.arraycopy(randQueue, 0, randQueueTmp, 0, len);
            randQueue = randQueueTmp;
        }
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int rand = StdRandom.uniform(len);
        return randQueue[rand];
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int pos;
        final private int length = len;
        final private int[] shuffeldIndex;

        ListIterator() {
            shuffeldIndex = new int[length];
            for (int i = 0; i < length; i++)
                shuffeldIndex[i] = i;
            StdRandom.shuffle(shuffeldIndex);
            pos = 0;
        }

        public boolean hasNext() {
            return pos != len;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return randQueue[shuffeldIndex[pos++]];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 10000; i++) {
            int a = StdRandom.uniform(2);
            if (a == 0)
                rq.enqueue(i);
            else if (!rq.isEmpty())
                rq.dequeue();
        }
    }
}


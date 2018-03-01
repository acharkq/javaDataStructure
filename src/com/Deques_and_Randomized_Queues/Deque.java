package com.Deques_and_Randomized_Queues;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int len;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque() {
        first = null;
        last = null;
        len = 0;
    }

    public boolean isEmpty() {
        return len == 0;
    }

    public int size() {
        return len;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        first.prev = null;
        first.next = oldFirst;
        len++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        last.prev = oldLast;
        last.next = null;
        len++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = first.item;
        if (first.next != null)
            first.next.prev = null;
        first = first.next;
        len--;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = last.item;
        if (last.prev != null)
            last.prev.next = null;
        last = last.prev;
        len--;
        if (isEmpty()) {
            first = null;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();
        dq.addLast(0);
        dq.removeLast();
        for(Integer d:dq)
            System.out.println(d);
    }
}

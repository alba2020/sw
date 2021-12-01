import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[0];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Trying to add a null item");

        if (n == items.length)
            resize(n == 0 ? 1 : 2 * items.length);
        items[n++] = item;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("queue is empty");
        int i = StdRandom.uniform(n);
        Item item = items[i];
        items[i] = items[--n];
        items[n] = null; // no loitering
        if (n > 0 && n == items.length / 4)
            resize(items.length / 2);
        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("queue is empty");
        return items[StdRandom.uniform(n)];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = items[i];
        items = copy;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private final int[] keys;

        public ArrayIterator() {
            keys = new int[n];
            for (int j = 0; j < n; j++) {
                keys[j] = j;
            }
            StdRandom.shuffle(keys);
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() not supported");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("no more items");
            return items[keys[i++]];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(q.dequeue());
            else if (s.equals("*"))
                for (String el : q)
                    StdOut.print(el + " ");
            else
                q.enqueue(s);
        }
    }
}

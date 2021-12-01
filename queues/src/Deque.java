import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first, last;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque() {
        size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        // return the number of items on the deque
        // store size in a variable => t ~ O(const) ?
        return size;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Trying to add a null item");
        Node oldfirst = first;
        first = new Node();

        first.item = item;
        first.next = oldfirst;
        first.prev = null;

        if (last == null) // list was empty
            last = first; // now 1 item
        else
            oldfirst.prev = first;

        size++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Trying to add a null item");
        Node oldlast = last;
        last = new Node();

        last.item = item;
        last.next = null;
        last.prev = oldlast;

        if (first == null) // was empty
            first = last; // now 1 item
        else
            oldlast.next = last;

        size++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("trying to delete from empty deque");
        Item item = first.item;
        first = first.next;
        if (first == null)
            last = null; // we're empty
        else
            first.prev = null;

        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("trying to delete from empty deque");
        Item item = last.item;
        last = last.prev;
        if (last == null)
            first = null; // empty list
        else
            last.next = null;

        size--;
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
            throw new UnsupportedOperationException("remove() not supported");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more items");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(d.removeLast());
            else if (s.equals("f"))
                StdOut.print(d.removeFirst());
            else if (s.equals("*"))
                for (String el : d)
                    StdOut.println("<" + el + ">");
            else if (s.equals("s"))
                StdOut.println("size = " + d.size());
            else
                d.addLast(s);
        }
    }
}

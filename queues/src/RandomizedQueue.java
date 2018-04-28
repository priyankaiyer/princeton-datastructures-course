import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size = 0;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (null == item) {
            throw new IllegalArgumentException("Cannot call enqueue with null");
        }
        if (size == items.length) {
            resize(size * 2);
        }
        items[size++] = item;
    }

    private void resize(int newLength) {
        Item[] newArray = copyArray(newLength);
        this.items = newArray;
    }

    private Item[] copyArray(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = items[i];
        }
        return newArray;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Randomized queue is empty");
        }
        int randomNumber = StdRandom.uniform(size);
        Item randomItemToBeRemoved = items[randomNumber];
        if (randomNumber != size - 1) {
            items[randomNumber] = items[size - 1];
        }
        items[--size] = null;
        if (size > 1 && size == items.length / 4) {
            resize(items.length / 2);
        }

        return randomItemToBeRemoved;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Randomized queue is empty");
        }
        int uniform = StdRandom.uniform(size);
        return items[uniform];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] iterableItems;
        private int current = size;

        public RandomizedQueueIterator() {
            iterableItems = copyArray(size);
        }

        @Override
        public boolean hasNext() {
            return current > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items in randomizedqueue");
            }
            int randomNumber = StdRandom.uniform(current);
            Item randomItemToBeRemoved = iterableItems[randomNumber];
            if (randomNumber != current - 1) {
                iterableItems[randomNumber] = iterableItems[current - 1];
            }
            iterableItems[--current] = null;
            return randomItemToBeRemoved;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This operation is not currently supported");
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> integers = new RandomizedQueue<>();
        integers.enqueue(1);
        integers.enqueue(2);
        integers.enqueue(3);
        integers.enqueue(4);
        integers.enqueue(5);
        integers.enqueue(6);
        integers.enqueue(7);
        integers.enqueue(8);
        integers.enqueue(9);
        integers.enqueue(10);
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
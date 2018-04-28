import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size;

    private class Node {
        Item value;
        Node next;
        Node prev;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("No more items in deque");
            }
            Item item = current.value;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This operation is not currently supported");
        }
    }

    public Deque() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (null == item) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node currentFirst = first;
        first = new Node();
        first.value = item;
        first.next = currentFirst;
        if (currentFirst == null) {
            last = first;
        } else {
            currentFirst.prev = first;
        }
        size++;
    }

    public void addLast(Item item) {
        if (null == item) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node currentLast = last;
        last = new Node();
        last.value = item;
        if (currentLast != null) {
            last.prev = currentLast;
            currentLast.next = last;
        }
        if (first == null) {
            first = last;
        }
        last.next = null;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node currentFirst = this.first;
        Node nextFirst = currentFirst.next;
        size--;
        if (nextFirst != null) {
            nextFirst.prev = null;
            this.first = nextFirst;
        } else {
            this.first = null;
            this.last = null;
        }

        return currentFirst.value;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node currentLast = this.last;
        Node newLast = currentLast.prev;
        size--;
        if (newLast != null) {
            this.last = null;
            newLast.next = null;
            this.last = newLast;
        } else {
            this.last = null;
            this.first = null;
        }
        return currentLast.value;

    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> integers = new Deque<>();
        integers.addFirst(0);
        integers.addFirst(-1);
        integers.addLast(1);
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }


}

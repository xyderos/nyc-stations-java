import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item>{

    private Node<Item> head;

    public void add(Item item) {

        if (head == null)head = new Node<>(item);

        else head = new Node<>(item, head);

    }

    public boolean isEmpty() {

        return head == null;

    }

    @Override
    public Iterator<Item> iterator() {

        return new BagIterator<>(head);

    }

    public boolean contains(Item item){

        for (Item value : this) if (value.equals(item)) return true;

        return false;

    }

    private static class Node<Item> {

        private Item item;

        private Node<Item> next;

        public Node(Item item) {

            this.item = item;

        }

        public Node(Item item, Node<Item> next) {

            this.item = item;

            this.next = next;

        }

    }


    private static class BagIterator<Item> implements Iterator<Item> {

        private Node<Item> current;

        public BagIterator(Node<Item> current) {

            this.current = current;

        }

        @Override
        public boolean hasNext() {

            return current != null;

        }

        @Override
        public Item next() {

            if (current == null) throw new NoSuchElementException();

            Item res = current.item;

            current = current.next;

            return res;

        }
    }
}

import java.util.NoSuchElementException;

// a simple index table that holds the item index pair
public class IndexTable<Item> {

    private int nofItems;

    private int currIndex;

    private Bag<ItemIndex<Item>>[] table;

    private Item[] indexes;

    public IndexTable(int nofItems) {

        this.table = (Bag<ItemIndex<Item>>[]) new Bag[nofItems];

        this.indexes = (Item[]) new Object[nofItems];

        this.nofItems = nofItems;

    }

    public int add(Item item) {

        int key = hash(item);

        if (table[key] == null) {

            if (currIndex > nofItems) throw new IndexOutOfBoundsException();

            table[key] = new Bag<>();

        }

        for (ItemIndex<Item> next : table[key]) if (next.item.equals(item)) return next.index;

        if (currIndex > nofItems) throw new IndexOutOfBoundsException();

        table[key].add(new ItemIndex(item, currIndex));

        indexes[currIndex] = item;

        return currIndex++;

    }

    public int getIndex(Item item) {

        int key = hash(item);

        if (table[key] == null) throw new NoSuchElementException(String.format("Item %snot found", item));

        for (ItemIndex<Item> next : table[key])if (next.item.equals(item))return next.index;

        throw new NoSuchElementException(String.format("Item %s not found", item));
    }

    public Item getItem(int index) {

        if (index >= table.length) throw new NoSuchElementException(String.format("No item on index %d", index));

        return indexes[index];
    }

    private int hash(Item item) {

        return (item.hashCode() & 0x7fffffff) % nofItems;
    }

    private static class ItemIndex<Item> {

        private Item item;

        private int index;

        public ItemIndex(Item item, int index) {

            this.item = item;

            this.index = index;

        }
    }
}

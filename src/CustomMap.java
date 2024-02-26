import java.util.Arrays;
public class CustomMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry<K, V>[] table;
    private int size;

    public CustomMap() {
        this.table = new Entry[DEFAULT_CAPACITY];
        this.size = 0;
    }
    public Entry<K, V>[] getTable() {
        return Arrays.copyOf(table, table.length);
    }

    public void put(K key, V value) {
        int index = getIndex(key);
        Entry<K, V> entry = table[index];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
            entry = entry.getNext();
        }

        addEntry(index, key, value);
    }

    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = table[index];

        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
            entry = entry.getNext();
        }

        return null;
    }

    public void remove(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = table[index];
        Entry<K, V> prev = null;

        while (entry != null) {
            if (entry.getKey().equals(key)) {
                if (prev == null) {
                    table[index] = entry.getNext();
                } else {
                    prev.setNext(entry.getNext());
                }
                size--;
                return;
            }
            prev = entry;
            entry = entry.getNext();
        }
    }

    public int size() {
        return size;
    }

    private int getIndex(K key) {
        int hash = key.hashCode();
        return (hash & 0x7FFFFFFF) % table.length;
    }

    private void addEntry(int index, K key, V value) {
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.setNext(table[index]);
        table[index] = newEntry;
        size++;

        if (size > table.length * LOAD_FACTOR) {
            resizeTable();
        }
    }

    private void resizeTable() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[table.length * 2];
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            while (entry != null) {
                put(entry.getKey(), entry.getValue());
                entry = entry.getNext();
            }
        }
    }


    public static class Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public void setNext(Entry<K, V> next) {
            this.next = next;
        }
    }
}

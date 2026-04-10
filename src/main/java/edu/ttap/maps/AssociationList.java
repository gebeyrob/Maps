package edu.ttap.maps;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An association list is an implementation of a map via a list of key-value
 * pairs.
 */
public class AssociationList<K, V> implements Map<K, V> {

    private class Pair<T, U> {
        public T key;
        public U value;

        public Pair(T key, U value) {
            this.key = key;
            this.value = value;
        }
    }

    private ArrayList<Pair<K, V>> list = new ArrayList<>();

    /**
     * Clears the association list, removing all key-value pairs.
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * @param key the key to check
     * @return true iff this map contains a mapping for the specified key
     */
    @Override
    public boolean containsKey(Object key) {
        for (Pair<K, V> i : list) {
            if (i.key.equals(key)) { // might run into null
                return true;
            }
        }
        return false;
    }

    /**
     * @param value the value to check
     * @return true iff this map maps one or more keys to the specified value
     */
    @Override
    public boolean containsValue(Object value) {
        for (Pair<K, V> i : list) {
            if (i.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return a set view of the mappings contained in this map
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        // NOTE: you do not need to implement this method!
        throw new UnsupportedOperationException("Unimplemented method 'entrySet'");
    }

    /**
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this
     *         map contains no mapping for the key
     */
    @Override
    public V get(Object key) {
        for (Pair<K, V> i : list) {
            if (i.key.equals(key)) {
                return i.value;
            }
        }
        return null;
    }

    /**
     * @return true if this map contains no key-value mappings
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * @return a set view of the keys contained in this map
     */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Pair<K, V> i : list) {
            keys.add(i.key);
        }
        return keys;
    }

    /**
     * If there is no entry for key in the map, updates the entry to associate key
     * with value. Otherwise, it updates the entry for key accordingly.
     * 
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    @Override
    public V put(K key, V value) {
        if (containsKey(key)) {
            for (Pair<K, V> i : list) {
                if (i.key.equals(key)) {
                    V oldValue = i.value;
                    i.value = value;
                    return oldValue;
                }
            }
        }
        list.add(new Pair<>(key, value));
        return null;
    }

    /**
     * Copies all of the mappings from the specified map to this map. The effect of
     * this
     * operation is equivalent to applying the put(K, V) operation to each entry in
     * the
     * specified map.
     * 
     * @param m the map whose mappings are to be copied to this map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(Entry<? extends K, ? extends V> entry: m.entrySet()){
            put(entry.getKey(),entry.getValue());
        }
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * 
     * @param key the key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     *         mapping for
     *         key.
     */
    @Override
    public V remove(Object key) {
       if (containsKey(key)) {
            for (Pair<K, V> i : list) {
                if (i.key.equals(key)) {
                    V oldValue = i.value;
                    list.remove(i);
                    return oldValue;
                }
            }
        }
        return null;
    }

    /**
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * @return a collection of the values contained in this map, e.g., a list
     */
    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Pair<K, V> i : list) {
            values.add(i.value);
        }
        return values;
    }
}

import javax.management.ValueExp;
import java.util.*;
import java.util.ArrayList;

/**
 * This class is implemented by a hashtable that stores a list of values
 * associated with each unique key.  These lists of values are sorted
 * according to the compareTo() defined within the ValueType.
 */
public class HashTableSortedSets <KeyType, ValueType extends Comparable<ValueType>>
        extends HashtableMap<KeyType, List<ValueType>> implements IHashTableSortedSets<KeyType, ValueType> {

    // instance fields
    private int size;

    // hashtable constructor
    public HashTableSortedSets() {
        super();
    }

    // hashtable constructor with capacity parameter
    public HashTableSortedSets(int capacity) {
        super(capacity);
    }

    /**
     * This add method is different from the put() method in that it appends a
     * single value to the end of the list associated with a given key.  If a
     * key does not yet have a list of values associated with it, then a new
     * one will be created when this method is called.
     *
     * @param key   used to later look up the list containing this value
     * @param value associated with the previous key
     */

    public void add(KeyType key, ValueType value) {
        // create linked list of BothTypes
        ArrayList<ValueType> listShows = new ArrayList<>();

        // search for index
        int index = Math.abs(key.hashCode()) % this.items.length;

        // check if the array has a linked list or not
        if(this.items[index] == null){
            this.items[index] = new LinkedList<>();
        }

        if (!this.containsKey(key)){
            BothTypes<KeyType, List<ValueType>> toBeAdded = new BothTypes(key, listShows);
            this.items[index].add(toBeAdded);
        }


        for (int i = 0; i < this.items[index].size(); i++){
            if(this.items[index].get(i).key.equals(key)){
                this.items[index].get(i).value.add(value);
                this.capacity++;
                Collections.sort(this.items[index].get(i).value);
            }
        }
        if ( (((float) capacity) / this.items.length) > 0.75 || (((float) capacity)  / this.items.length) == 0.75){
            regrow();
        }


}}

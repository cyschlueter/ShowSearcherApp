// --== CS400 Project One File Header ==--
// Name: Trevor Johnson
// CSL Username: trevorj
// Email: tmjohnson32@wisc.edu
// Lecture #: 002
// Notes to Grader: <any optional extra notes to your grader>
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @param <KeyType>
 * @param <ValueType>
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>{

    /**
     * The single dimension array storing linkedlist with BothTypes
     */
    protected LinkedList<BothTypes<KeyType, ValueType>>[] items;
    /**
     * capacity tracks the number of key-value paris in the hashtable
     */
    protected int capacity;

    /**
     * @param size - the intended size of the array
     */
    public HashtableMap (int size){
        // initializes the array with raw type
        LinkedList[] retArr = new LinkedList[size];
        // downcast to the generic bounded array, might throw exceptions
        this.items = ((LinkedList<BothTypes<KeyType, ValueType>>[]) retArr);
    }


    /**
     * default constructor initializes an array with length 20
     */
    public HashtableMap(){
        this(20);
    }

    /**
     * put the key-value BothTypess by hashing
     * @return true if successful, false otherwise
     */
    @Override
    public boolean put(KeyType key, ValueType value) {
        // return false if the key is null
        if (key == null){
            return false;
        }
        // hashing value = absolute of the hashCode of the key mod the size of the current hashtable
        int hashCode = Math.abs(key.hashCode()) % this.items.length;
        BothTypes<KeyType, ValueType> BothTypes = new BothTypes<>(key, value);
        // insert the BothTypes into the hashtable
        if (this.items[hashCode] == null){
            LinkedList<BothTypes<KeyType, ValueType>> linkedList = new LinkedList<>();
            linkedList.push(BothTypes);
            this.items[hashCode] = linkedList;
            this.capacity++;
            // regrow if the load factor => 0.75
            if ( (((float) capacity) / this.items.length) > 0.75 || (((float) capacity)  / this.items.length) == 0.75){
                regrow();
            }
        }
        else{
            for(int i = 0; i < this.items[hashCode].size(); i++){
                if(this.items[hashCode].get(i).getKey().equals(key)){
                    return false;
                }
            }
            this.items[hashCode].push(BothTypes);
            this.capacity++;
            if ( (((float) capacity) / this.items.length) > 0.75 || (((float) capacity)  / this.items.length) == 0.75){
                regrow();
            }
        }
        return true;
    }

    /**
     * get the specified value based on the key provided
     * @return the desired value of ValueType
     * @throws NoSuchElementException if the key is null or not found in the hashtable
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        if (key == null){
            throw new NoSuchElementException("Provide a valid key, not null");
        }
        int hashCode = Math.abs(key.hashCode()) % this.items.length;
        // throw NSEE if the key is null or not in the table
        if (this.items[hashCode] == null){
            throw new NoSuchElementException("No such element in the table");
        }
        // loop through the table to extract and return the desired value
        for(int i = 0; i < this.items[hashCode].size(); i++){
            if(this.items[hashCode].get(i).getKey().equals(key)){
                return this.items[hashCode].get(i).getValue();
            }
        }
        // not found
        throw new NoSuchElementException("No such element in table");
    }

    /**
     * @return the current capacity of the hashtable
     */
    @Override
    public int size() {
        return this.capacity;
    }

    /**
     * @return true if the key is present in the hashtable, false if otherwise
     */
    @Override
    public boolean containsKey(KeyType key) {
        try {
            get(key);
            return true;
        }
        catch(NoSuchElementException NSEE){
            return false;
        }
    }

    /**
     * remove the BothTypes based on the key provided
     * @return the removed Value
     */
    @Override
    public ValueType remove(KeyType key) {
        if(key == null) return null;
        int hashCode = Math.abs(key.hashCode()) % this.items.length;
        // removes by cycling the linkedlist
        if(this.items[hashCode] != null) {
            for (int i = 0; i < this.items[hashCode].size(); i++) {
                // check if the ith item in the linkedlist is the desired
                if (this.items[hashCode].get(i).getKey().equals(key)) {
                    // store the value for returning
                    ValueType retval = this.items[hashCode].get(i).getValue();
                    // remove it
                    this.items[hashCode].remove(i);
                    return retval;
                }
            }
        }
        return null;
    }

    /**
     * clear the entire hashtable
     */
    @Override
    public void clear() {
        // clear everything with loop and setting every element in the array to null
        for(int i = 0; i < this.items.length; i++){
            this.items[i] = null;
        }
        this.capacity = 0;
    }

    /**
     * private helper method to regrow the hashtable to twice its original size and rehashing
     */
    protected void regrow(){
        // create new hashtable with twice the original size
        HashtableMap<KeyType, ValueType> copy = new HashtableMap<>(this.items.length * 2);
        // rehash everything with loop
        for (LinkedList<BothTypes<KeyType, ValueType>> item : this.items) {
            if (item != null) {
                while (item.size() != 0) {
                    copy.put(item.peekFirst().getKey(), item.peekFirst().getValue());
                    item.pop();
                }
            }
        }
        // assign the reference of the original map with the one from the copy map with identical contents but twice the size
        this.items = copy.items;
    }


      // A toString method I have written to test things visually, pls disregard
          public String toString() {
              String retVal = "";
              for (int i = 0; i < 20; i++) {
                  if (this.items[i] != null) {
                      retVal += this.items[i].toString() + "\n";
                  }
                  else {
                      retVal += "index " + i + " null" + "\n";
                  }
              }
              return retVal;
          }

}
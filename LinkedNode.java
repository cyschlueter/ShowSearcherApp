// --== CS400 Project One File Header ==--
// Name: Trevor Johnson
// CSL Username: trevorj
// Email: tmjohnson32@wisc.edu
// Lecture #: 002
// Notes to Grader: <any optional extra notes to your grader>
public class LinkedNode<KeyType, ValueType> {

    private final KeyType key;
    private ValueType value;
    private LinkedNode<KeyType, ValueType> next;

    /**
     * Creates an instance of LinkedNode with given item and reference to next
     * node
     *
     * @param key   value
     * @param value data
     */
    public LinkedNode(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }

    public LinkedNode(KeyType key, ValueType value, LinkedNode<KeyType, ValueType>
            next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    /**
     * Accessor for data field
     *
     * @return the data within this linked node
     */
    public ValueType getValue() {
        return value;
    }

    /**
     * Setter of the item
     *
     * @param value the data to set
     */
    public void setValue(ValueType value) {
        this.value = value;
    }

    public KeyType getKey() {
        return this.key;
    }
    /**
     * Accessor for next field
     *
     * @return the next
     */
    public LinkedNode<KeyType, ValueType> getNext() {
        return next;
    }
    /**
     * Setter for next field
     *
     * @param next the next to set
     */
    public void setNext(LinkedNode<KeyType, ValueType> next) {
        this.next = next;
    }
}
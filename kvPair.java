
/**
 * This class allows me to store key-value pairs within a single object, so that I can access the key of a certain
 * value without looking in the hash table
 *
 */
public class kvPair<KeyType, ValueType> {
	private KeyType key;
	private ValueType value;
	
	
     public kvPair(KeyType key, ValueType value) {
    	 this.key = key;
    	 this.value = value;
     }
     
     protected ValueType getValue() {
    	 return this.value;
     }
     
     protected KeyType getKey() {
    	 return this.key;
     }
     
}

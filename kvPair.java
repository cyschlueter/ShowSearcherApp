// --== CS400 Project One File Header ==--
// Name: Cy Schlueter
// CSL Username: cschlueter (or it really might be cy I am not sure)
// Email: cschlueter@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader: Thank you
/**
 * This class allows me to store key-value pairs within a single object, so that I can access the key of a certain
 * value without looking in the hash table
 * @author Cy Schlueter
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

// --== CS400 Project One File Header ==--
// Name: Trevor Johnson
// CSL Username: trevorj
// Email: tmjohnson32@wisc.edu
// Lecture #: 002
// Notes to Grader: <any optional extra notes to your grader>
public class BothTypes<KeyType, ValueType> {

    protected KeyType key;
    protected ValueType value;

    public BothTypes(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }

    public KeyType getKey() {
        return this.key;
    }

    public ValueType getValue() {
        return this.value;
    }
}

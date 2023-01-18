
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

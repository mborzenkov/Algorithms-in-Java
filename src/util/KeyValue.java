package util;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

/** Mutable pair of key and value. */
public class KeyValue {

    /** Key, integer. */
    private @NotNull final Integer key;
    /** Value, any object. */
    private @NotNull Object value;

    /** Creates new key-value pair.
     *
     * @param key integer, not null
     * @param value any object, not null
     *
     * @throws IllegalArgumentException if key or value is null
     */
    public KeyValue(@NotNull Integer key, @NotNull Object value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Can't create KeyValue :: key or value is null");
        }
        this.key = key;
        this.value = value;
    }

    /** Returns key. */
    public @NotNull Integer getKey() {
        return key;
    }

    /** Returns value. */
    public @NotNull Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyValue keyValue = (KeyValue) o;
        return Objects.equals(key, keyValue.key)
                && Objects.equals(value, keyValue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override

    public String toString() {
        return String.format(
                "KeyValue (key=%s, value=%s)", this.key, this.value);
    }

}
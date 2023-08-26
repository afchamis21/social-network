package andre.chamis.socialnetwork.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * An abstract class representing an in-memory cache.
 *
 * @param <KeyType>   The type of the keys used in the cache.
 * @param <ValueType> The type of the values stored in the cache.
 */
public abstract class InMemoryCache<KeyType, ValueType> {
    private final Map<KeyType, ValueType> cache = new HashMap<>();

    /**
     * Retrieves the underlying cache map.
     *
     * @return The cache map containing the stored key-value pairs.
     */
    protected synchronized Map<KeyType, ValueType> getCache(){
        return cache;
    }

    /**
     * Adds a key-value pair to the cache.
     *
     * @param key   The key associated with the value.
     * @param value The value to be stored in the cache.
     */
    public synchronized void put(KeyType key, ValueType value){
        cache.put(key, value);
    }

    /**
     * Checks if the cache contains a specific key.
     *
     * @param key The key to check for existence in the cache.
     * @return {@code true} if the key is found in the cache, otherwise {@code false}.
     */
    public synchronized boolean containsKey(KeyType key){
        return cache.containsKey(key);
    }

    /**
     * Removes a key-value pair from the cache.
     *
     * @param key The key of the value to be removed from the cache.
     */
    public synchronized void remove(KeyType key){
        cache.remove(key);
    }

    /**
     * Retrieves a value from the cache based on the provided key.
     *
     * @param key The key of the value to retrieve.
     * @return An {@link Optional} containing the value if found, or empty if not found.
     */
    public synchronized Optional<ValueType> get(KeyType key){
        ValueType value = cache.get(key);

        return Optional.ofNullable(value);
    }

    /**
     * Initializes the cache with a list of values and a key extractor function.
     * <p>
     * This method clears the existing cache and then populates it with the provided values.
     *
     * @param values       The list of values to populate the cache with.
     * @param keyExtractor A function that extracts keys from the values.
     */
    public synchronized void initializeCache(List<ValueType> values, Function<ValueType, KeyType> keyExtractor){
        cache.clear();

        addMultiple(values, keyExtractor);
    }

    /**
     * Adds multiple values to the cache using a key extractor function.
     *
     * @param values       The list of values to add to the cache.
     * @param keyExtractor A function that extracts keys from the values.
     */
    public synchronized void addMultiple(List<ValueType> values, Function<ValueType, KeyType> keyExtractor){
        for (ValueType value: values){
            KeyType key = keyExtractor.apply(value);
            put(key, value);
        }
    }

    /**
     * Deletes multiple values from the cache using a key extractor function.
     *
     * @param values       The list of values to delete from the cache.
     * @param keyExtractor A function that extracts keys from the values.
     */
    public synchronized void deleteFromList(List<ValueType> values, Function<ValueType, KeyType> keyExtractor) {
        for (ValueType value: values){
            KeyType key = keyExtractor.apply(value);
            remove(key);
        }
    }

    /**
     * Retrieves the size of the cache.
     *
     * @return The number of key-value pairs in the cache.
     */
    public synchronized int getSize() {
        return cache.size();
    }
}

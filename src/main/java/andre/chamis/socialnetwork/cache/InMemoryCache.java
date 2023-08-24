package andre.chamis.socialnetwork.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class InMemoryCache<KeyType, ValueType> {
    private final Map<KeyType, ValueType> cache = new HashMap<>();
    protected synchronized Map<KeyType, ValueType> getCache(){
        return cache;
    }

    public synchronized void put(KeyType key, ValueType value){
        cache.put(key, value);
    }

    public synchronized boolean containsKey(KeyType key){
        return cache.containsKey(key);
    }

    public synchronized void remove(KeyType key){
        cache.remove(key);
    }

    public synchronized Optional<ValueType> get(KeyType key){
        ValueType value = cache.get(key);

        return Optional.ofNullable(value);
    }

    public synchronized void initializeCache(List<ValueType> values, Function<ValueType, KeyType> keyExtractor){
        cache.clear();

        addMultiple(values, keyExtractor);
    }

    public synchronized void addMultiple(List<ValueType> values, Function<ValueType, KeyType> keyExtractor){
        for (ValueType value: values){
            KeyType key = keyExtractor.apply(value);
            put(key, value);
        }
    }

    public synchronized void deleteFromList(List<ValueType> values, Function<ValueType, KeyType> keyExtractor) {
        for (ValueType value: values){
            KeyType key = keyExtractor.apply(value);
            remove(key);
        }
    }
}

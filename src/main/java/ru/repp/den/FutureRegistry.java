package ru.repp.den;

import org.springframework.stereotype.Component;
import ru.repp.den.dto.People;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Component
public class FutureRegistry {
    private final ConcurrentHashMap<String, Future<List<People>>> registry = new ConcurrentHashMap<>();

    public String putAndGetKey(Future<List<People>> future) {
        String key = UUID.randomUUID().toString();
        registry.put(key, future);
        return key;
    }

    public Future<List<People>> get(String key) {
        return registry.get(key);
    }

}

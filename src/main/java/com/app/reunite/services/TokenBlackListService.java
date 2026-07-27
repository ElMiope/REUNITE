package com.app.reunite.services;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlackListService {
    private final Map<String,Long> tokens = new ConcurrentHashMap<>();

    public void addToBlacklist(String token, long expirationTimeInMs) {
        tokens.put(token, expirationTimeInMs);

        cleanExpiredTokens();
    }

    public boolean isBlackListed(String token) {
        if (!tokens.containsKey(token)) {
            return false;
        }

        long expiration = tokens.get(token);

        if (System.currentTimeMillis() > expiration) {
            tokens.remove(token);
            return false;
        }

        return true;
    }

    private void cleanExpiredTokens() {
        long now = System.currentTimeMillis();
        tokens.entrySet().removeIf(entry -> now > entry.getValue());
    }
}

package com.codeit.cache.config;

import com.codeit.cache.dto.post.PostSearchRequest;
import org.springframework.stereotype.Component;

@Component("keyGen")
public class CacheKeyGenerator {
    public String hashKey(Object req) {
        return Integer.toHexString(req.hashCode());

    }
}
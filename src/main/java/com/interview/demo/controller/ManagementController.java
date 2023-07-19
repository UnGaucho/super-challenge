package com.interview.demo.controller;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagementController {

    private final CacheManager cacheManager;

    public ManagementController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @PutMapping(path = "/cache/{cacheName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> put(@PathVariable String cacheName, @RequestParam String key, @RequestParam String val) {
        if (!cacheManager.getCacheNames().contains(cacheName)) {
            return ResponseEntity.notFound().build();
        }

        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return ResponseEntity.internalServerError().build();
        }

        cache.put(key, val);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(path = "/cache/{cacheName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> invalidateCaches(@PathVariable String cacheName) {
        if (!cacheManager.getCacheNames().contains(cacheName)) {
            return ResponseEntity.notFound().build();
        }

        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return ResponseEntity.internalServerError().build();
        }

        cache.clear();
        return ResponseEntity.ok().build();
    }
}

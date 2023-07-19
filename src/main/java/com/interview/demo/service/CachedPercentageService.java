package com.interview.demo.service;

import com.interview.demo.exceptions.ServiceUnavailableException;
import org.springframework.cache.Cache;
import org.springframework.scheduling.annotation.Scheduled;

public class CachedPercentageService implements PercentageService {

    private final Cache cache;
    private static final String CACHE_KEY = "per";
    private PercentageService remote;

    public CachedPercentageService(Cache cache, PercentageService remote) {
        this.cache = cache;
        this.remote = remote;
    }


    /**
     * Lazy loads the cache with a cache-aside strategy.
     * Considerations: If this were a real scenario the best strategy is usually a write-through at the writer service
     * side (the holder of percentage) as it is mentioned that there's many consumers for a relatively unchanging
     * single key value. This key should also have a TTL to guarantee a bounded inconsistency should it happen.
     *
     * Other strategies are registering to a queue on the consumer side to invalidate the cache but this has many issues
     * on a large enough scale, some of them to name a few is the added complexity of cache consistency, having to
     * maintain a list of consumers and the extra messaging layer.
     * @return the percentage
     */
    @Override
    public double getPercentage() {

        Double cached = getFromCache();
        if (cached != null) {
            return cached;
        } else {
            Double fromRemote = this.remote.getPercentage();
            if (fromRemote != null) {
                cache.putIfAbsent(CACHE_KEY, fromRemote);
                return fromRemote;
            }
        }
        throw new ServiceUnavailableException("Unable to retrieve percentage");
    }

    private Double getFromCache() {
        return cache.get(CACHE_KEY, Double.class);
    }
}

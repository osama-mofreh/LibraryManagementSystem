package com.LMS.LibraryManagementSystem.configs;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("booksCache", "patronsCache", "borrowingRecords", "allBooks", "allPatrons", "books", "patrons", "availableBooks");
    }
}


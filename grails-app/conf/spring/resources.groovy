import org.springframework.cache.ehcache.EhCacheFactoryBean

// Place your Spring DSL code here
beans = {

    runningRecordsCache(EhCacheFactoryBean) { bean ->
        cacheManager = ref("springcacheCacheManager")
        cacheName = "runningRecordsCache"
        // these are just examples of properties you could set
        eternal = false
        diskPersistent = false
        memoryStoreEvictionPolicy = "LRU"
        timeToIdle = 3600
        timeToLive = 64000
    }

    triathlonRecordsCache(EhCacheFactoryBean) { bean ->
        cacheManager = ref("springcacheCacheManager")
        cacheName = "triathlonRecordsCache"
        // these are just examples of properties you could set
        eternal = false
        diskPersistent = false
        memoryStoreEvictionPolicy = "LRU"
        timeToIdle = 3600
        timeToLive = 64000
    }
}

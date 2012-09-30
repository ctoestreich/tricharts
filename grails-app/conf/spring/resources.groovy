import org.springframework.cache.ehcache.EhCacheFactoryBean
import com.tgid.tri.cache.AuthenticationAwareKeyGenerator
import com.tgid.tri.auth.SpringAuthenticationEventListener
import com.tgid.tri.auth.SpringAuthenticationFailureEventListener

// Place your Spring DSL code here
beans = {

    springAuthenticationEventListener(SpringAuthenticationEventListener)

    springAuthenticationFailureEventListener(SpringAuthenticationFailureEventListener)

    authenticationAwareKeyGenerator(AuthenticationAwareKeyGenerator)

    runningRecordsCache(EhCacheFactoryBean) { bean ->
        cacheManager = ref("springcacheCacheManager")
        cacheName = "runningRecordsCache"
        // these are just examples of properties you could set
        eternal = false
        diskPersistent = false
        memoryStoreEvictionPolicy = "LRU"
        timeToIdle = 600
        timeToLive = 64000
    }

    triathlonRecordsCache(EhCacheFactoryBean) { bean ->
        cacheManager = ref("springcacheCacheManager")
        cacheName = "triathlonRecordsCache"
        // these are just examples of properties you could set
        eternal = false
        diskPersistent = false
        memoryStoreEvictionPolicy = "LRU"
        timeToIdle = 600
        timeToLive = 64000
    }

    chartCache(EhCacheFactoryBean) { bean ->
        cacheManager = ref("springcacheCacheManager")
        cacheName = "chartCache"
        // these are just examples of properties you could set
        eternal = false
        diskPersistent = false
        memoryStoreEvictionPolicy = "LRU"
        timeToIdle = 120
        timeToLive = 3600
    }

    siteCache(EhCacheFactoryBean){bean->
        cacheManager = ref("springcacheCacheManager")
        cacheName = "siteCache"
        // these are just examples of properties you could set
        eternal = true
        diskPersistent = false
        memoryStoreEvictionPolicy = "LRU"
        timeToIdle = 64000
        timeToLive = 64000
    }
}

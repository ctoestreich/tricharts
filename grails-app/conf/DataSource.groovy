dataSource {
    pooled = true
}
hibernate {
//    dialect = "org.hibernate.dialect.MySQLInnoDBDialect"
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            jndi = "java:comp/env/jdbc/trichartsDB"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
            pooled = true
            dbCreate = "update"
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
            jndi = "java:comp/env/jdbc/trichartsDB"
        }
    }
}

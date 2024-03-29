dataSource {
    pooled = true
}
hibernate {
//    dialect = org.hibernate.dialect.MySQLInnoDBDialect
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost:3306/tricharts?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true"
            username = "tricharts"
            password = 'Tr1Ch4rt5'
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
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://tricharts.cryau4uda2kb.us-east-1.rds.amazonaws.com:3306/tricharts?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true"
            username = "admin"
            password = '!Chris$4'
            properties {
                maxActive = 100
                maxIdle = 25
                minIdle = 5
                initialSize = 10
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
                maxWait = 10000
            }
        }
    }
}

package com.firstAPI.firstAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement

public class Configg {

    @Bean
    public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory)
    {
        return new MongoTransactionManager(dbFactory);
    }
}

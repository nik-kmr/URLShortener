package com.urlshortener.module;

import com.google.inject.AbstractModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataBaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataSource.class).toInstance(createHikariDataSource());
    }

    private DataSource createHikariDataSource() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getenv("JDBC_URL"));
        config.setUsername(System.getenv("DB_USERNAME"));
        config.setPassword(System.getenv("DB_PASSWORD"));
        return new HikariDataSource(config);
    }
}

package com.urlshortener.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.urlshortener.handler.RedirectURLHandler;
import com.urlshortener.handler.ShortenUrlHandler;
import com.urlshortener.helper.DatabaseHelper;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

import javax.sql.DataSource;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public Javalin provideJavalin() {
        return Javalin.create(config -> config.jsonMapper(new JavalinJackson()));
    }

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper(final DataSource dataSource) {
        return new DatabaseHelper(dataSource);
    }

    @Provides
    @Singleton
    public ShortenUrlHandler provideShortenUrlHandler(final DatabaseHelper databaseHelper) {
        return new ShortenUrlHandler(databaseHelper);
    }

    @Provides
    @Singleton
    public RedirectURLHandler provideRedirectURLHandler(final DatabaseHelper databaseHelper) {
        return new RedirectURLHandler(databaseHelper);
    }
}

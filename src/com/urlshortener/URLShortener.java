package com.urlshortener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import io.javalin.Javalin;
import com.urlshortener.module.DataBaseModule;
import com.urlshortener.module.ServiceModule;
import org.apache.logging.log4j.LogManager;

public class URLShortener {
    public static void main(String[] args) throws Exception {
        Injector injector;
        try {
            injector = Guice.createInjector(
                    Stage.PRODUCTION,
                    new DataBaseModule(),
                    new ServiceModule()
            );
        } catch (Exception e) {
            System.exit(1);
            return;
        }

        final Route route = injector.getInstance(Route.class);
        route.setupRoutes();

        final Javalin server = injector.getInstance(Javalin.class);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop();
            LogManager.shutdown();
        }));

        server.start(8080);
        // Keeps JVM running
        Thread.currentThread().join();
    }
}

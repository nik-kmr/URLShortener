package com.urlshortener;

import com.google.inject.Inject;
import com.urlshortener.constants.Constants;
import com.urlshortener.handler.RedirectURLHandler;
import com.urlshortener.handler.ShortenUrlHandler;
import io.javalin.Javalin;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor_ = {@Inject})
public class Route {
    private final Javalin server;
    private final ShortenUrlHandler shortenUrlHandler;
    private final RedirectURLHandler redirectURLHandler;

    public void setupRoutes() {
        server.post("/shorten", shortenUrlHandler);
        server.get(String.format("/{%s}", Constants.SHORTEN_URL_PATH_PARAM), redirectURLHandler);
    }
}

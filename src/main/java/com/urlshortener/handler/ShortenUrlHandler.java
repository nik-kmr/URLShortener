package com.urlshortener.handler;

import com.google.inject.Inject;
import com.urlshortener.constants.Constants;
import com.urlshortener.helper.DatabaseHelper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import com.urlshortener.model.ShortenURLRequest;
import com.urlshortener.model.ShortenURLResponse;
import com.urlshortener.util.Encoder;

import java.util.Random;

@AllArgsConstructor(onConstructor_ = {@Inject})
public class ShortenUrlHandler implements Handler {
    private final DatabaseHelper databaseHelper;

    @Override
    public void handle(@NonNull final Context context) throws Exception {
        final ShortenURLRequest request = context.bodyAsClass(ShortenURLRequest.class);
        final String url = request.getUrl();

        final long currentNumber = databaseHelper.getAndIncrement(getRandomNumber());
        final String encodedString = Encoder.toEncodedString(currentNumber);
        databaseHelper.saveShortenURL(url, encodedString);
        context.json(getShortenURLResponse(context, encodedString));
    }

    private int getRandomNumber() {
        return new Random().nextInt(Constants.TOTAL_IDS);
    }

    private ShortenURLResponse getShortenURLResponse(final Context context, final String encodedString) {
        String scheme = context.header("X-Forwarded-Proto");
        final String domain = context.host();
        String shortUrl = scheme + "://" + domain + "/" + encodedString;
        return ShortenURLResponse.builder()
                .shortenURL(shortUrl)
                .build();
    }
}

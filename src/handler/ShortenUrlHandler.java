package handler;

import com.google.inject.Inject;
import constants.Constants;
import helper.DatabaseHelper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import model.ShortenURLRequest;
import model.ShortenURLResponse;
import util.Encoder;

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
        context.json(ShortenURLResponse.builder().shortenURL("").build());
    }

    private int getRandomNumber() {
        return new Random().nextInt(Constants.TOTAL_IDS);
    }
}

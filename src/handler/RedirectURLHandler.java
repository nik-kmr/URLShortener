package handler;

import com.google.inject.Inject;
import constants.Constants;
import helper.DatabaseHelper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor(onConstructor_ = {@Inject})
public class RedirectURLHandler implements Handler {
    private final DatabaseHelper databaseHelper;

    @Override
    public void handle(@NonNull final Context context) {
        final String shortURL = context.pathParam(Constants.SHORTEN_URL_PATH_PARAM);
        final String originalUrl = databaseHelper.getOriginalUrl(shortURL);
        context.redirect(originalUrl);
    }
}

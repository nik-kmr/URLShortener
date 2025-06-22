import com.google.inject.Inject;
import constants.Constants;
import handler.RedirectURLHandler;
import handler.ShortenUrlHandler;
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

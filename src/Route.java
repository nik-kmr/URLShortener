import com.google.inject.Inject;
import handler.ShortenUrlHandler;
import io.javalin.Javalin;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor_ = {@Inject})
public class Route {
    private final Javalin server;
    private final ShortenUrlHandler shortenUrlHandler;

    public void setupRoutes() {
        server.post("/shorten", shortenUrlHandler);
    }
}

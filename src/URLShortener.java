import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import module.DataBaseModule;
import org.apache.logging.log4j.LogManager;

public class URLShortener {
    public static void main(String[] args) {
        Injector injector;
        try {
            injector = Guice.createInjector(
                    Stage.PRODUCTION,
                    new DataBaseModule()
            );
        } catch (Exception e) {
            System.exit(1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(LogManager::shutdown));
    }
}

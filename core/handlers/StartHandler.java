package core.handlers;

import core.Constants;
import org.dreambot.api.methods.MethodContext;

import static org.dreambot.api.methods.MethodProvider.sleepUntil;

/**
 * Created by 7804364 on 2/14/2017.
 */
public class StartHandler {

    public StartHandler(MethodContext context) {
        if (context.getClientSettings().roofsEnabled()) {
            context.getClientSettings().toggleRoofs(false);
            sleepUntil(() -> !context.getClientSettings().roofsEnabled(), 3000);
        }
    }
}

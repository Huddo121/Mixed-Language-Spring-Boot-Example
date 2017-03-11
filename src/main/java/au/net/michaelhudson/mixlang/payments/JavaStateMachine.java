package au.net.michaelhudson.mixlang.payments;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by mhudson
 *
 * @since 11/3/17.
 */
public class JavaStateMachine {

    public static final List<String> STATES = Arrays.asList("INIT", "CLEARED", "SETTLED", "REJECTED", "ABORTED");
    public static final List<String> EVENTS = Arrays.asList("CLEARED", "SETTLED", "REJECTED", "ABORTED");
    private static Logger LOG = Logger.getLogger("JavaStateMachine");

    public static String calculate(String currentState, String event) {
        if(! STATES.contains(currentState)){
            LOG.warning("BAD STATE");
            return "ERROR";
        }
        if(! EVENTS.contains(event)) {
            LOG.warning("BAD EVENT");
            return "ERROR";
        }

        switch(currentState) {
            case "INIT":        return handleInit(event);
            case "CLEARED":     return handleCleared(event);
            case "SETTLED":     return handleSettled(event);
            case "REJECTED":    return handleRejected(event);
            case "ABORTED":     return handleAborted(event);
        }

        return "ERROR";
    }

    private static String handleInit(String event) {
        return event;
    }

    private static String handleCleared(String event) {
        return event;
    }

    private static String handleSettled(String event) {
        if(event.equals("ABORTED")) {
            LOG.warning("SETTLED -/-> ABORTED");
            return "ERROR";
        } else if(event.equals("REJECTED")) {
            LOG.warning("SETTLED -/-> REJECTED");
            return "ERROR";
        }

        return "SETTLED";
    }

    private static String handleRejected(String event) {
        if(event.equals("ABORTED")) {
            LOG.warning("REJECTED -/-> ABORTED");
            return "ERROR";
        } else if (event.equals("SETTLED")) {
            LOG.warning("REJECTED -/-> SETTLED");
            return "ERROR";
        }

        return "REJECTED";
    }

    private static String handleAborted(String event) {
        if(event.equals("REJECTED")) {
            LOG.warning("ABORTED -/-> REJECTED");
            return "ERROR";
        } else if (event.equals("SETTLED")) {
            LOG.warning("ABORTED -/-> SETTLED");
            return "ERROR";
        }
        return "ABORTED";
    }
}

package au.net.michaelhudson;

import au.net.michaelhudson.mixlang.payments.ScalaStateMachine;
import au.net.michaelhudson.mixlang.payments.JavaStateMachine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mhudson
 *
 * @since 11/3/17.
 */
@RunWith(Parameterized.class)
public class StateMachineTest {

    @Parameterized.Parameters(name = "In {0} with {1}: {2}")
    public static List<Object[]> StateMachineTest() {
        return Arrays.asList(new Object[][] {
                {"INIT", "CLEARED", "CLEARED"},
                {"INIT", "SETTLED", "SETTLED"},
                {"INIT", "ABORTED", "ABORTED"},
                {"INIT", "REJECTED", "REJECTED"},

                {"CLEARED", "CLEARED", "CLEARED"},
                {"CLEARED", "SETTLED", "SETTLED"},
                {"CLEARED", "ABORTED", "ABORTED"},
                {"CLEARED", "REJECTED", "REJECTED"},

                {"SETTLED", "CLEARED", "SETTLED"},
                {"SETTLED", "SETTLED", "SETTLED"},
                {"SETTLED", "ABORTED", "ERROR"},
                {"SETTLED", "REJECTED", "ERROR"},

                {"ABORTED", "CLEARED", "ABORTED"},
                {"ABORTED", "SETTLED", "ERROR"},
                {"ABORTED", "ABORTED", "ABORTED"},
                {"ABORTED", "REJECTED", "ERROR"},

                {"REJECTED", "CLEARED", "REJECTED"},
                {"REJECTED", "SETTLED", "ERROR"},
                {"REJECTED", "ABORTED", "ERROR"},
                {"REJECTED", "REJECTED", "REJECTED"},

                // Non-existant states and events
                {"REEEEJCTED", "REJECTED", "ERROR"},
                {"ERROR", "REJECTED", "ERROR"},
                {"INIT", "cleared", "ERROR"},
                {"ELBOW", "GREASE", "ERROR"}
        });
    }

    String currentState, event, expectedState;

    public StateMachineTest(String currentState, String event, String expectedState) {
        this.currentState = currentState;
        this.event = event;
        this.expectedState = expectedState;
    }

    @Test
    public void testJavaImplementation() {
        String actualState = JavaStateMachine.calculate(currentState, event);
        String scalaState = ScalaStateMachine.calculate(currentState, event);
        assertEquals(expectedState, actualState);
        assertEquals(expectedState, scalaState);
    }

}

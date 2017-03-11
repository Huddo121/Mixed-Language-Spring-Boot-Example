package au.net.michaelhudson.mixlang;

import au.net.michaelhudson.mixlang.payments.PaymentRepository;
import au.net.michaelhudson.mixlang.payments.ScalaStateMachine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

/**
 * Created by mhudson
 *
 * @since 11/3/17.
 */
@RestController
public class PaymentsController {

    private PaymentRepository db;

    public PaymentsController(PaymentRepository db) {
        this.db = db;
    }

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public Collection<Map.Entry<Integer, String>> getPayments() {
        return db.getPayments();
    }

    @RequestMapping(value = "/payments", method = RequestMethod.PUT)
    public Integer createPayment() {
        return db.createPayment();
    }

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public String calculateState(@RequestParam Integer paymentId, @RequestParam String event) {
        String currentState = db.getPaymentState(paymentId);
        String paymentState = ScalaStateMachine.calculate(currentState, event);

        if(paymentState.equals("ERROR")) {
            throw new IllegalArgumentException("ERROR - Payment State not updated, bad event");
        }

        db.update(paymentId, paymentState);
        return paymentState;
    }
}

package au.net.michaelhudson.mixlang.payments;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mhudson
 *
 * @since 11/3/17.
 */
@Repository
public class PaymentRepository {
    private ConcurrentHashMap<Integer, String> payments = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger();

    public PaymentRepository() {
    }

    public void update(int paymentId, String state) {
        payments.put(paymentId, state);
    }

    public Collection<Map.Entry<Integer, String>> getPayments() {
        return payments.entrySet();
    }

    public String getPaymentState(Integer paymentId) {
        String payment = payments.get(paymentId);

        if (payment == null) {
            throw new IllegalArgumentException("Payment does not exist");
        }

        return payment;
    }

    public Integer createPayment() {
        int paymentId = counter.incrementAndGet();
        update(paymentId, "INIT");
        return paymentId;
    }
}

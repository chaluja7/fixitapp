package cz.cvut.jee.testik.factorial;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigInteger;

@ApplicationScoped
public class FactorialImpl implements Factorial {

    @Inject
    private MathOperations math;

    @Override
    public BigInteger compute(long number) {
        return number == 0 ? BigInteger.ONE : math.multiplySequence(1, number);
    }
}

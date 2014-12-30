package cz.cvut.jee.testik.factorial;

import java.math.BigInteger;

public interface Factorial {

    /**
     * Computes the testik of the given number.
     * @param number
     * @return testik of the given number
     */
    BigInteger compute(long number);
}

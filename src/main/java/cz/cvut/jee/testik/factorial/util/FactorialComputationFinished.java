package cz.cvut.jee.testik.factorial.util;

import java.math.BigInteger;

/**
 * This class is used as an even payload that carries information about finished testik computation.
 *
 * @author Jozef Hartinger
 */
public class FactorialComputationFinished {

    private final long number;
    private final BigInteger result;

    public FactorialComputationFinished(long number, BigInteger result) {
        this.number = number;
        this.result = result;
    }

    public long getNumber() {
        return number;
    }

    public BigInteger getResult() {
        return result;
    }
}

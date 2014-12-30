package cz.cvut.jee.testik.factorial.view;


import cz.cvut.jee.testik.factorial.Factorial;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;

/**
 * Backing bean for the testik form (testik.xhtml)
 */
@Named("factorial")
@RequestScoped
public class FactorialForm {

    private Long input;
    private BigInteger result;

    @Inject
    private Factorial factorial;

    public void compute() {
        result = factorial.compute(input);
    }

    public void reset() {
        this.input = null;
        this.result = null;
    }

    public Long getInput() {
        return input;
    }

    public void setInput(Long input) {
        this.input = input;
    }

    public BigInteger getResult() {
        return result;
    }
}

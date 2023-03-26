package service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class FunctionCalculator {
    public static BigDecimal getYByX(int x) {
        BigDecimal function = BigDecimal.ONE
                .multiply(new BigDecimal(getFactor(x)))
                .divide(new BigDecimal(getFactorial(x)), 6, RoundingMode.HALF_DOWN);
        return function;
    }

    private static BigInteger getFactorial(int value) {
        if (value <= 1) {
            return BigInteger.valueOf(1);
        } else {
            return BigInteger.valueOf(value).multiply(getFactorial(value - 1));
        }
    }

    private static BigInteger getFactor(int value) {
        BigInteger function = BigInteger.ZERO;
        for (int i = 1; i <= value; i++) {
            function = function.add(getFactorial(i));
        }
        return function;
    }

}



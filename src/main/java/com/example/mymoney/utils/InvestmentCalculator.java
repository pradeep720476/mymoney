package com.example.mymoney.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvestmentCalculator {

    public static BigDecimal change(BigDecimal holdingAmount, Float percent) {
        if (holdingAmount != null) {
            return holdingAmount.multiply(BigDecimal.valueOf(percent / 100.00)).setScale(2, RoundingMode.HALF_UP)
                    .setScale(0, RoundingMode.FLOOR);
        }

        return BigDecimal.ZERO;
    }

    public static Float percent(BigDecimal divisor, BigDecimal divdent) {
        return (divisor.divide(divdent, 2, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(100)).floatValue();
    }

    public static BigDecimal amountOfPercetage(BigDecimal amount, Float percent) {
        return amount.multiply(BigDecimal.valueOf(percent / 100.00)).setScale(2, RoundingMode.HALF_UP)
                .setScale(0, RoundingMode.FLOOR);
    }

}

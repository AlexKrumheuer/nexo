package com.example.nexo.util;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class Util {
    public static BigDecimal calculateFinalPrice(BigDecimal price, Integer discount) {
        if (discount == null || discount == 0)
            return price;
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount))
                        .divide(BigDecimal.valueOf(100)));
    }
}

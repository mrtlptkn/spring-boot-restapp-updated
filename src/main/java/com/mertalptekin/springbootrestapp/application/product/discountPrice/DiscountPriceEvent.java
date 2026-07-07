package com.mertalptekin.springbootrestapp.application.product.discountPrice;

import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

public class DiscountPriceEvent extends ApplicationEvent {

    // Event args
    public final BigDecimal oldPrice;
    public final BigDecimal newPrice;
    public DiscountPriceEvent(Object source, BigDecimal oldPrice, BigDecimal newPrice) {
        super(source);
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }
}

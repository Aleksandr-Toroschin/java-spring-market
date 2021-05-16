package ru.toroschin.spring.market.utils;

public enum PaymentStatus {
    WAIT_PAY(1),
    PAYMENT(2),
    RETURN(3);

    private int value;

    PaymentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

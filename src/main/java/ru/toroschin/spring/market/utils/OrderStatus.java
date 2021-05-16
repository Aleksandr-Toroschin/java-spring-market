package ru.toroschin.spring.market.utils;

public enum OrderStatus {
    PLACED(1),
    IN_WORK(2),
    READY_TO_SHIP(3),
    SHIPPED(4),
    DELIVERED(5);

    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

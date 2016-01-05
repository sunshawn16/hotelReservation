package com.tw.hotelReservation.model;

public enum CustomerType {
    REGULAR("REGULAR"), REWARDS("REWARDS");

    private final String name;

    CustomerType(String name) {
        this.name = name;
    }
}

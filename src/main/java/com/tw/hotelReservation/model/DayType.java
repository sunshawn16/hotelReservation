package com.tw.hotelReservation.model;

public enum DayType {
    WEEKDAY("WEEKDAY"),WEEKEND("WEEKEND");

    private final String dayType;

    DayType(String dayType) {
        this.dayType = dayType;
    }
}

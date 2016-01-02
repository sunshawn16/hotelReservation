package com.tw.hotelReservation.model;

import javax.persistence.*;

@Entity
@Table(name = "HOTEL_PRICE")
public class HotelPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "CODE")
    private int code;

    @Column(name = "CUSTOMER_TYPE")
    private String customerType;


    @Column(name = "DAY_TYPE")
    private boolean dayType;

    @Column(name = "PRICE")
    private double price;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public boolean getDayType() {
        return dayType;
    }

    public void setDayType(boolean dayType) {
        this.dayType = dayType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

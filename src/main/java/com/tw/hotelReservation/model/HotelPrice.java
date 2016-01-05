package com.tw.hotelReservation.model;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "HOTEL_PRICE")
public class HotelPrice {
    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column(name = "CODE")
    private int code;

    @Enumerated(STRING)
    @Column(name = "CUSTOMER_TYPE")
    private CustomerType customerType;

    @Enumerated(STRING)
    @Column(name = "DAY_TYPE")
    private DayType dayType;

    @Column(name = "PRICE")
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

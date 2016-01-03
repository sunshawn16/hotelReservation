package com.tw.hotelReservation.model;

import javax.persistence.*;

@Entity
@Table(name = "HOTEL")
public class Hotel {

    @Column(name = "CODE")
    private int code;

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "RATING")
    private int rating;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

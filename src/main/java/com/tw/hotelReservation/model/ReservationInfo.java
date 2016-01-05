package com.tw.hotelReservation.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.joda.time.LocalDate;

@JsonInclude
public class ReservationInfo {

    private CustomerType customerType;
    private LocalDate arrivalDate;
    private LocalDate departureDate;


    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }
}

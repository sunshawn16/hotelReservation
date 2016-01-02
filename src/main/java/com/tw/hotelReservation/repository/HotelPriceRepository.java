package com.tw.hotelReservation.repository;

import com.tw.hotelReservation.model.HotelPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelPriceRepository extends CrudRepository<HotelPrice,Integer> {
    HotelPrice findByCodeAndCustomerTypeAndDayType(int code, String customerType, Boolean dayType);

    HotelPrice findByCode(int code);
}

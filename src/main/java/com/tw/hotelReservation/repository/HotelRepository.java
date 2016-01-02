package com.tw.hotelReservation.repository;

import com.tw.hotelReservation.model.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends CrudRepository<Hotel,Integer> {
    Hotel findByCode(int code);
}

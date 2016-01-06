package com.tw.hotelReservation.service;

import com.tw.hotelReservation.model.DayType;
import com.tw.hotelReservation.model.Hotel;
import com.tw.hotelReservation.model.ReservationInfo;
import com.tw.hotelReservation.repository.HotelPriceRepository;
import com.tw.hotelReservation.repository.HotelRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.tw.hotelReservation.model.DayType.WEEKDAY;
import static com.tw.hotelReservation.model.DayType.WEEKEND;
import static org.joda.time.Days.daysBetween;

@Service
public class ReserveService {

    public static final int SUNDAY = 7;
    public static final int SATURDAY = 6;
    @Autowired
    private HotelPriceRepository hotelPriceRepository;
    @Autowired
    private HotelRepository hotelRepository;

    public Hotel findBestReservation(ReservationInfo reservationInfo) {
        int numberOfHotel = (int) hotelRepository.count();
        Map<Integer, Double> payments = calculatePaymentsForHotels(reservationInfo, numberOfHotel);
        int indexForBestHotel = getIndexForBestHotel(payments, numberOfHotel);
        return hotelRepository.findByCode(indexForBestHotel);
    }

    private int getIndexForBestHotel(Map<Integer, Double> payments, int numOfHotel) {
        double minPayment = payments.get(1);
        int codeForBestHotel = 1;
        for (int codeOfHotel = 1; codeOfHotel <= numOfHotel; codeOfHotel++) {
            if (payments.get(codeOfHotel) <= minPayment) {
                minPayment = payments.get(codeOfHotel);
                codeForBestHotel = codeOfHotel;
            }
        }
        return codeForBestHotel;
    }

    private Map<Integer, Double> calculatePaymentsForHotels(ReservationInfo reservationInfo, int numberOfHotel) {
        int days = daysBetween(reservationInfo.getArrivalDate(), reservationInfo.getDepartureDate()).getDays();
        Map<Integer, Double> payments = new HashMap<>();
        for (int codeOfHotel = 1; codeOfHotel <= numberOfHotel; codeOfHotel++) {
            payments.put(codeOfHotel, paymentForOneHotel(reservationInfo, days, codeOfHotel));
        }
        return payments;
    }

    private double paymentForOneHotel(ReservationInfo reservationInfo, int days, int index) {
        double paymentForOneHotel = 0.0;
        LocalDate date = reservationInfo.getArrivalDate();
        for (int day = 0; day < days; day++) {
            double costForOneDay = hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(index,
                    reservationInfo.getCustomerType(), getDayType(date)).getPrice();
            paymentForOneHotel = paymentForOneHotel + costForOneDay;
            date = date.plusDays(1);

        }
        return paymentForOneHotel;
    }


    private DayType getDayType(LocalDate date) {
        return isSundayOrSaturday(date) ? WEEKEND : WEEKDAY;
    }

    private boolean isSundayOrSaturday(LocalDate date) {
        return SATURDAY == date.dayOfWeek().get() || SUNDAY == date.dayOfWeek().get();
    }

}

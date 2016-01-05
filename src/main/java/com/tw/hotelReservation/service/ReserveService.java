package com.tw.hotelReservation.service;

import com.tw.hotelReservation.model.DayType;
import com.tw.hotelReservation.model.Hotel;
import com.tw.hotelReservation.model.ReservationInfo;
import com.tw.hotelReservation.repository.HotelPriceRepository;
import com.tw.hotelReservation.repository.HotelRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<Double> payments = calculatePaymentsForHotels(reservationInfo, numberOfHotel);
        int indexForBestHotel = getIndexForBestHotel(payments, numberOfHotel);
        return hotelRepository.findByCode(indexForBestHotel + 1);
    }

    private int getIndexForBestHotel(List<Double> payments, int numOfHotel) {
        double minPayment = payments.get(0);
        int indexForBestHotel = 0;
        for (int index = 0; index < numOfHotel; index++) {
            if (payments.get(index) <= minPayment) {
                minPayment = payments.get(index);
                indexForBestHotel = index;
            }
        }
        return indexForBestHotel;
    }

    private List<Double> calculatePaymentsForHotels(ReservationInfo reservationInfo, int numberOfHotel) {
        int days = daysBetween(reservationInfo.getArrivalDate(), reservationInfo.getDepartureDate()).getDays();
        List<Double> payments = new ArrayList<>();
        for (int index = 1; index <= numberOfHotel; index++) {
            paymentForOneHotel(reservationInfo, days, payments, index);
        }
        return payments;
    }

    private void paymentForOneHotel(ReservationInfo reservationInfo, int days,List<Double> payments, int index) {
        double paymentForOneHotel = 0.0;
        LocalDate date = reservationInfo.getArrivalDate();
        for (int day = 0; day < days; day++) {
            double costForOneDay = hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(index,
                    reservationInfo.getCustomerType(), getDayType(date)).getPrice();
            paymentForOneHotel = paymentForOneHotel + costForOneDay;
            date = date.plusDays(1);

        }
        payments.add(paymentForOneHotel);
    }


    private DayType getDayType(LocalDate date) {
        return isSundayOrSaturday(date) ? WEEKEND : WEEKDAY;
    }

    private boolean isSundayOrSaturday(LocalDate date) {
        return SATURDAY == date.dayOfWeek().get() || SUNDAY == date.dayOfWeek().get();
    }

}

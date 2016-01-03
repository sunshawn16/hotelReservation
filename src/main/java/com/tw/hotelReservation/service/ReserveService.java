package com.tw.hotelReservation.service;

import com.tw.hotelReservation.model.CustomerInfo;
import com.tw.hotelReservation.model.Hotel;
import com.tw.hotelReservation.repository.HotelPriceRepository;
import com.tw.hotelReservation.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReserveService {

    @Autowired
    private HotelPriceRepository hotelPriceRepository;
    @Autowired
    private HotelRepository hotelRepository;

    public Hotel findBestReservation(CustomerInfo customerInfo) {
        List<Double> payments = calculateForEachHotel(customerInfo);
        int indexForBestHotel = getIndexForBestHotel(payments);
        return hotelRepository.findByCode(indexForBestHotel + 1);
    }

    private int getIndexForBestHotel(List<Double> payments) {
        double minPayment = payments.get(0);
        int indexForBestHotel = 0;
        for (int index = 0; index < 3; index++) {
            if (payments.get(index) < minPayment) {
                minPayment = payments.get(index);
                indexForBestHotel = index;
            }
        }
        return indexForBestHotel;
    }

    private List<Double> calculateForEachHotel(CustomerInfo customerInfo) {
        int days = daysBetween(customerInfo.getArrivalDate(), customerInfo.getDepartureDate());

        String customerType = customerInfo.getCustomerType();
        List<Double> payments = new ArrayList<>();
        for (int index = 1; index <= 3; index++) {
            double paymentForOneHotel = 0;
            Date date = customerInfo.getArrivalDate();
            for (int day = 0; day < days; day++) {
                double costForOneDay = hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(index, customerType, isWeekDay(date)).getPrice();
                paymentForOneHotel = paymentForOneHotel + costForOneDay;
                date = plusOneDay(date);
            }
            payments.add(paymentForOneHotel);
        }
        return payments;
    }


    public boolean isWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        return dayofweek != 1 & dayofweek != 7;
    }

    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date plusOneDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
}

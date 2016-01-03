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

    public static final int NUMBER_OF_HOTEL = 3;
    public static final int SUNDAY = 1;
    public static final int SATURDAY = 7;
    @Autowired
    private HotelPriceRepository hotelPriceRepository;
    @Autowired
    private HotelRepository hotelRepository;

    public Hotel findBestReservation(CustomerInfo customerInfo) {
        List<Double> payments = calculatePaymentsForHotels(customerInfo);
        int indexForBestHotel = getIndexForBestHotel(payments);
        return hotelRepository.findByCode(indexForBestHotel + 1);
    }

    private int getIndexForBestHotel(List<Double> payments) {
        double minPayment = payments.get(0);
        int indexForBestHotel = 0;
        for (int index = 0; index < NUMBER_OF_HOTEL; index++) {
            if (payments.get(index) <= minPayment) {
                minPayment = payments.get(index);
                indexForBestHotel = index;
            }
        }
        return indexForBestHotel;
    }

    private List<Double> calculatePaymentsForHotels(CustomerInfo customerInfo) {
        int days = daysBetween(customerInfo.getArrivalDate(), customerInfo.getDepartureDate());
        List<Double> payments = new ArrayList<>();
        for (int index = 1; index <= NUMBER_OF_HOTEL; index++) {
            paymentForOneHotel(customerInfo, days, payments, index);
        }
        return payments;
    }

    private void paymentForOneHotel(CustomerInfo customerInfo, int days, List<Double> payments, int index) {
        double paymentForOneHotel = 0;
        Date date = customerInfo.getArrivalDate();
        for (int day = 0; day < days; day++) {
            double costForOneDay = hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(index,
                    customerInfo.getCustomerType(), isWeekDay(date)).getPrice();
            paymentForOneHotel = paymentForOneHotel + costForOneDay;
            date = plusOneDay(date);
        }
        payments.add(paymentForOneHotel);
    }


    public boolean isWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        return dayofweek != SUNDAY & dayofweek != SATURDAY;
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

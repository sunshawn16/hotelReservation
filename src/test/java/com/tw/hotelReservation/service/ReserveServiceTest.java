package com.tw.hotelReservation.service;

import com.tw.hotelReservation.model.CustomerInfo;
import com.tw.hotelReservation.model.HotelPrice;
import com.tw.hotelReservation.repository.HotelPriceRepository;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.tw.hotelReservation.service.ReserveService.daysBetween;
import static com.tw.hotelReservation.service.ReserveService.plusOneDay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class ReserveServiceTest {
    public static final String PATTERN = "MM-dd-yyyy";
    public static final SimpleDateFormat DateFormat =
            new SimpleDateFormat(PATTERN);

    private ReserveService reserveService;
    private HotelPriceRepository hotelPriceRepository;

    @Before
    public void setUp() throws Exception {
        reserveService = new ReserveService();
        EasyMockSupport easyMockSupport = new EasyMockSupport();
        hotelPriceRepository = easyMockSupport.createMock(HotelPriceRepository.class);
        setField(reserveService, "hotelPriceRepository", hotelPriceRepository);

    }

    @Test
    public void findTheBestHotelForRegularCustomerFromDec28ToDec31() throws Exception {
        CustomerInfo customer = new CustomerInfo();
        customer.setCustomerType("REGULAR");
        customer.setArrivalDate(DateFormat.parse("12-28-2015"));
        customer.setDepartureDate(DateFormat.parse("12-31-2015"));
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(1, "REGULAR", true)).andReturn(generateHotelPrice(1, "REGULAR", true, 110)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(2, "REGULAR", true)).andReturn(generateHotelPrice(2, "REGULAR", true, 160)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(3, "REGULAR", true)).andReturn(generateHotelPrice(3, "REGULAR", true, 220)).anyTimes();

        replay(hotelPriceRepository);
        reserveService.findBestReservation(customer);
        verify(hotelPriceRepository);

        System.out.println(reserveService.getMinPayment());
    }
    @Test
    public void findTheBestHotelForRegularCustomerFromDec26ToDec29() throws Exception {
        CustomerInfo customer = new CustomerInfo();
        customer.setCustomerType("REGULAR");
        customer.setArrivalDate(DateFormat.parse("12-26-2015"));
        customer.setDepartureDate(DateFormat.parse("12-29-2015"));
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(1, "REGULAR", true)).andReturn(generateHotelPrice(1, "REGULAR", true, 110)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(2, "REGULAR", true)).andReturn(generateHotelPrice(2, "REGULAR", true, 160)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(3, "REGULAR", true)).andReturn(generateHotelPrice(3, "REGULAR", true, 220)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(1, "REGULAR", false)).andReturn(generateHotelPrice(1, "REGULAR", false, 90)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(2, "REGULAR", false)).andReturn(generateHotelPrice(2, "REGULAR", false, 60)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(3, "REGULAR", false)).andReturn(generateHotelPrice(3, "REGULAR", false, 150)).anyTimes();

        replay(hotelPriceRepository);
        reserveService.findBestReservation(customer);
        verify(hotelPriceRepository);

        System.out.println(reserveService.getMinPayment());
    }

    private HotelPrice generateHotelPrice(int code, String customerType, boolean dayType, double price) {
        HotelPrice hotelPrice = new HotelPrice();
        hotelPrice.setCode(code);
        hotelPrice.setCustomerType(customerType);
        hotelPrice.setDayType(dayType);
        hotelPrice.setPrice(price);
        return hotelPrice;

    }

    @Test
    public void isWeekend() throws Exception {
        Date date1 = DateFormat.parse("12-29-2015");
        Date date2 = DateFormat.parse("1-2-2016");

        assertThat(reserveService.isWeekDay(date1), is(true));
        assertThat(reserveService.isWeekDay(date2), is(false));
    }

    @Test
    public void testDaysBetween() throws Exception {

        Date date1 = DateFormat.parse("12-29-2015");
        Date date2 = DateFormat.parse("1-2-2016");

        assertThat(daysBetween(date1, date2), is(4));

    }

    @Test
    public void testPlusOneDay() throws Exception {
        Date date = DateFormat.parse("12-29-2015");
        assertThat(plusOneDay(date), is(DateFormat.parse("12-30-2015")));

    }
}
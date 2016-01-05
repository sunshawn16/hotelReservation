package com.tw.hotelReservation.service;

import com.tw.hotelReservation.model.*;
import com.tw.hotelReservation.repository.HotelPriceRepository;
import com.tw.hotelReservation.repository.HotelRepository;
import org.easymock.EasyMockSupport;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static com.tw.hotelReservation.model.CustomerType.REGULAR;
import static com.tw.hotelReservation.model.CustomerType.REWARDS;
import static com.tw.hotelReservation.model.DayType.WEEKDAY;
import static com.tw.hotelReservation.model.DayType.WEEKEND;
import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class ReserveServiceTest {
    private ReserveService reserveService;
    private HotelPriceRepository hotelPriceRepository;
    private HotelRepository hotelRepository;


    @Before
    public void setUp() throws Exception {
        reserveService = new ReserveService();
        EasyMockSupport easyMockSupport = new EasyMockSupport();
        hotelPriceRepository = easyMockSupport.createMock(HotelPriceRepository.class);
        hotelRepository = easyMockSupport.createMock(HotelRepository.class);
        setField(reserveService, "hotelPriceRepository", hotelPriceRepository);
        setField(reserveService, "hotelRepository", hotelRepository);

    }

    @Test
    public void findTheBestHotelForRegularCustomerFromDec28ToDec31() throws Exception {
        ReservationInfo customer = new ReservationInfo();
        customer.setCustomerType(REGULAR);
        customer.setArrivalDate(new LocalDate(2015,12,28));
        customer.setDepartureDate(new LocalDate(2015,12,31));
        expect(hotelRepository.count()).andReturn(3L);
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(1, REGULAR, WEEKDAY)).andReturn(generateHotelPrice(1, REGULAR, WEEKDAY, 110)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(2, REGULAR, WEEKDAY)).andReturn(generateHotelPrice(2, REGULAR, WEEKDAY, 160)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(3, REGULAR, WEEKDAY)).andReturn(generateHotelPrice(3, REGULAR, WEEKDAY, 220)).anyTimes();
        expect(hotelRepository.findByCode(1)).andReturn(generateHotel(1, "LAKEWOOD")).times(1);

        replay(hotelPriceRepository, hotelRepository);
        Hotel hotel = reserveService.findBestReservation(customer);
        verify(hotelPriceRepository, hotelRepository);

        assertThat(hotel.getCode(), is(1));

    }

    @Test
    public void findTheBestHotelForRegularCustomerFromDec26ToDec29() throws Exception {
        ReservationInfo customer = new ReservationInfo();
        customer.setCustomerType(REGULAR);
        customer.setArrivalDate(new LocalDate(2015,12,26));
        customer.setDepartureDate(new LocalDate(2015,12,29));
        expect(hotelRepository.count()).andReturn(3L);
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(1, REGULAR, WEEKDAY)).andReturn(generateHotelPrice(1, REGULAR, WEEKDAY, 110)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(2, REGULAR, WEEKDAY)).andReturn(generateHotelPrice(2, REGULAR, WEEKDAY, 160)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(3, REGULAR, WEEKDAY)).andReturn(generateHotelPrice(3, REGULAR, WEEKDAY, 220)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(1, REGULAR, WEEKEND)).andReturn(generateHotelPrice(1, REGULAR, WEEKEND, 90)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(2, REGULAR, WEEKEND)).andReturn(generateHotelPrice(2, REGULAR, WEEKEND, 60)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(3, REGULAR, WEEKEND)).andReturn(generateHotelPrice(3, REGULAR, WEEKEND, 150)).anyTimes();
        expect(hotelRepository.findByCode(2)).andReturn(generateHotel(2, "BRIDGEWOOD")).times(1);

        replay(hotelPriceRepository, hotelRepository);
        Hotel hotel = reserveService.findBestReservation(customer);
        verify(hotelPriceRepository, hotelRepository);
        assertThat(hotel.getCode(), is(2));

    }
    @Test
    public void findTheBestHotelForRewardsCustomerFromMAR26ToMar29() throws Exception {
        ReservationInfo customer = new ReservationInfo();
        customer.setCustomerType(REWARDS);
        customer.setArrivalDate(new LocalDate(2009,3,26));
        customer.setDepartureDate(new LocalDate(2009,3,29));
        expect(hotelRepository.count()).andReturn(3L);
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(1, REWARDS, WEEKDAY)).andReturn(generateHotelPrice(1, REWARDS, DayType.WEEKDAY, 80)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(2, REWARDS, WEEKDAY)).andReturn(generateHotelPrice(2, REWARDS, DayType.WEEKDAY, 110)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(3, REWARDS, WEEKDAY)).andReturn(generateHotelPrice(3, REWARDS, DayType.WEEKDAY, 100)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(1, REWARDS, WEEKEND)).andReturn(generateHotelPrice(1, REWARDS, DayType.WEEKEND, 80)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(2, REWARDS, WEEKEND)).andReturn(generateHotelPrice(2, REWARDS, DayType.WEEKEND, 50)).anyTimes();
        expect(hotelPriceRepository.findByCodeAndCustomerTypeAndDayType(3, REWARDS, WEEKEND)).andReturn(generateHotelPrice(3, REWARDS, DayType.WEEKEND, 40)).anyTimes();
        expect(hotelRepository.findByCode(3)).andReturn(generateHotel(3, "RIDGEWOOD")).times(1);

        replay(hotelPriceRepository, hotelRepository);
        Hotel hotel = reserveService.findBestReservation(customer);
        verify(hotelPriceRepository, hotelRepository);
        assertThat(hotel.getCode(), is(3));

    }

    private Hotel generateHotel(int id, String name) {
        Hotel hotel = new Hotel();
        hotel.setCode(id);
        hotel.setName(name);
        return hotel;
    }

    private HotelPrice generateHotelPrice(int code, CustomerType customerType, DayType dayType, double price) {
        HotelPrice hotelPrice = new HotelPrice();
        hotelPrice.setCode(code);
        hotelPrice.setCustomerType(customerType);
        hotelPrice.setDayType(dayType);
        hotelPrice.setPrice(price);
        return hotelPrice;

    }


}
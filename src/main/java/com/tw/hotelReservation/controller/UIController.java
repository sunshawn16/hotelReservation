package com.tw.hotelReservation.controller;

import com.tw.hotelReservation.model.CustomerInfo;
import com.tw.hotelReservation.model.Hotel;
import com.tw.hotelReservation.repository.HotelRepository;
import com.tw.hotelReservation.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UIController {
    @Autowired
    private ReserveService reserveService;
    @Autowired
    private HotelRepository hotelRepository;

    @RequestMapping(value = "/welcome")
    public String home() {
        return "welcome";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.GET)
    public String reserve(Model model) {
        model.addAttribute("customerInfo", new CustomerInfo());
        return "reserve";
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public String submitReservation(@ModelAttribute CustomerInfo customerInfo, Model model) {
        Hotel hotel = reserveService.findBestReservation(customerInfo);
        model.addAttribute("hotelResult", hotel);
        return "result";

    }

}

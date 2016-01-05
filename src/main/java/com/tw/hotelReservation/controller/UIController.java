package com.tw.hotelReservation.controller;

import com.tw.hotelReservation.model.Hotel;
import com.tw.hotelReservation.model.ReservationInfo;
import com.tw.hotelReservation.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class UIController {
    @Autowired
    private ReserveService reserveService;

    @RequestMapping(value = "/welcome")
    public String home() {
        return "welcome";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/reserve", method = GET)
    public String reserve(Model model) {
        model.addAttribute("reservationInfo", new ReservationInfo());
        return "reserve";
    }

    @RequestMapping(value = "/reserve", method = POST)
    public String submitReservation(@ModelAttribute ReservationInfo reservationInfo, Model model) {
        Hotel hotel = reserveService.findBestReservation(reservationInfo);
        model.addAttribute("hotelResult", hotel);
        return "result";

    }

}

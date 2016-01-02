package com.tw.hotelReservation.controller;

import com.tw.hotelReservation.model.CustomerInfo;
import com.tw.hotelReservation.repository.HotelRepository;
import com.tw.hotelReservation.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "/reserve", method = RequestMethod.GET)
    public String reserve(Model model) {
        model.addAttribute("reservationInfo", new CustomerInfo());
        return "reserve";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView submitReservation(@ModelAttribute CustomerInfo customerInfo){
        reserveService.findBestReservation(customerInfo);
        return new ModelAndView("result");
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public String submitReservation(@ModelAttribute CustomerInfo customerInfo, Model model) {
//        HotelPrice hotelPrice = reserveService.findBestReservation(customerInfo);
//        model.addAttribute("hotelResult",hotelRepository.findByCode(hotelPrice.getCode()));
//        return "result";
//    }


}

package com.example.SpringTest01.controllers;

import com.example.SpringTest01.dtos.ReservationDTO;
import com.example.SpringTest01.services.ReservationService;
import com.example.SpringTest01.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final DateUtils dateUtils;
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(DateUtils dateUtils, ReservationService reservationService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getReservations(@RequestParam(value = "date", required = false) String dateString, Model model) {
        Date date = this.dateUtils.createDateFromDateString(dateString);
        List<ReservationDTO> reservations = this.reservationService.getRoomReservationsForDate(date);
        model.addAttribute("reservations", reservations);
        return "reservations";
    }
}

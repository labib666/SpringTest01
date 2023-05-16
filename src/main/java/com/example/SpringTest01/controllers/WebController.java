package com.example.SpringTest01.controllers;

import com.example.SpringTest01.dtos.GuestDTO;
import com.example.SpringTest01.dtos.ReservationDTO;
import com.example.SpringTest01.dtos.RoomDTO;
import com.example.SpringTest01.services.GuestService;
import com.example.SpringTest01.services.ReservationService;
import com.example.SpringTest01.services.RoomService;
import com.example.SpringTest01.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebController {
    private final DateUtils dateUtils;
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final GuestService guestService;

    @Autowired
    public WebController(DateUtils dateUtils, ReservationService reservationService, RoomService roomService, GuestService guestService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.guestService = guestService;
    }

    @RequestMapping(path = "/reservations", method = RequestMethod.GET)
    public List<ReservationDTO> getReservations(@RequestParam(value = "date", required = false) String dateString) {
        Date date = this.dateUtils.createDateFromDateString(dateString);
        return this.reservationService.getRoomReservationsForDate(date);
    }

    @RequestMapping(path = "/rooms", method = RequestMethod.GET)
    public List<RoomDTO> getRooms() {
        return this.roomService.getAllRooms();
    }

    @RequestMapping(path = "/guests", method = RequestMethod.GET)
    public List<GuestDTO> getGuests() {
        return this.guestService.getAllGuests();
    }
}

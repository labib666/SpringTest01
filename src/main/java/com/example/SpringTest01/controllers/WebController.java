package com.example.SpringTest01.controllers;

import com.example.SpringTest01.dtos.ReservationDTO;
import com.example.SpringTest01.models.Guest;
import com.example.SpringTest01.models.Room;
import com.example.SpringTest01.services.GuestService;
import com.example.SpringTest01.services.ReservationService;
import com.example.SpringTest01.services.RoomService;
import com.example.SpringTest01.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<Room> getRooms() {
        return this.roomService.getAllRooms();
    }

    @RequestMapping(path = "/guests", method = RequestMethod.GET)
    public List<Guest> getGuests() {
        return this.guestService.getAllGuests();
    }

    @RequestMapping(path = "/guests", method = RequestMethod.POST)
    public ResponseEntity<Guest> addGuest(@RequestBody Guest guest) {
        Optional<Guest> persistedGuest = this.guestService.createGuest(guest);
        if(persistedGuest.isEmpty()) {
            return ResponseEntity.internalServerError().body(null);
        } else {
            return ResponseEntity.created(URI.create(String.format("/guests/%s", guest.getGuestId()))).body(persistedGuest.get());
        }
    }
}

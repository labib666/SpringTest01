package com.example.SpringTest01.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.SpringTest01.dtos.ReservationDTO;
import com.example.SpringTest01.models.Guest;
import com.example.SpringTest01.repositories.GuestRepository;
import com.example.SpringTest01.models.Reservation;
import com.example.SpringTest01.repositories.ReservationRepository;
import com.example.SpringTest01.models.Room;
import com.example.SpringTest01.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationDTO> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, ReservationDTO> reservationMap = new HashMap<>();
        rooms.forEach(room -> {
            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setRoomId(room.getId());
            reservationDTO.setRoomName(room.getName());
            reservationDTO.setRoomNumber(room.getRoomNumber());
            reservationMap.put(room.getId(), reservationDTO);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            ReservationDTO reservationDTO = reservationMap.get(reservation.getRoomId());
            reservationDTO.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            reservationDTO.setFirstName(guest.getFirstName());
            reservationDTO.setLastName(guest.getLastName());
            reservationDTO.setGuestId(guest.getGuestId());
            reservationDTO.setReservationId(reservation.getReservationId());
        });
        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Long id : reservationMap.keySet()) {
            reservationDTOs.add(reservationMap.get(id));
        }
        reservationDTOs.sort((ReservationDTO o1, ReservationDTO o2) -> {
            if (o1.getRoomName().equals(o2.getRoomName())) {
                return o1.getRoomNumber().compareTo(o2.getRoomNumber());
            }
            return o1.getRoomName().compareTo(o2.getRoomName());
        });
        return reservationDTOs;
    }
}


package com.example.SpringTest01.services;

import com.example.SpringTest01.dtos.GuestDTO;
import com.example.SpringTest01.models.Guest;
import com.example.SpringTest01.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<GuestDTO> getAllGuests() {
        Iterable<Guest> guests = this.guestRepository.findAll();
        List<GuestDTO> guestDTOs = new ArrayList<>();
        guests.forEach(guest -> {
            GuestDTO guestDTO = new GuestDTO();
            guestDTO.setLastName(guest.getLastName());
            guestDTO.setFirstName(guest.getFirstName());
            guestDTO.setEmailAddress(guest.getEmailAddress());
            guestDTO.setPhoneNumber(guest.getPhoneNumber());
            guestDTOs.add(guestDTO);
        });
        return guestDTOs;
    }
}


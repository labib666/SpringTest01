package com.example.SpringTest01.services;

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

    public List<Guest> getAllGuests() {
        List<Guest> guests = new ArrayList<>();
        this.guestRepository.findAll().forEach(guests::add);
        guests.sort((Guest o1, Guest o2) -> {
            if (o1.getLastName().equals(o2.getLastName())) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            } else {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        return guests;
    }

    public Optional<Guest> createGuest(Guest guest) {
        System.out.println("Create " + guest);
        Optional<Guest> persistedGuest = Optional.empty();
        try {
            persistedGuest = Optional.of(this.guestRepository.save(guest));
        } catch(Exception e) {
            System.out.println("DB Error: " + e.getMessage());
        }
        return persistedGuest;
    }
}


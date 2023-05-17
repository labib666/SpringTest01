package com.example.SpringTest01.services;

import com.example.SpringTest01.models.Room;
import com.example.SpringTest01.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        this.roomRepository.findAll().forEach(rooms::add);
        rooms.sort((Room o1, Room o2) -> {
            if (o1.getName().equals(o2.getName())) {
                return o1.getRoomNumber().compareTo(o2.getRoomNumber());
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return rooms;
    }
}


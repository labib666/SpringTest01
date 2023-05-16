package com.example.SpringTest01.utils;

import com.example.SpringTest01.repositories.GuestRepository;
import com.example.SpringTest01.repositories.ReservationRepository;
import com.example.SpringTest01.repositories.RoomRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    public AppStartupEvent(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        System.out.println("Application Event: " + event);

        printAllEntries(roomRepository);
        printAllEntries(guestRepository);
        printAllEntries(reservationRepository);
    }

    private void printAllEntries(@NonNull CrudRepository<?, ?> repository) {
        Iterable<?> entries = repository.findAll();
        entries.forEach(System.out::println);
    }
}

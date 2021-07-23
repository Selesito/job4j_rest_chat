package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Iterable<Room> findAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> findById(int id) {
        return roomRepository.findById(id);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public void delete(Room room) {
        roomRepository.delete(room);
    }
}

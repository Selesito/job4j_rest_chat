package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Iterable<Room> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        var person = this.service.findById(id);
        return new ResponseEntity<Room>(
                person.orElse(new Room()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return new ResponseEntity<Room>(
                this.service.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        this.service.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Room room = new Room();
        room.setId(id);
        this.service.delete(room);
        return ResponseEntity.ok().build();
    }
}
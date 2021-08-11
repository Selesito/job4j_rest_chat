package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import java.util.HashMap;
import java.util.Optional;

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
    public ResponseEntity<Optional<Room>> findById(@PathVariable int id) {
        var person = this.service.findById(id);
        if (!person.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person is not found. Please, check requisites.");
        }
        var entity = ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.TEXT_PLAIN)
                .body(person);
        return entity;
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("Username mustn't be empty");
        }
        var body = this.service.save(room);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(body);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("Username mustn't be empty");
        }
        this.service.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (id == 0) {
            throw new NullPointerException("ID mustn't be empty");
        }
        Room room = new Room();
        room.setId(id);
        var body = new HashMap<>() {{
            put("1", room);
        }}.toString();
        this.service.delete(room);

        var entity = ResponseEntity.status(HttpStatus.OK)
                .header("Delete", "delete")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(body.length())
                .body(body);
        return entity;
    }
}
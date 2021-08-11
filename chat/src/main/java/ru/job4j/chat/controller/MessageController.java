package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.service.MessegerService;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessegerService service;

    public MessageController(MessegerService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Iterable<Message> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        var message = this.service.findById(id);
        if (!message.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Message is not found. Please, check requisites.");
        }
        return new ResponseEntity<Message>(message.orElse(new Message()), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody Message message, @RequestParam int idRoom, @RequestParam int idPer) {
        if (message.getName() == null || message.getText() == null) {
            throw new NullPointerException("Name or text mustn't be empty");
        }
        return new ResponseEntity<Message>(
                this.service.save(message, idRoom, idPer),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        if (message.getName() == null || message.getText() == null) {
            throw new NullPointerException("Name or text mustn't be empty");
        }
        this.service.save(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (id == 0) {
            throw new NullPointerException("ID mustn't be empty");
        }
        Message message = new Message();
        message.setId(id);
        this.service.delete(message);
        return ResponseEntity.ok().build();
    }
}
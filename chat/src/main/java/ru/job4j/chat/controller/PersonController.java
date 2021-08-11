package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.PathService;
import ru.job4j.chat.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@RestController
@RequestMapping("/person")
public class PersonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());

    private final PersonService service;

    private final ObjectMapper objectMapper;

    public PersonController(PersonService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public Iterable<Person> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.service.findById(id);
        if (!person.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person is not found. Please, check requisites.");
        }
        return new ResponseEntity<Person>(person.orElse(new Person()), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        if (person.getName() == null || person.getPassword() == null
                || person.getLogin() == null) {
            throw new NullPointerException("Username or password  or login mustn't be empty");
        }
        return new ResponseEntity<Person>(
                this.service.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        if (person.getName() == null || person.getPassword() == null
                || person.getLogin() == null) {
            throw new NullPointerException("Username or password  or login mustn't be empty");
        }
        if (person.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Invalid person. Argument is equal to the empty");
        }
        this.service.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@RequestParam int id) {
        if (id == 0) {
            throw new NullPointerException("ID mustn't be empty");
        }
        Person person = new Person();
        person.setId(id);
        this.service.delete(person);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }

    @PatchMapping("/example")
    public Person example(@RequestBody Person person) throws InvocationTargetException, IllegalAccessException {
        var current = service.findById(person.getId());
        if (current == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var path = new PathService<Person>();
        service.save(path.getPatch(current.get(), person));
        return current.get();
    }
}
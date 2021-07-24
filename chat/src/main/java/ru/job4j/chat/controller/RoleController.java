package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleRepository repository;

    public RoleController(RoleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public Iterable<Role> findAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        var role = this.repository.findById(id);
        return new ResponseEntity<Role>(
                role.orElse(new Role()),
                role.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return new ResponseEntity<Role>(
                this.repository.save(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Role role) {
        this.repository.save(role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Role role = new Role();
        role.setId(id);
        this.repository.delete(role);
        return ResponseEntity.ok().build();
    }
}
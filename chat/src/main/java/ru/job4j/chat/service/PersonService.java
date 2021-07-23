package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoleRepository;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    public PersonService(PersonRepository personRepository, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        Person per = personRepository.save(person);
        per.setRole(roleRepository.findByAuthority("ROLE_USER"));
        return personRepository.save(per);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }
}

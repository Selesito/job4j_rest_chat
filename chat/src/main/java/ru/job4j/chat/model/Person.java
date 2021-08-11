package ru.job4j.chat.model;

import ru.job4j.chat.Operation;

import javax.persistence.*;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private int id;
    @NotBlank(message = "Title must be not empty", groups = {
            Operation.OnUpdate.class, Operation.OnCreate.class
    })
    private String name;
    @NotBlank(message = "Title must be not empty", groups = {
            Operation.OnUpdate.class, Operation.OnCreate.class
    })
    private String login;
    @NotBlank(message = "Title must be not empty", groups = {
            Operation.OnUpdate.class, Operation.OnCreate.class
    })
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public static Person of(int id, String login, String password, String name) {
        Person person = new Person();
        person.setName(name);
        person.setId(id);
        person.setLogin(login);
        person.setPassword(password);
        return person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

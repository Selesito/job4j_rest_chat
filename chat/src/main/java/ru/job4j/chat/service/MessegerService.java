package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.MessageRepository;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoomRepository;

import java.util.Optional;

@Service
public class MessegerService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final PersonRepository personRepository;

    public MessegerService(MessageRepository messageRepository, RoomRepository roomRepository, PersonRepository personRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.personRepository = personRepository;
    }

    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

    public Optional<Message> findById(int id) {
        return messageRepository.findById(id);
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public Message save(Message mess, int idRoom, int idPer) {
        Message message = messageRepository.save(mess);
        Optional<Person> person = personRepository.findById(idPer);
        message.setPerson(person.get());
        Optional<Room> room = roomRepository.findById(idRoom);
        room.get().addMessage(message);
        roomRepository.save(room.get());
        return message;
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }
}

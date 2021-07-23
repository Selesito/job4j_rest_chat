create table role (
    id serial primary key not null,
    authority varchar(2000)
);

create table person (
    id serial primary key not null,
    name varchar(2000),
    login varchar(2000),
    password varchar(2000),
    role_id INT REFERENCES role(id)
);

create table message (
    id serial primary key not null,
    name varchar(2000),
    text varchar(2000),
    person_id INT REFERENCES person(id)
);

create table room (
    id serial primary key not null,
    name varchar(2000)
);

insert into role (authority) values ('ROLE_USER');
insert into role (authority) values ('ROLE_ADMIN');

insert into room (name) values ('G-chats');
insert into room (name) values ('Job');
insert into room (name) values ('Private');

insert into person (name, login, password, role_id)
values ('admin', 'admin', '123', (select id from role where authority = 'ROLE_ADMIN'));

insert into person (name, login, password, role_id)
values ('root', 'root', '123', (select id from role where authority = 'ROLE_USER'));

insert into message (name, text, person_id) values ('Правила', 'Правила', 2);
insert into message (name, text, person_id) values ('Вопрос', 'Вопрос', 1);
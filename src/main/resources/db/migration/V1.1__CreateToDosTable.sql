create table todos (
       id  serial not null,
        completed boolean not null,
        text varchar(255) not null ,
        primary key (id)
    );
alter table if exists media drop constraint if exists media_type_fk;
alter table if exists message_media drop constraint if exists message_fk;
alter table if exists message_media drop constraint if exists media_fk;

drop table if exists message_media cascade;
drop table if exists media cascade;
drop table if exists media_type cascade;

create table media_type (
    id      int8 generated by default as identity,
    name    varchar(255) not null,
    primary key (id)
);

create table media (
    id              int8 generated by default as identity,
    name            varchar(255) not null,
    media_type_id   int8 not null,
    data            bytea,
    primary key (id)
);

create table message_media (
    message_id  int8 not null,
    media_id    int8 not null,
    primary key (message_id, media_id)
);

alter table if exists media add constraint media_type_fk foreign key (media_type_id) references media_type;
alter table if exists message_media add constraint message_fk foreign key (message_id) references message;
alter table if exists message_media add constraint media_fk foreign key (media_id) references media;

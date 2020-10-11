drop table user;
create table if not exists USER (
    id varchar(256) primary key,
    username varchar(100),
    email varchar(100) not null,
    enabled boolean default true not null,
    password varchar(255) not null,
    tokenExpired boolean default false not null
);

create index i_user on user(username, email);

drop table role;

create table if not exists ROLE (
    id int primary key auto_increment,
    name varchar(100) not null
);

drop table privilege;
create table if not exists privilege (
    id int primary key auto_increment,
    name varchar(100) not null
);
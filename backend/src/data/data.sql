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
create table if not exists role (
    id int primary key auto_increment,
    name varchar(100) not null
);

drop table privilege;
create table if not exists privilege (
    id int primary key auto_increment,
    name varchar(100) not null
);

insert into privilege(id, name) values
(0, 'file:read'),
(1, 'file:read_all'),
(2, 'file:add'),
(3, 'file:add_all'),
(4, 'file:delete'),
(5, 'file:delete_all'),
(6, 'file:update'),
(7, 'file:update_all');


insert into role(id, name) values
(0, 'USER'),
(1, 'ADMIN');

insert into role_privileges values
(0, 0),
(0, 1),
(0, 2),
(0, 3),
(0, 4),
(0, 5),
(0, 6),
(0, 7);

drop table file;
create table if not exists file (
  id varchar(256) primary key,
  name varchar(100) not null ,
  size double not null,
  link varchar(256) not null
);

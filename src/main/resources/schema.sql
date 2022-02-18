create table dept (
    id bigint not null auto_increment,
    name varchar(255) not null, 
    reg_dt datetime default now(),
    primary key (id)
);

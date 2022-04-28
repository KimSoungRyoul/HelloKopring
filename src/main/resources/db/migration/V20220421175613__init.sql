create table newsfeed
(
    id               bigint not null auto_increment,
    created_datetime datetime(6),
    updated_datetime datetime(6),
    approval         bit    not null,
    contents         varchar(255),
    receiver_id      bigint not null,
    title            varchar(255),
    user_id          bigint,
    primary key (id)
) engine = InnoDB;

create table user
(
    id               bigint not null auto_increment,
    created_datetime datetime(6),
    updated_datetime datetime(6),
    email            varchar(50),
    password         varchar(100),
    username         varchar(50),
    primary key (id)
) engine = InnoDB;

create table user_roles
(
    user_id bigint not null,
    roles   varchar(255)
) engine = InnoDB;

alter table user
    add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

alter table user
    add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);

alter table newsfeed
    add constraint FKn9qc1h5bynw6b7i95bbsy364m
        foreign key (user_id)
            references user (id);

alter table user_roles
    add constraint FK55itppkw3i07do3h7qoclqd4k
        foreign key (user_id)
            references user (id);

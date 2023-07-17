create table if not exists users (
    id               bigint       not null,
    username         varchar(255) not null,
    password         text not null,
    email            varchar(255) not null,
    display_username varchar(255) not null,
    profile_picture  text,
    primary key (id),
    constraint username_uq unique (username),
    constraint email_uq unique (email)
);

create sequence if not exists user_id_sequence start 1 increment 10 owned by users.id;

create table if not exists user_details (
    user_id                     bigint primary key references users (id),
    is_active                   boolean not null,
    is_non_locked               boolean not null,
    has_not_expired             boolean not null,
    are_credentials_not_expired boolean not null
);

create table if not exists account_verification_token (
    user_id    bigint primary key references users (id),
    expires_at timestamp without time zone not null
);

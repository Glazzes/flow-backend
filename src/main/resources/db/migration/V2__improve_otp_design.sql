drop table account_verification_token;

create table otp(
    id bigint,
    token varchar(255) not null,
    type int not null,
    expires_at timestamp without time zone not null,
    user_id bigint not null,
    primary key (id),
    constraint user_id_fk foreign key(user_id) references users(id)
);

create sequence if not exists otp_sequence start 1 increment 10;

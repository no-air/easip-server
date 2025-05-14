create table member (
    id CHAR(26) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    deleted_at timestamp(6),
    name varchar(255) not null,
    primary key (id)
);

create table social_auth (
    identifier varchar(255) not null,
    provider VARCHAR(10) CHECK (provider IN ('APPLE', 'GOOGLE')) NOT NULL,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    member_id CHAR(26) not null,
    primary key (identifier, provider)
);

alter table social_auth
add constraint FKioepl7sbxuqumxaumweigjvae
foreign key (member_id)
references member(id);

-- HOUSE 테이블 생성
create table house (
    house_id varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    deleted_at timestamp(6),
    name varchar(255) not null,
    address varchar(255) not null,
    near_station varchar(255),
    developer_name varchar(255),
    constructor_name varchar(255),
    first_recruitment_date varchar(255),
    move_in_date varchar(255),
    general_supply_count varchar(255) not null,
    special_supply_count varchar(255) not null,
    latitude varchar(255) not null,
    longitude varchar(255) not null,
    primary key (house_id)
);

-- HOUSE_IMAGE 테이블 생성
create table house_image (
    house_image_id varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    deleted_at timestamp(6),
    house_id varchar(255) not null,
    url varchar(255) not null,
    house_image_type varchar(255) not null,
    ordering varchar(255) not null,
    primary key (house_image_id)
);

-- ROOM_RENTAL_CONDITION 테이블 생성
create table room_rental_condition (
    rental_condition_id varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    house_id varchar(255) not null,
    supply_type varchar(255) not null,
    room_type varchar(255) not null,
    exclusive_area double precision not null,
    application_eligibility varchar(255) not null,
    total_room_count integer not null,
    deposit double precision not null,
    monthly_rent double precision not null,
    maintenance_fee double precision not null,
    primary key (rental_condition_id)
);

-- BADGE 테이블 생성
create table badge (
    badge_id varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    deleted_at timestamp(6),
    name varchar(255) not null,
    primary key (badge_id)
);

-- DISTRICT 테이블 생성
create table district (
    district_id varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    deleted_at timestamp(6),
    name varchar(255) not null,
    primary key (district_id)
);

-- POST 테이블 생성
create table post (
    post_id varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    deleted_at timestamp(6),
    title varchar(255) not null,
    primary key (post_id)
);

-- POST_SCHEDULE 테이블 생성
create table post_schedule (
    post_schedule_id varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    deleted_at timestamp(6),
    ordering integer not null,
    title varchar(255) not null,
    start_date date,
    start_datetime varchar(255),
    start_note varchar(255),
    end_datetime varchar(255),
    end_note varchar(255),
    post_id varchar(255) not null,
    primary key (post_schedule_id)
);

-- POST_HOUSE 테이블 생성
create table post_house (
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    deleted_at timestamp(6),
    house_id varchar(255) not null,
    supply_type varchar(255) not null,
    living_type varchar(255) not null,
    deposit double precision,
    monthly_rent double precision,
    post_id varchar(255),
    supply_room_count integer not null,
    primary key (house_id, supply_type, living_type, deposit, monthly_rent)
);

-- POST_SCHEDULE_NOTIFICATION 테이블 생성
create table post_schedule_notification (
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    deleted_at timestamp(6),
    post_schedule_id varchar(255) not null,
    member_id varchar(255) not null,
    primary key (post_schedule_id, member_id)
);

-- BOOKMARK 테이블 생성
create table bookmark (
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    deleted_at timestamp(6),
    member_id varchar(255) not null,
    house_id varchar(255) not null,
    primary key (member_id, house_id)
);

-- MEMBER_DEVICE 테이블 생성
create table member_device (
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    fcm_token varchar(255) not null,
    member_id varchar(255) not null,
    primary key (fcm_token)
);

-- HOUSE_DISTRICT 연결 테이블 생성
create table house_district (
    house_id varchar(255) not null,
    district_id varchar(255) not null,
    primary key (house_id, district_id)
);

-- HOUSE_BADGE 연결 테이블 생성
create table house_badge (
    house_id varchar(255) not null,
    badge_id varchar(255) not null,
    primary key (house_id, badge_id)
);

-- POST_BADGE 연결 테이블 생성
create table post_badge (
    post_id varchar(255) not null,
    badge_id varchar(255) not null,
    primary key (post_id, badge_id)
);

-- MEMBER_DISTRICT 연결 테이블 생성
create table member_district (
    member_id varchar(255) not null,
    district_id varchar(255) not null,
    primary key (member_id, district_id)
);

-- 외래 키 제약 조건 추가
alter table house_image
    add constraint fk_house_image_house
    foreign key (house_id)
    references house(house_id);

alter table room_rental_condition
    add constraint fk_room_rental_condition_house
    foreign key (house_id)
    references house(house_id);

alter table post_schedule
    add constraint fk_post_schedule_post
    foreign key (post_id)
    references post(post_id);

alter table post_house
    add constraint fk_post_house_post
    foreign key (post_id)
    references post(post_id);

alter table post_house
    add constraint fk_post_house_house
    foreign key (house_id)
    references house(house_id);

alter table post_schedule_notification
    add constraint fk_post_schedule_notification_post_schedule
    foreign key (post_schedule_id)
    references post_schedule(post_schedule_id);

alter table post_schedule_notification
    add constraint fk_post_schedule_notification_member
    foreign key (member_id)
    references member(id);

alter table bookmark
    add constraint fk_bookmark_member
    foreign key (member_id)
    references member(id);

alter table bookmark
    add constraint fk_bookmark_house
    foreign key (house_id)
    references house(house_id);

alter table member_device
    add constraint fk_member_device_member
    foreign key (member_id)
    references member(id);

alter table house_district
    add constraint fk_house_district_house
    foreign key (house_id)
    references house(house_id);

alter table house_district
    add constraint fk_house_district_district
    foreign key (district_id)
    references district(district_id);

alter table house_badge
    add constraint fk_house_badge_house
    foreign key (house_id)
    references house(house_id);

alter table house_badge
    add constraint fk_house_badge_badge
    foreign key (badge_id)
    references badge(badge_id);

alter table post_badge
    add constraint fk_post_badge_post
    foreign key (post_id)
    references post(post_id);

alter table post_badge
    add constraint fk_post_badge_badge
    foreign key (badge_id)
    references badge(badge_id);

alter table member_district
    add constraint fk_member_district_member
    foreign key (member_id)
    references member(id);

alter table member_district
    add constraint fk_member_district_district
    foreign key (district_id)
    references district(district_id);

-- 인덱스 생성
create index idx_post_schedule_start_date on post_schedule(start_date); 
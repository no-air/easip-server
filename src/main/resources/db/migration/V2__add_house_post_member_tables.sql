-- 연결 테이블 먼저 삭제
DROP TABLE IF EXISTS post_schedule_notification;
DROP TABLE IF EXISTS post_house;
DROP TABLE IF EXISTS post_badge;
DROP TABLE IF EXISTS house_badge;
DROP TABLE IF EXISTS house_district;
DROP TABLE IF EXISTS member_district;
DROP TABLE IF EXISTS bookmark;
DROP TABLE IF EXISTS member_device;

-- 기본 테이블 삭제
DROP TABLE IF EXISTS post_schedule;
DROP TABLE IF EXISTS room_rental_condition;
DROP TABLE IF EXISTS house_image;
DROP TABLE IF EXISTS social_auth;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS house;
DROP TABLE IF EXISTS badge;
DROP TABLE IF EXISTS district;
DROP TABLE IF EXISTS member;

-- badge 테이블
CREATE TABLE IF NOT EXISTS badge (
    badge_id CHAR(26) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    deleted_at TIMESTAMP(6),
    is_deleted BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (badge_id)
);

-- bookmark 테이블
CREATE TABLE IF NOT EXISTS bookmark (
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    house_id VARCHAR(255) NOT NULL,
    member_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (house_id, member_id)
);

-- district 테이블
CREATE TABLE IF NOT EXISTS district (
    district_id CHAR(26) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    deleted_at TIMESTAMP(6),
    is_deleted BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (district_id)
);

-- house 테이블
CREATE TABLE IF NOT EXISTS house (
    house_id CHAR(26) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    deleted_at TIMESTAMP(6),
    is_deleted BOOLEAN NOT NULL,
    address VARCHAR(255) NOT NULL,
    constructor_name VARCHAR(255),
    developer_name VARCHAR(255),
    first_recruitment_date VARCHAR(255),
    general_supply_count VARCHAR(255) NOT NULL,
    latitude VARCHAR(255) NOT NULL,
    longitude VARCHAR(255) NOT NULL,
    move_in_date VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    near_station VARCHAR(255),
    special_supply_count VARCHAR(255) NOT NULL,
    PRIMARY KEY (house_id)
);

-- house_badge 테이블
CREATE TABLE IF NOT EXISTS house_badge (
    house_id CHAR(26) NOT NULL,
    badge_id CHAR(26) NOT NULL,
    PRIMARY KEY (house_id, badge_id)
);

-- house_district 테이블
CREATE TABLE IF NOT EXISTS house_district (
    house_id CHAR(26) NOT NULL,
    district_id CHAR(26) NOT NULL,
    PRIMARY KEY (house_id, district_id)
);

-- house_image 테이블
CREATE TABLE IF NOT EXISTS house_image (
    house_image_id CHAR(26) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    deleted_at TIMESTAMP(6),
    is_deleted BOOLEAN NOT NULL,
    house_image_type TINYINT NOT NULL CHECK (house_image_type BETWEEN 0 AND 1),
    ordering VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    house_id CHAR(26) NOT NULL,
    PRIMARY KEY (house_image_id)
);

-- member 테이블
CREATE TABLE IF NOT EXISTS member (
    id CHAR(26) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    deleted_at TIMESTAMP(6),
    is_deleted BOOLEAN NOT NULL,
    all_family_member_count INT NOT NULL,
    asset_price DOUBLE,
    car_price BIGINT,
    date_of_birth DATE NOT NULL,
    family_member_monthly_salary DOUBLE NOT NULL,
    has_car BOOLEAN NOT NULL,
    my_mothly_salary DOUBLE NOT NULL,
    name VARCHAR(255) NOT NULL,
    position TINYINT NOT NULL CHECK (position BETWEEN 0 AND 2),
    living_district_id CHAR(26) NOT NULL,
    PRIMARY KEY (id)
);

-- member_district 테이블
CREATE TABLE IF NOT EXISTS member_district (
    member_id CHAR(26) NOT NULL,
    district_id CHAR(26) NOT NULL,
    PRIMARY KEY (member_id, district_id)
);

-- member_device 테이블
CREATE TABLE IF NOT EXISTS member_device (
    fcm_token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    member_id CHAR(26) NOT NULL,
    PRIMARY KEY (fcm_token)
);

-- post 테이블
CREATE TABLE IF NOT EXISTS post (
    post_id CHAR(26) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    deleted_at TIMESTAMP(6),
    is_deleted BOOLEAN NOT NULL,
    title VARCHAR(255) NOT NULL,
    PRIMARY KEY (post_id)
);

-- post_badge 테이블
CREATE TABLE IF NOT EXISTS post_badge (
    post_id CHAR(26) NOT NULL,
    badge_id CHAR(26) NOT NULL,
    PRIMARY KEY (post_id, badge_id)
);

-- post_house 테이블
CREATE TABLE IF NOT EXISTS post_house (
    deposit DOUBLE NOT NULL,
    living_type VARCHAR(100) NOT NULL,
    monthly_rent DOUBLE NOT NULL,
    supply_type VARCHAR(100) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    supply_room_count INT NOT NULL,
    house_id VARCHAR(255) NOT NULL,
    post_id CHAR(26),
    PRIMARY KEY (deposit, house_id, living_type, monthly_rent, supply_type)
);

-- post_schedule 테이블
CREATE TABLE IF NOT EXISTS post_schedule (
    post_schedule_id CHAR(26) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    deleted_at TIMESTAMP(6),
    is_deleted BOOLEAN NOT NULL,
    end_datetime VARCHAR(255),
    end_note VARCHAR(255),
    ordering INT NOT NULL,
    start_date DATE,
    start_datetime VARCHAR(255),
    start_note VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    post_id CHAR(26) NOT NULL,
    PRIMARY KEY (post_schedule_id)
);

-- post_schedule_notification 테이블
CREATE TABLE IF NOT EXISTS post_schedule_notification (
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    member_id VARCHAR(255) NOT NULL,
    post_schedule_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (member_id, post_schedule_id)
);

-- room_rental_condition 테이블
CREATE TABLE IF NOT EXISTS room_rental_condition (
    rental_condition_id CHAR(26) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    application_eligibility ENUM('NEWLY_MARRIED_COUPLE','PRE_NEWLY_MARRIED_COUPLE','YOUNG_MAN') NOT NULL,
    deposit DOUBLE NOT NULL,
    exclusive_area DOUBLE NOT NULL,
    maintenance_fee DOUBLE NOT NULL,
    monthly_rent DOUBLE NOT NULL,
    room_type VARCHAR(255) NOT NULL,
    supply_type VARCHAR(255) NOT NULL,
    total_room_count INT NOT NULL,
    house_id CHAR(26) NOT NULL,
    PRIMARY KEY (rental_condition_id)
);

-- social_auth 테이블
CREATE TABLE IF NOT EXISTS social_auth (
    identifier VARCHAR(255) NOT NULL,
    provider TINYINT NOT NULL CHECK (provider BETWEEN 0 AND 1),
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    member_id CHAR(26) NOT NULL,
    PRIMARY KEY (identifier, provider)
);

-- 외래 키 제약조건
ALTER TABLE member
    ADD CONSTRAINT fk_member_living_district FOREIGN KEY (living_district_id) REFERENCES district(district_id);

ALTER TABLE member_device
    ADD CONSTRAINT fk_member_device_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE bookmark
    ADD CONSTRAINT fk_bookmark_house FOREIGN KEY (house_id) REFERENCES house(house_id);

ALTER TABLE bookmark
    ADD CONSTRAINT fk_bookmark_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE house_badge
    ADD CONSTRAINT fk_house_badge_badge FOREIGN KEY (badge_id) REFERENCES badge(badge_id);

ALTER TABLE house_badge
    ADD CONSTRAINT fk_house_badge_house FOREIGN KEY (house_id) REFERENCES house(house_id);

ALTER TABLE house_district
    ADD CONSTRAINT fk_house_district_district FOREIGN KEY (district_id) REFERENCES district(district_id);

ALTER TABLE house_district
    ADD CONSTRAINT fk_house_district_house FOREIGN KEY (house_id) REFERENCES house(house_id);

ALTER TABLE house_image
    ADD CONSTRAINT fk_house_image_house FOREIGN KEY (house_id) REFERENCES house(house_id);

ALTER TABLE member_district
    ADD CONSTRAINT fk_member_district_district FOREIGN KEY (district_id) REFERENCES district(district_id);

ALTER TABLE member_district
    ADD CONSTRAINT fk_member_district_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE post_badge
    ADD CONSTRAINT fk_post_badge_badge FOREIGN KEY (badge_id) REFERENCES badge(badge_id);

ALTER TABLE post_badge
    ADD CONSTRAINT fk_post_badge_post FOREIGN KEY (post_id) REFERENCES post(post_id);

ALTER TABLE post_house
    ADD CONSTRAINT fk_post_house_house FOREIGN KEY (house_id) REFERENCES house(house_id);

ALTER TABLE post_house
    ADD CONSTRAINT fk_post_house_post FOREIGN KEY (post_id) REFERENCES post(post_id);

ALTER TABLE post_schedule
    ADD CONSTRAINT fk_post_schedule_post FOREIGN KEY (post_id) REFERENCES post(post_id);

ALTER TABLE post_schedule_notification
    ADD CONSTRAINT fk_post_schedule_notification_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE post_schedule_notification
    ADD CONSTRAINT fk_post_schedule_notification_post_schedule FOREIGN KEY (post_schedule_id) REFERENCES post_schedule(post_schedule_id);

ALTER TABLE room_rental_condition
    ADD CONSTRAINT fk_room_rental_condition_house FOREIGN KEY (house_id) REFERENCES house(house_id);

ALTER TABLE social_auth
    ADD CONSTRAINT fk_social_auth_member FOREIGN KEY (member_id) REFERENCES member(id);

-- 인덱스 생성
CREATE INDEX IF NOT EXISTS idx_post_schedule_start_date ON post_schedule (start_date);
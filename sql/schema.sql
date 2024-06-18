create table homestay
(
    id           bigint generated always as identity primary key,
    name         text not null,
    description  text,
    type         integer,
    status       integer,
    phone_number text,

    address      text,
    ward_id      integer,
    district_id  integer,
    province_id  integer,

    images text[],

    guests       smallint,
    bedrooms     smallint,
    bathrooms    smallint,

    extra_data   jsonb,
    version      bigint,
    created_at   timestamp with time zone,
    created_by   bigint,
    updated_at   timestamp with time zone,
    updated_by   bigint
);

create table "user"
(
    id         bigint generated always as identity primary key,
    username   text     not null,
    password   text     not null,
    email      text     not null,
    fullname   text,
    type       smallint not null,
    status     smallint not null,

    extra_data jsonb,
    version    bigint,
    created_at timestamp with time zone,
    created_by bigint,
    updated_at timestamp with time zone,
    updated_by bigint
);

create table profile
(
    user_id    bigint not null,
    avatar     text,
    work       text,
    about      text,
    interests  text[],
    status     smallint,

    extra_data jsonb,
    version    bigint,
    created_at timestamp with time zone,
    created_by bigint,
    updated_at timestamp with time zone,
    updated_by bigint
);

create table booking
(
    id            bigint generated always as identity primary key,
    user_id       bigint   not null,
    homestay_id   bigint   not null,
    checkin_date  date     not null,
    checkout_date date     not null,
    guests        smallint not null,
    status        smallint not null,

    currency      text     not null,
    subtotal      numeric(12, 6),
    discount      numeric(6, 8),
    total_amount  numeric  not null,
    price_detail  jsonb,

    note          text,
    request_id    text     not null,

    version       smallint,
    extra_data    jsonb,
    created_at    timestamp with time zone,
    created_by    bigint,
    updated_at    timestamp with time zone,
    updated_by    bigint
);

create table homestay_availability
(
    homestay_id bigint not null,
    date        date   not null,
    price       numeric,
    status      smallint,
    primary key (homestay_id, date)
);


create table amenity
(
    id   integer generated always as identity primary key,
    name text not null,
    icon text not null
);

create table homestay_amenity
(
    homestay_id bigint  not null constraint homestay_amenity_homestay_id_fk references homestay,
    amenity_id  integer not null constraint homestay_amenity_amenity_id_fk references amenity
);

create table ward
(
    id          integer generated always as identity primary key,
    ward_name   text not null,
    district_id integer
);


create table district
(
    id            integer generated always as identity primary key,
    district_name text not null,
    province_id   integer
);

create table province
(
    id            integer generated always as identity primary key,
    province_name text not null,
    country_id    integer
);
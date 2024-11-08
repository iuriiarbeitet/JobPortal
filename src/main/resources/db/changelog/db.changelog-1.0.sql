--liquibase formatted sql

--changeset rotary:1
create table if not exists users_type
(
    `user_type_id` int NOT NULL,
    `user_type_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`user_type_id`)
);

--changeset rotary:2
create table if not exists users
(
    is_active         bit          not null,
    user_id           int auto_increment
        primary key,
    user_type_id      int          null,
    registration_date datetime(6)  null,
    email             varchar(255) null,
    password          varchar(255) not null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint FK5snet2ikvi03wd4rabd40ckdl
        foreign key (user_type_id) references users_type (user_type_id)
);

--changeset rotary:3
create table if not exists job_company
(
    id   int auto_increment
        primary key,
    logo varchar(255) null,
    name varchar(255) null
);

--changeset rotary:4
create table if not exists job_location
(
    id      int auto_increment
        primary key,
    city    varchar(255) null,
    country varchar(255) null,
    state   varchar(255) null
);

--changeset rotary:5
create table if not exists job_seeker_profile
(
    user_account_id    int          not null
        primary key,
    profile_photo      varchar(64)  null,
    city               varchar(255) null,
    country            varchar(255) null,
    employment_type    varchar(255) null,
    first_name         varchar(255) null,
    last_name          varchar(255) null,
    resume             varchar(255) null,
    state              varchar(255) null,
    work_authorization varchar(255) null,
    constraint FKohp1poe14xlw56yxbwu2tpdm7
        foreign key (user_account_id) references users (user_id)
);

--changeset rotary:6
create table if not exists recruiter_profile
(
    user_account_id int          not null
        primary key,
    profile_photo   varchar(64)  null,
    city            varchar(255) null,
    company         varchar(255) null,
    country         varchar(255) null,
    first_name      varchar(255) null,
    last_name       varchar(255) null,
    state           varchar(255) null,
    constraint FK42q4eb7jw1bvw3oy83vc05ft6
        foreign key (user_account_id) references users (user_id)
);

--changeset rotary:7
create table if not exists job_post_activity
(
    job_company_id     int            null,
    job_location_id    int            null,
    job_post_id        int auto_increment
        primary key,
    posted_by_id       int            null,
    posted_date        datetime(6)    null,
    description_of_job varchar(10000) null,
    job_title          varchar(255)   null,
    job_type           varchar(255)   null,
    remote             varchar(255)   null,
    salary             varchar(255)   null,
    constraint FK44003mnvj29aiijhsc6ftsgxe
        foreign key (job_location_id) references job_location (id),
    constraint FK62yqqbypsq2ik34ngtlw4m9k3
        foreign key (posted_by_id) references users (user_id),
    constraint FKpjpv059hollr4tk92ms09s6is
        foreign key (job_company_id) references job_company (id)
);

--changeset rotary:8
create table if not exists job_seeker_save
(
    id      int auto_increment
        primary key,
    job     int null,
    user_id int null,
    constraint UKimgfq93sbk5ihxxicpk9we62x
        unique (user_id, job),
    constraint FK96dyvgd8hmdohqsfdpvyl89mg
        foreign key (user_id) references job_seeker_profile (user_account_id),
    constraint FKpb44x040gkdltxqy9m7jmvvf3
        foreign key (job) references job_post_activity (job_post_id)
);

--changeset rotary:9
create table if not exists job_seeker_apply
(
    id           int auto_increment
        primary key,
    job          int          null,
    user_id      int          null,
    apply_date   datetime(6)  null,
    cover_letter varchar(255) null,
    constraint UKeu17rpgtaeppufvfectnh3g1g
        unique (user_id, job),
    constraint FKmfhx9q4uclbb74vm49lv9dmf4
        foreign key (job) references job_post_activity (job_post_id),
    constraint FKs9fftlyxws2ak05q053vi57qv
        foreign key (user_id) references job_seeker_profile (user_account_id)
);

--changeset rotary:10
create table if not exists skills
(
    id                  int auto_increment
        primary key,
    job_seeker_profile  int          null,
    experience_level    varchar(255) null,
    name                varchar(255) null,
    years_of_experience varchar(255) null,
    constraint FKsjdksau8sat30c00aqh5xf2wh
        foreign key (job_seeker_profile) references job_seeker_profile (user_account_id)
);

# --changeset rotary:11
# INSERT INTO `users_type` VALUES ((1,'Recruiter'),(2,'Job Seeker'));

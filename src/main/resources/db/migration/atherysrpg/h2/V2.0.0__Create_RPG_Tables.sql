create schema if not exists atherysrpg;

create table if not exists atherysrpg.PlayerCharacter (
    id binary not null,
    experience double not null,
    spentAttributeExperience double not null,
    spentExperience double not null,
    spentSkillExperience double not null,
    primary key (id)
);

create table if not exists atherysrpg.PlayerCharacter_attributes (
    PlayerCharacter_id binary not null,
    value double,
    attribute_type varchar(255) not null,
    primary key (PlayerCharacter_id, attribute_type)
);

create table if not exists atherysrpg.PlayerCharacter_skills (
    PlayerCharacter_id binary not null,
    skills varchar(255)
);

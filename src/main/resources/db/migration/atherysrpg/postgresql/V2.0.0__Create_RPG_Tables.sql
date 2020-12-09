create schema if not exists atherysrpg;

create table if not exists atherysrpg.PlayerCharacter (
    id uuid not null,
    experience float8 not null,
    spentAttributeExperience float8 not null,
    spentExperience float8 not null,
    spentSkillExperience float8 not null,
    primary key (id)
);

create table if not exists atherysrpg.PlayerCharacter_attributes (
    PlayerCharacter_id uuid not null,
    value float8,
    attribute_type varchar(255) not null,
    primary key (PlayerCharacter_id, attribute_type)
);

create table if not exists atherysrpg.PlayerCharacter_skills (
    PlayerCharacter_id uuid not null,
    skills varchar(255)
);
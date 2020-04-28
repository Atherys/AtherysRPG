CREATE TABLE IF NOT EXISTS atherys.playercharacter (
    id uuid NOT NULL,
    experience double precision NOT NULL,
    spentexperience double precision NOT NULL,
    spentattributeexperience double precision NOT NULL,
    spentskillexperience double precision NOT NULL
);
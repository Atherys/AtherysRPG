CREATE TABLE IF NOT EXISTS atherys.playercharacter_attributes (
    playercharacter_id uuid NOT NULL,
    value double precision,
    attribute_type character varying(255) NOT NULL
);
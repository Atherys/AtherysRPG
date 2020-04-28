ALTER TABLE atherys.playercharacter_attributes
    DROP CONSTRAINT IF EXISTS playercharacter_attributes_pkey;
ALTER TABLE ONLY atherys.playercharacter_attributes
    ADD CONSTRAINT playercharacter_attributes_pkey PRIMARY KEY (playercharacter_id, attribute_type);

ALTER TABLE atherys.playercharacter
    DROP CONSTRAINT IF EXISTS playercharacter_pkey;
ALTER TABLE ONLY atherys.playercharacter
    ADD CONSTRAINT playercharacter_pkey PRIMARY KEY (id);

ALTER TABLE atherys.playercharacter_attributes
    DROP CONSTRAINT IF EXISTS playercharacter_attributes_fkey;
ALTER TABLE ONLY atherys.playercharacter_attributes
    ADD CONSTRAINT playercharacter_attributes_fkey FOREIGN KEY (playercharacter_id) REFERENCES atherys.playercharacter(id);

ALTER TABLE atherys.playercharacter_skills
    DROP CONSTRAINT IF EXISTS playercharacter_skills_fkey;
ALTER TABLE ONLY atherys.playercharacter_skills
    ADD CONSTRAINT playercharacter_skills_fkey FOREIGN KEY (playercharacter_id) REFERENCES atherys.playercharacter(id);


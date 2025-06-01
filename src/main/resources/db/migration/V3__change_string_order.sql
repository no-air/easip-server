ALTER TABLE house_image MODIFY COLUMN ordering INTEGER NOT NULL;
ALTER TABLE house ADD COLUMN page_url VARCHAR(2083);
ALTER TABLE house_image MODIFY COLUMN house_image_type VARCHAR(20) NOT NULL;
ALTER TABLE house_image DROP CHECK house_image_type;
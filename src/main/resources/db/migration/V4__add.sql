ALTER TABLE house_image DROP CHECK house_image_type;
ALTER TABLE member MODIFY COLUMN position VARCHAR(20) NOT NULL;
ALTER TABLE member DROP CHECK position;
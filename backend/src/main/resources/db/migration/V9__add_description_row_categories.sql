
ALTER TABLE categories DROP COLUMN active;

ALTER TABLE categories RENAME COLUMN category_name TO name;
ALTER TABLE categories RENAME COLUMN category_description TO description;

ALTER TABLE categories RENAME COLUMN category_status TO active;
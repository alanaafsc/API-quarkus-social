-- public.posts definition

-- Drop table

-- DROP TABLE public.posts;

CREATE TABLE public.posts (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	datetime timestamp NULL,
	post_text varchar(255) NULL,
	user_id int8 NULL
);
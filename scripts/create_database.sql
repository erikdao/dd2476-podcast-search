CREATE TABLE IF NOT EXISTS metadata (
	show_uri varchar(50) NOT NULL,
	show_name varchar(255),
	show_description text,
	publisher varchar(255),
	LANGUAGE varchar
(50),
	rss_link varchar(255),
	episode_uri varchar(255),
	episode_name varchar(255),
	episode_description text,
	duration numeric(15, 10),
	show_filename_prefix varchar(50),
	episode_filename_prefix varchar(50),
	id serial NOT NULL CONSTRAINT metadata_pkey PRIMARY KEY,
	CONSTRAINT metadata_show_uri_episode_uri_key UNIQUE (show_uri, episode_uri)
);

ALTER TABLE metadata OWNER TO postgres;

CREATE TABLE IF NOT EXISTS shows (
	show_uri varchar(50) NOT NULL CONSTRAINT shows_pkey PRIMARY KEY,
	show_name varchar(255),
	show_description text,
	publisher varchar(255),
	LANGUAGE varchar
(50),
	rss_link varchar(255),
	show_filename_prefix varchar(50)
);

ALTER TABLE shows OWNER TO postgres;

CREATE TABLE IF NOT EXISTS episodes (
	episode_uri varchar(50) NOT NULL CONSTRAINT episodes_pkey PRIMARY KEY,
	episode_name varchar(255),
	episode_description text,
	duration numeric(15, 10),
	episode_filename_prefix varchar(50),
	show_uri varchar(50) NOT NULL CONSTRAINT fk_shows REFERENCES shows
);

ALTER TABLE episodes OWNER TO postgres;
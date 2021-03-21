CREATE TABLE metadata (
    show_uri CHARACTER VARYING(50) PRIMARY KEY,
    show_name CHARACTER VARYING(255),
    show_description TEXT,
    publisher CHARACTER VARYING(255),
    language CHARACTER VARYING(50),
    rss_link CHARACTER VARYING(255),
    episode_uri CHARACTER VARYING(255),
    episode_name CHARACTER VARYING(255),
    episode_description TEXT,
    duration NUMERIC(15, 10),
    show_filename_prefix CHARACTER VARYING(50),
    episode_filename_prefix CHARACTER VARYING(50)
);

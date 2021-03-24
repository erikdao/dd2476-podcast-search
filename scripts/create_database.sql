create table if not exists metadata
(
    show_uri                varchar(50) not null,
    show_name               varchar(255),
    show_description        text,
    publisher               varchar(255),
    language                varchar(50),
    rss_link                varchar(255),
    episode_uri             varchar(255),
    episode_name            varchar(255),
    episode_description     text,
    duration                numeric(15, 10),
    show_filename_prefix    varchar(50),
    episode_filename_prefix varchar(50),
    id                      serial      not null
    constraint metadata_pkey primary key,
    constraint metadata_show_uri_episode_uri_key unique (show_uri, episode_uri)
);

alter table metadata owner to postgres;

create table if not exists shows
(
    show_uri             varchar(50) not null constraint shows_pkey primary key,
    show_name            varchar(255),
    show_description     text,
    publisher            varchar(255),
    language             varchar(50),
    rss_link             varchar(255),
    show_filename_prefix varchar(50)
);

alter table shows owner to postgres;

create table if not exists episodes
(
    episode_uri             varchar(50) not null constraint episodes_pkey primary key,
    episode_name            varchar(255),
    episode_description     text,
    duration                numeric(15, 10),
    episode_filename_prefix varchar(50),
    show_uri                varchar(50) not null  constraint fk_shows references shows
);

alter table episodes owner to postgres;


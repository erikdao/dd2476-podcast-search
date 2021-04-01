import os
import sys
import typing
import argparse
from tqdm import tqdm

from logger import logger
from models import db, Show, Episode, Metadata

CURRENT_DIR = os.getcwd()
ROOT_DIR = os.path.dirname(os.path.dirname(CURRENT_DIR))


def maybe_create_tables():
    """Inspect the database and create the tables possibly"""
    tables = db.get_tables()
    if not tables:
        logger.info("Creating tables")
        db.create_tables([Metadata, Show, Episode])

    logger.info("To ensure the integrity of database, existing data will "
                "now be removed")

    query = Metadata.raw("""TRUNCATE metadata CASCADE""")
    query.execute()

    query = Episode.raw("""TRUNCATE episodes CASCADE""")
    query.execute()

    query = Show.raw("""TRUNCATE shows CASCADE""")
    query.execute()


def line_to_metadata_dict(parts: typing.List[str]) -> typing.Optional[dict]:
    """
    Create a metadata dict from each line in the metadata file
    """
    if len(parts) != 12:
        logger.error('Invalid metadata line, ignored!')
        logger.debug(parts)
        return

    return {
        'show_uri': parts[0],
        'show_name': parts[1],
        'show_description': parts[2],
        'publisher': parts[3],
        'language': parts[4].replace("'[", "").replace("]'", ""),
        'rss_link': parts[5],
        'episode_uri': parts[6],
        'episode_name': parts[7],
        'episode_description': parts[8],
        'duration': float(parts[9]),
        'show_filename_prefix': parts[10],
        'episode_filename_prefix': parts[11],
    }


def load_metadata(fname: str) -> None:
    """
    Read the metadata line by line, parse the records and put them
    to database

    Each line contains data for the following fields:
    1. show_uri
    2. show_name
    3. show_description
    4. publisher
    5. language
    6. rss_link
    7. episode_uri
    8. episode_name
    9. episode_description
    10. duration
    11. show_filename_prefix
    12. episode_filename_prefix    
    """
    logger.debug(f'Input file: {fname}')
    
    instances = []
    with open(fname, 'r') as f:
        header = next(f)  # skip header
        
        count = 0
        for line in tqdm(f):
            parts = line.strip().split('\t')
            metadata = line_to_metadata_dict(parts)
            if metadata is None:
                continue

            instances.append(metadata)
            count += 1

            if count % 10000 == 0:
                Metadata.insert_many(instances).execute()
                logger.info(f"Inserted {count} records to database")
                instances = []

        if len(instances) != 0:
            Metadata.insert_many(instances).execute()
            logger.info(f"Inserted {count} records to database")


def insert_show_data() -> None:
    """Insert records to shows table from metadata table"""
    logger.info("Inserting shows data""")

    INSERT_SHOW_SQL = """
      INSERT INTO shows(
        show_uri, show_name, show_description, publisher, language, rss_link,
        show_filename_prefix
      )
      SELECT DISTINCT ON (show_uri)
        show_uri, show_name, show_description, publisher, language, rss_link,
        show_filename_prefix
      FROM metadata
    """
    Show.raw(INSERT_SHOW_SQL).execute()


def insert_episode_data() -> None:
    """Insert records to episodes table from metadata table"""
    logger.info("Inserting episodes data""")

    INSERT_EPISODES_SQL = """
      INSERT INTO episodes(
        episode_uri, episode_name, episode_description,
        duration, show_uri, episode_filename_prefix
      )
      SELECT DISTINCT ON (episode_uri)
        episode_uri, episode_name, episode_description,
        duration, show_uri, episode_filename_prefix
      FROM metadata
    """
    Episode.raw(INSERT_EPISODES_SQL).execute()


def main(args: typing.Any) -> None:
    maybe_create_tables()
    fname = os.path.join(ROOT_DIR, args.tsv_file) 
    load_metadata(fname)
    insert_show_data()
    insert_episode_data()


if __name__ == '__main__':
    db.connect()

    # Parse arg
    parser = argparse.ArgumentParser()
    parser.add_argument('-f', '--tsv-file', required=True)
    args = parser.parse_args()

    main(args)

    db.close()


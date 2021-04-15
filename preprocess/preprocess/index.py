"""
Interface to Elasticsearch index
"""
from typing import Any

from elasticsearch import Elasticsearch, helpers

from logger import logger
from models import db, Episode, Show


client = Elasticsearch([
    {'host': 'localhost', 'port': 9200}
])


def create_show_index():
    if client.indices.exists('shows'):
        logger.info("`shows` index existed. For integrity, it will now be"
                    "removed and replaced with new index")
        client.indices.delete('shows')

    mappings = {
        'properties': {
            'id': {'type': 'keyword'},
            'show_uri': {'type': 'keyword'},
            'show_name': {'type': 'text'},
            'show_description': {'type': 'text'},
            'publisher': {'type': 'text'},
            'language': {'type': 'keyword'}
        }
    }

    client.indices.create('shows', body={'mappings': mappings})
    logger.info("Show index created")


def index_show():
    logger.info("Indexing shows...")
    data = []
    count = 0
    for show in Show.select():
        show_id = show.show_uri.replace('spotify:show:', '')
        data.append({
            '_index': 'shows',
            '_id': show_id,
            'id': show_id,
            'show_uri': show.show_uri,
            'show_name': show.show_name,
            'show_description': show.show_description,
            'publisher': show.publisher,
            'language': show.language
        })

        count += 1

        if count % 2000 == 0:
            helpers.bulk(client, data)
            data = []
            logger.info(f"Indexed {count} shows to ES")
    
    if len(data) != 0:
        helpers.bulk(client, data)
        logger.info(f"Indexed {count} shows to ES")


def create_episode_index():
    if client.indices.exists('episodes'):
        logger.info("`episodes` index existed. For integrity, it will now be"
                    "removed and replaced with new index")
        client.indices.delete('episodes')

    mappings = {
        'properties': {
            'id': {'type': 'keyword'},
            'show_uri': {'type': 'keyword'},
            'episode_uri': {'type': 'keyword'},
            'episode_name': {'type': 'text'},
            'episode_description': {'type': 'text'},
            'duration': {'type': 'double'},
            'transcripts': {'type': 'text'},
        }
    }

    client.indices.create('episodes', body={'mappings': mappings})
    logger.info("Episode index created")


def index_episodes():
    logger.info("Indexing episodes...")
    data = []
    count = 0
    for episode in Episode.select():
        episode_id = episode.episode_uri.replace('spotify:episode:', '')

        data.append({
            '_index': 'episodes',
            '_id': episode_id,
            'id': episode_id,
            'show_uri': episode.show_uri,
            'episode_uri': episode.episode_uri,
            'episode_name': episode.episode_name,
            'episode_description': episode.episode_description,
            'duration': episode.duration,
            'transcripts': ''
        })

        count += 1
        if count % 20000 == 0:
            helpers.bulk(client, data)
            data = []
            logger.info(f"Indexed {count} episodes to ES")

    if len(data) != 0:
        helpers.bulk(client, data)
        logger.info(f"Indexed {count} episodes to ES")


if __name__ == '__main__':
    db.connect()
    create_show_index()
    index_show()
    create_episode_index()
    index_episodes()
    db.close()


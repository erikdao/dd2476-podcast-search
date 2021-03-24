"""
Interface to Elasticsearch index
"""
from typing import Any
from tqdm import tqdm

from elasticsearch import Elasticsearch

from models import db, Episode, Show


client = Elasticsearch([
    {'host': 'localhost', 'port': 9200}
])


def create_show_index():
    if not client.indices.exists('shows'):
        mappings = {
            'properties': {
                'show_uri': {'type': 'keyword'},
                'show_name': {'type': 'text'},
                'show_description': {'type': 'text'},
                'publisher': {'type': 'text'},
                'language': {'type': 'keyword'}
            }
        }

        client.indices.create('shows', body={'mappings': mappings})
        print("Show index created")


def index_show():
    for show in tqdm(Show.select()):
        show_id = show.show_uri.replace('spotify:show:', '')
        body = {
            'show_uri': show_id,
            'show_name': show.show_name,
            'show_description': show.show_description,
            'publisher': show.publisher,
            'language': show.language
        }

        client.index(index='shows', id=show_id, body=body)


def create_episode_index():
    if not client.indices.exists('episodes'):
        mappings = {
            'properties': {
                'show_uri': {'type': 'keyword'},
                'episode_uri': {'type': 'keyword'},
                'episode_name': {'type': 'text'},
                'episode_description': {'type': 'text'},
                'duration': {'type': 'double'}
            }
        }

        client.indices.create('episodes', body={'mappings': mappings})
        print("Episode index created")


def index_episodes():
    print("Indexing episodes...")
    for episode in tqdm(Episode.select()):
        show_id = episode.show.show_uri.replace('spotify:show:', '')
        episode_uri = episode.episode_uri.replace('spotify:episode:', '')

        body = {
            'show_uri': show_id,
            'episode_uri': episode_uri,
            'episode_name': episode.episode_name,
            'episode_description': episode.episode_description,
            'duration': episode.duration
        }

        client.index(index='episodes', id=episode_uri, body=body)


if __name__ == '__main__':
    db.connect()
    create_show_index()
    index_show()
    # create_episode_index()
    # index_episodes()
    db.close()


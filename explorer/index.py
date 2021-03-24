"""
Interface to Elasticsearch index
"""
from typing import Any

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


def index_show():
    for idx, show in enumerate(Show.select()):
        show_id = show.show_uri.replace('spotify:show:', '')
        body = {
            'show_uri': show.show_uri,
            'show_name': show.show_name,
            'show_description': show.show_description,
            'publisher': show.publisher,
            'language': show.publisher
        }

        client.index(index='shows', doc_type='show', id=show_id, body=body)

        if idx > 0 and idx % 1000 == 0:
            print("Put %d docs to ES" % idx)


if __name__ == '__main__':
    db.connect()
    create_show_index()
    index_show()
    db.close()


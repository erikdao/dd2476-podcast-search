"""
This script extracts/combines the transcript for each episode and put it into Elasticsearch
"""
import os
import json
import typing

import elasticsearch
from elasticsearch import Elasticsearch, helpers

from logger import logger

client = Elasticsearch([
    {'host': 'localhost', 'port': 9200}
])

# First episode of Your IRL
SHOW_URI = 'spotify:show:0A21Tz1wA0RbFuWO2PjFgB'
EPISODE_ID = '3u8mo4u0XsMlDIMWOMjb3k'
EPISODE_URI = 'spotify:episode:3u8mo4u0XsMlDIMWOMjb3k'
EPISODE_JSON = 'data/podcasts-transcript/spotify-podcasts-2020/podcasts-transcripts/0/A/show_0A21Tz1wA0RbFuWO2PjFgB/3u8mo4u0XsMlDIMWOMjb3k.json'

def read_transcript() -> str:
    current_path = os.path.realpath(__file__)
    root_dir = os.path.dirname(os.path.dirname(os.path.dirname(current_path)))
    print('root_dir', root_dir)

    json_path = os.path.join(root_dir, EPISODE_JSON)
    with open(json_path, 'r') as f:
        json_data = json.load(f)
    
    results = json_data['results']
    transcripts = []

    for result in results:
        alternatives = result['alternatives']
        if not alternatives[0].get('transcript', None):
            print('No transcript', alternatives[0].keys())
        else:
            transcripts.append(alternatives[0]['transcript'])

    transcripts = "".join(transcripts)
    return transcripts

def index_transcript(transcripts: str) -> None:
    try:
        response = client.get(index='episodes', id=EPISODE_ID)
        # import pdb; pdb.set_trace()
        update_data = {
            '_index': response.get('_index'),
            '_id': response.get('_id'),
            **response.get('_source'),
        }
        update_data['transcripts'] = transcripts
        helpers.bulk(client, [update_data])
    except elasticsearch.exceptions.NotFoundError:
        logger.error(f"Cannot find document with id={EPISODE_ID}")


def main():
    transcripts = read_transcript()
    index_transcript(transcripts)


if __name__ == '__main__':
    main()
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
TRANSCRIPT_ROOT_PATH = 'data/podcasts-transcript/spotify-podcasts-2020/podcasts-transcripts/'

def read_transcript(json_path: str) -> str:
    with open(json_path, 'r') as f:
        json_data = json.load(f)
    
    results = json_data['results']
    transcripts = []

    for result in results:
        alternatives = result['alternatives']
        if not alternatives[0].get('transcript', None):
            # print('No transcript', alternatives[0].keys())
            continue
        else:
            transcripts.append(alternatives[0]['transcript'])

    transcripts = "".join(transcripts)
    return transcripts

def index_transcript(transcripts: str, episode_id: str) -> None:
    try:
        response = client.get(index='episodes', id=episode_id)
        # import pdb; pdb.set_trace()
        update_data = {
            '_index': response.get('_index'),
            '_id': response.get('_id'),
            **response.get('_source'),
        }
        update_data['transcripts'] = transcripts
        helpers.bulk(client, [update_data])
    except elasticsearch.exceptions.NotFoundError:
        logger.error(f"Cannot find document with id={episode_id}")

def read_all_json_paths(transcripts_path: str) -> None:
    # dir_path is the full path to the show directory
    # files is a list of all the files in the show directory
    show_directories = [(dir_path, files) for (dir_path, _, files) in os.walk(transcripts_path) if len(files) > 0]
    for directory in show_directories[0:2000]: # Change to whole show_directories
        for json_file in directory[1]:
            json_path = os.path.join(transcripts_path, f"{directory[0]}/{json_file}")
            transcript = read_transcript(json_path)
            if(len(transcript) > 0):
                episode_id = json_file.replace('.json', '')
                index_transcript(transcript, episode_id)


def main():
    current_path = os.path.realpath(__file__)
    root_dir = os.path.dirname(os.path.dirname(os.path.dirname(current_path)))
    transcripts_path = os.path.join(root_dir, TRANSCRIPT_ROOT_PATH)
    read_all_json_paths(transcripts_path)


if __name__ == '__main__':
    main()
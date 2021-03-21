import os
import sys
from tqdm import tqdm
from typing import *
from peewee import *

from .models import db


def main():
    db.connect()

    fname = sys.argv[1]
    if not os.path.exists(fname):
        raise ValueError(f'File not found {fname}')

    count = 0
    with open(fname, 'r') as f:
        next(f) # skip first header line
        instances = []

        for line in tqdm(f):
            line = line.strip()  # to get rid of `\n` character
            components = line.split('\t')
            if len(components) != 12:
                print(components)
                continue
            
            instances.append(dict(
                show_uri=components[0],
                show_name = components[1],
                show_description = components[2],
                publisher = components[3],
                language = components[4][2:len(components[4])-2],
                rss_link = components[5],
                episode_uri = components[6],
                episode_name = components[7],
                episode_description = components[8],
                duration = float(components[9]),
                show_filename_prefix = components[10],
                episode_filename_prefix = components[11]
            ))

            count += 1
            if count % 10000 == 0:
                Metadata.insert_many(instances).execute()
                instances = []
                print(f"Inserted {count} records") 

        if len(instances) != 0:
            Metadata.insert_many(instances).execute()

    db.close()

if __name__ == '__main__':
    main()


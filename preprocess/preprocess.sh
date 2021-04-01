#!/usr/bin/env bash

set -eux
set -o pipefail
set -o errexit

WORKING_DIR=./preprocess

pushd ${WORKING_DIR}

echo "Setting up database and load metadata"

poetry install

# DATA_DIR=data/metadata_samples.tsv
DATA_DIR="data/podcasts-transcript/spotify-podcasts-2020/metadata.tsv"

poetry run python metadata.py -f ${DATA_DIR}

echo "Finished."

echo "Creating Elasticsearch indexes"
poetry run python index.py

echo "Done."

popd

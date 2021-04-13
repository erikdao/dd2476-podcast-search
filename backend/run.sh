#!/usr/bin/env bash

set -eux
set -o pipefail
set -o errexit

echo "Building and starting development backend server"
./gradlew build && java -jar build/libs/*.jar
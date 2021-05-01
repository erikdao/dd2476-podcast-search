#!/usr/bin/env bash

set -eux
set -o pipefail
set -o errexit

echo "Building and starting development backend server"
./gradlew build --stacktrace && java -jar build/libs/*0.1.1*.jar
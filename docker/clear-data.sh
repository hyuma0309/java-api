#!/bin/sh
./stop-docker.sh
rm -rf ./minio/server/data/*
rm -rf ./mysql/server/data/*

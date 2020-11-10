#!/bin/sh

kill -9 $(lsof -i:80 -P)
pid=$(lsof -i:80 -P)
if [ $pid != null ]; then
  kill $pid
fi
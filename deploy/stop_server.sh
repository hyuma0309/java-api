#!/bin/sh

kill -9 $(lsof -t -i:80)
pid=$(lsof -t :80)
if [ $pid != null ]; then
  kill $pid
fi
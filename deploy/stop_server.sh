#!/bin/sh

kill -9 $(lsof -i:80)
pid=$(lsof -i :80)
if [ $pid != null ]; then
  kill $pid
fi
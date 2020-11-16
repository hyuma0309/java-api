#!/bin/sh

source /home/ec2-user/.bash_profile

kill -9 $(lsof -t -i:80)
pid=$(lsof -t :80)
echo &(lsof -t -i:80)
if [ $pid != null ]; then
  kill $pid
  echo &(lsof -t -i:80)
fi
#!/bin/sh

nohup java -jar -Dspring.profiles.active=production /home/ec2-user/asada-restapi-0.0.1-SNAPSHOT.jar

#!/bin/sh
source /home/ec2-user/.bash_profile

# apiを起動させる
sudo nohup java -jar -Dspring.profiles.active=production /home/ec2-user/asada-restapi-0.0.1-SNAPSHOT.jar &

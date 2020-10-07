#!/bin/sh


# apiを起動させる

sudo java -jar -Dspring.profiles.active=production /home/ec2-user/asada-restapi-0.0.1-SNAPSHOT.jar

cd home/ec2-user/asada-frontend/build

python -m SimpleHTTPServer 3000


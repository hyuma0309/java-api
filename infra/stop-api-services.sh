#!/bin/sh

source /home/ec2-user/.bash_profile

#　起動中のサービスの停止
sudo systemctl stop api.service

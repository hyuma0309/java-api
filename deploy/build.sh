readonly KEY_PAIR_ON_LOCAL="~/.ssh/asadahyuma-key.pem"
readonly INSTANCE_USER_NAME="ec2-user"
readonly PUBLIC_DNS_NAME="asadahyuma-alb-1711287778.ap-northeast-1.elb.amazonaws.com"


# EC2インスタンスにログインし、apiを起動させる
ssh -i ${KEY_PAIR_ON_LOCAL} ${INSTANCE_USER_NAME}@${PUBLIC_DNS_NAME} 'sudo systemctl restart api.service'

java -jar -Dgithub.clientId=${GITHUB_CLIENT_ID} -Dgithub.clientSecret=${GITHUB_CLIENT_SECRET} -Dspring.profiles.active=production ./build/libs/asada-restapi-0.0.1-SNAPSHOT.jar

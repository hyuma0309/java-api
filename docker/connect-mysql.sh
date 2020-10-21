#!/bin/sh

getContainerName() {
  docker ps -f name=mysql --format "{{.Names}}"
}
color() {
  colorCode=$1
  message="$2"
  DEFAULT_COLOR_CODE=$(printf '\033')
  printf "${DEFAULT_COLOR_CODE}[${colorCode}m${message}${DEFAULT_COLOR_CODE}[m"
}
red() {
  color 31 "${1}"
}
yellow() {
  color 33 "${1}"
}

MYSQL_CONTAINER_NAME="$(getContainerName)"
DOCKER_EXEC="docker exec -it ${MYSQL_CONTAINER_NAME} sh -c"
CONTAINER_MY_CNF_PATH="/root/.my.cnf"
WAIT_MAX=60
retryCount=0

# mysql-serverの起動を待つ
until ${DOCKER_EXEC} "mysqladmin ping --silent" > /dev/null 2>&1 ; do
  if [ ${retryCount} -ge ${WAIT_MAX} ]; then
    echo "$(red '[ERROR]') could not connect to MySQL"
    exit 1
  fi
  sleep 1
  retryCount=$(expr ${retryCount} + 1)
  echo "$(yellow '[WARN]') [$(printf "%02d\n" ${retryCount})/${WAIT_MAX}] Waiting for running..."
done

${DOCKER_EXEC} "mysql --defaults-extra-file=${CONTAINER_MY_CNF_PATH}"

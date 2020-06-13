
#!/bin/bash
set -e

# Arguments
ARG_FILE=$1
ARG_NUM_PROCESS_EXPECTED=$2

# Config Script
BLUE='\033[0;34m'
GREEN='\033[0;32m'
RED='\033[0;31m'
LIGHT_BLUE='\033[1;34m'
NO_COLOR='\033[0m'

# Config Kafka Infraestructure
ZOOKEEPER_HOST=$DOCKER_HOST_IP
ZOOKEEPER_PORT=2181

check_num_process(){
    echo "\n${BLUE}[ACTION]${NO_COLOR} Checking number of running processes";
    count_process_running=`docker-compose -f $1 ps | grep Up | wc -l | sed -e 's/^[ \t]*//'`
    echo "* Num Expected Processes [$ARG_NUM_PROCESS_EXPECTED]"
    echo "* Num Running Processes [$count_process_running]"

    if [ "$count_process_running" != "$ARG_NUM_PROCESS_EXPECTED" ]; then
        echo "${RED}[ERROR]${NO_COLOR} Invalid the number of processes for the infrastructure "
        #docker-compose -f $1 ps
        #docker-compose -f $ARG_FILE logs
        exit 1
    fi

    echo "${GREEN}[INFO]${NO_COLOR} Number of running processes checked"
}

show_brokers_used(){
    echo "\n${BLUE}[ACTION]${NO_COLOR} Showing brokers actives "
    command=`echo dump | nc "$ZOOKEEPER_HOST" "$ZOOKEEPER_PORT" | grep brokers | sed -e 's/^[ \t]*//'`
    echo $command
    sleep 1
    echo "${GREEN}[INFO]${NO_COLOR} Brokers actives shown"
}

echo "\nINFO KAFKA INFRASTRUCTURE\n"

echo "\n${BLUE}[ACTION]${NO_COLOR} Showing processes involved with file '$ARG_FILE'";
docker-compose -f $ARG_FILE ps
echo "${GREEN}[INFO]${NO_COLOR} Processes involved shown"

check_num_process $ARG_FILE $ARG_NUM_PROCESS_EXPECTED

show_brokers_used

echo "\n${GREEN}[INFO]${NO_COLOR} Info Kafka Infrastructure Success"

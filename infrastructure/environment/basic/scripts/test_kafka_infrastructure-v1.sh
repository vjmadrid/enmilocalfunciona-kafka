
#!/bin/bash
set -e

# Arguments
ARG_FILE=$1
ARG_IS_DELETE_TEST_TOPICS=${2:-false}

# Config Script
BLUE='\033[0;34m'
GREEN='\033[0;32m'
RED='\033[0;31m'
LIGHT_BLUE='\033[1;34m'
NO_COLOR='\033[0m'

# Config Kafka Infraestructure
ZOOKEEPER=$DOCKER_HOST_IP:2181
KAFKA_1=$DOCKER_HOST_IP:9092

REPLICATION_FACTOR=1
NUM_TEST_TOPICS=2
NAME_TEST_TOPIC_NAME="test-topic"
TOPIC_SELECTED="$NAME_TEST_TOPIC_NAME-1"
NUM_PARTITIONS=3
REPLICATION_FACTOR=1

NUM_MESSAGES=4


delete_test_topics(){
    echo "\n${BLUE}[ACTION]${NO_COLOR} Deleting if there are test topics with name '$NAME_TEST_TOPIC_NAME-.*'"
    if [ "$ARG_IS_DELETE_TEST_TOPICS" = true ]; then
        kafka-topics --delete --topic "$NAME_TEST_TOPIC_NAME-.*" --zookeeper $ZOOKEEPER
        echo "${GREEN}[INFO]${NO_COLOR} All test topics '$NAME_TEST_TOPIC_NAME-.*' deleted"
    fi
}

set_replication_factor(){

    echo "\n${BLUE}[ACTION]${NO_COLOR} Checking Replication Factor basen on Kafka Brokers used (if exist Kafka Broker with id 3)"
    if grep -q kafka-3 $ARG_FILE; then 
        REPLICATION_FACTOR=3
    fi
    echo "${GREEN}[INFO]${NO_COLOR} Replication Factor : $REPLICATION_FACTOR"

}

show_available_topics(){
    echo "\n${BLUE}[ACTION]${NO_COLOR} Showing available test topics"
    kafka-topics --list --zookeeper $ZOOKEEPER
    sleep 1
    echo "${GREEN}[INFO]${NO_COLOR} Available test topics shown"
}

create_test_topics(){
    echo "\n${BLUE}[ACTION]${NO_COLOR} Creating $NUM_TEST_TOPICS test topics with name '$NAME_TEST_TOPIC_NAME-*'"
    for ((i=1; i <= $NUM_TEST_TOPICS; i++)); do
        topic="$NAME_TEST_TOPIC_NAME-$i"
        create_command="kafka-topics --create --topic $topic --if-not-exists --replication-factor $REPLICATION_FACTOR --partitions $NUM_PARTITIONS --zookeeper $ZOOKEEPER"
        echo "${GREEN}[INFO]${NO_COLOR} $create_command"
        $create_command
    done
    echo "${GREEN}[INFO]${NO_COLOR} All test topics with name '$NAME_TEST_TOPIC_NAME-.*' created"
}

send_test_messages(){
    echo "\n${BLUE}[ACTION]${NO_COLOR} Sending $NUM_MESSAGES Messages for '$TOPIC_SELECTED' topic";
    for ((x=1; x <= $NUM_MESSAGES; x++)); do echo "Message-$x"; done | kafka-console-producer --broker-list $KAFKA_1 --topic $TOPIC_SELECTED

    # OPTION FILE   
    #kafka-console-producer --broker-list $KAFKA_1 --topic $TOPIC_SELECTED < messages.txt
    echo "${GREEN}[INFO]${NO_COLOR} $NUM_MESSAGES Messages sended"
}

receive_test_messages(){
    echo "\n${BLUE}[ACTION]${NO_COLOR} Receiving $NUM_MESSAGES Messages for '$TOPIC_SELECTED' topic"
    kafka-console-consumer --bootstrap-server $KAFKA_1 --topic $TOPIC_SELECTED --from-beginning  --max-messages $NUM_MESSAGES
    echo "${GREEN}[INFO]${NO_COLOR} $NUM_MESSAGES Messages sended"
    #--timeout-ms 7000
}

check_num_messages_received(){
    echo "\n${BLUE}[ACTION]${NO_COLOR} Checking messages Receives and Expected";
    count_messages_reveived=`kafka-console-consumer --bootstrap-server $KAFKA_1 --topic $TOPIC_SELECTED --from-beginning --max-messages $NUM_MESSAGES | wc -l | sed -e 's/^[ \t]*//' ` 
    echo "* Num Expected [$NUM_MESSAGES]"
    echo "* Num Received [$count_messages_reveived]"

    if [ "$count_messages_reveived" != "$NUM_MESSAGES" ]; then
        echo "${RED}[ERROR]${NO_COLOR} Invalid $NUM_MESSAGES Messages received"
        exit 1
    fi
}

show_topic_selected(){
    echo "\n${BLUE}[ACTION]${NO_COLOR} Showing test topic selected information with name '$TOPIC_SELECTED' "
    kafka-topics --describe --zookeeper $ZOOKEEPER --topic $TOPIC_SELECTED 
    sleep 1
    echo "${GREEN}[INFO]${NO_COLOR} Test topic selected information shown"
}


check_kafka_infrastructure(){
    delete_test_topics
    set_replication_factor
    create_test_topics
    show_available_topics
    show_topic_selected
    send_test_messages
    sleep 10
    receive_test_messages
    check_num_messages_received
}

echo "\nTEST KAFKA INFRASTRUCTURE\n"

check_kafka_infrastructure $1

echo "\n${GREEN}[INFO]${NO_COLOR} Test Kafka Infrastructure Success"


FROM openjdk:8u212-jre-alpine3.9

ADD target/*.jar consumer.jar

ENTRYPOINT java -jar consumer.jar \
           	--spring.kafka.bootstrap-servers=$BROKER \
            --message.processing.time=$MESSAGE_PROCESSING_TIME
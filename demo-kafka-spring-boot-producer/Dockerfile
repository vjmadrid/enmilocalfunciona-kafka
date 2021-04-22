FROM openjdk:8u212-jre-alpine3.9

ADD target/*.jar producer.jar

ENTRYPOINT java -jar producer.jar \
                    --spring.kafka.bootstrap-servers=$BROKER
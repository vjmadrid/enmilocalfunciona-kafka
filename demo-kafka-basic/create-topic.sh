#!/usr/bin/env bash

# Create a topic
kafka-topics --create \
--zookeeper localhost:2181 \
--replication-factor 1 \
--partitions 1 \
--topic my-topic

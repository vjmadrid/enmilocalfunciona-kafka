# Soporte de Ejecución de Kafka

Este documento sirve como ayuda en el uso de los comandos de Kafka


**Importante :** Asegurarse que la infraestructura esta "arrancada"

Existen diferentes formas de ejecutar los comandos proporcionados por Apache Kafka
```bash
# Método 1 : desde consola
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic XXX

# Método 2 : Ejecutando como sh
sh kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic XXX

# Método 3 : Ejecutando como sh con "./"
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic XXX

# Método 4 : Ejecutando como sh directo varias líneas
bin/kafka-topics.sh \
--create \
--zookeeper localhost:2181 \
--replication-factor 1 \
--partitions 1 \
--topic XXX

# Método 5 : Ejecutando como bat
KAFKA_HOME\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic XXX 
```

# Soporte de Consumer Group

Este documento sirve como ayuda en el uso de los comandos de Kafka


**Importante :** Asegurarse que la infraestructura esta "arrancada"


##Ejecutar un consumidor por consola con un grupo de consumo

```bash
bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic XXX \
--group YYY \
--from-beginning
```


##Listar todos los grupos de consumo (Consumer Group)

```bash
bin/kafka-consumer-groups.sh \
--bootstrap-server localhost:9092 \
--list
```


##Detalles de un Consumer Group (Consumer Group)

```bash
bin/kafka-consumer-groups.sh \
--bootstrap-server localhost:9092 \
--group test \
--describe
```
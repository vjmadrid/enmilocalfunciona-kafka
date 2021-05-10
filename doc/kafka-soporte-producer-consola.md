# Soporte de Producer por Consola

Este documento sirve como ayuda en el uso de los comandos de Kafka


**Importante :** Asegurarse que la infraestructura esta "arrancada"


Los parámetros utilizados son :

* **--broker-list:** Establece la lista de Brokers con los que trabajará
    * *--broker-list localhost:9092*
    * *--broker-list localhost:9092,localhost:9093,localhost:9094*
* **--topic:** Establece el nombre del topic



##Ejecutar productor por consola

```bash
bin/kafka-console-producer.sh \
--broker-list localhost:9092 \
--topic XXX
```

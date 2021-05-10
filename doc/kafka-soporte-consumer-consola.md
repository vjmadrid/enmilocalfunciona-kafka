# Soporte de Consumer por Consola

Este documento sirve como ayuda en el uso de los comandos de Kafka

**Importante :** Asegurarse que la infraestructura esta "arrancada"

Los parámetros utilizados son :

* **--bootstrap-server:** Establece la dirección de los Brokers con los que trabajara
* **--topic:** Establece el nombre del topic
* **--from-beginning:** Mostrará el contenido del topic desde el inicio de los datos recibidos en el topic
* **--partition:** Indicará sobre que partición trabajar
* **--group:** Indentificará un grupo de trabajo
* **--offset:** Indicará sobre que offset trabajar



##Ejecutar un consumidor por consola

```bash
bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic XXX
```



##Ejecutar un consumidor por consola desde el principio de los mensajes

```bash
bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic XXX \
--from-beginning
```


##Ejecutar un consumidor por consola desde el principio de los mensajes en una partición

```bash
bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--partition 3 \
--topic XXX \
--from-beginning
```


##Ejecutar un consumidor por consola desde un offset específico en una partición

```bash
bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--partition 3 \
--topic XXX \
--offset 0
```

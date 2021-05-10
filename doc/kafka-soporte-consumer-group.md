# Soporte de Consumer Group

>Nota
>
>Recordar tener las herramientas de gestión de Apache Kafka instaladas en el ordenador desde el que se va a utilizar. Normalmente estos scripts se ubican en el directorio de instalación (KAFKA_HOME).
>
> * **Entorno Unix:** Todos los ejecutables .sh se encuentran bajo el directorio %KAFKA_HOME%\bin
> * **Entorno Windows:** Todos los ejecutables .bat se encuentran bajo el directorio %KAFKA_HOME%\bin\windows
>
>Nota : Cuidado cuando se utiliza un gestor de instalaciones
>
>También se requiere tener la infraestructura de Apache Kafka "arrancada"

Este documento sirve como ayuda en el uso de los comandos de Kafka, en este caso se explicará detenidamente las posibilidades de utilizar grupos de consumo.

* Ejecutar un consumidor por consola con un grupo de consumo
* Listar todos los grupos de consumo (Consumer Group)
* Detalles de un Consumer Group (Consumer Group)

Los parámetros "comunes" utilizados son :

* **--bootstrap-server:** Establece la dirección de los Brokers con los que trabajara
* **--topic:** Establece el nombre del topic
* **--group:** Establece el nombre del grupo






## Ejecutar un consumidor por consola con un grupo de consumo

En este caso se explica como ejecutar un consumidor asociado a un grupo de consumo

```bash
# *** Ejemplos Linux / Mac ***

bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic XXX \
--group YYY \
--from-beginning



# *** Ejemplos Windows ***

bin\windows\kafka-console-consumer.bat \
--bootstrap-server localhost:9092 \
--topic XXX \
--group YYY \
--from-beginning
```





## Listar todos los grupos de consumo (Consumer Group)

```bash
# *** Ejemplos Linux / Mac ***

bin/kafka-consumer-groups.sh \
--bootstrap-server localhost:9092 \
--list



# *** Ejemplos Windows ***

bin\windows\kafka-consumer-groups.bat \
--bootstrap-server localhost:9092 \
--list
```





## Detalles de un Consumer Group (Consumer Group)

```bash
# *** Ejemplos Linux / Mac ***

bin/kafka-consumer-groups.sh \
--bootstrap-server localhost:9092 \
--group test \
--describe



# *** Ejemplos Windows ***

bin\windows\kafka-consumer-groups.bat \
--bootstrap-server localhost:9092 \
--group test \
--describe
```
# Soporte de Test de Rendimiento

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

Este documento sirve como ayuda en el uso de los comandos de Kafka, este caso se explicará detenidamente como hacer una prueba de rendimiento.

Hay que tener en cuenta que a la hora de realizar un test de rendimiento se tiene que tener muy clara la configuración de los diferentes elementos utilizados : Zookeeper, Brokers y Topics

**Ejemplo de propiedades de configuración de un broker**

```bash
broker.id=0  
listeners=PLAINTEXT://:9092  
log.dirs=C:/apache-kafka/kafka-logs/broker_0  
zookeeper.connect=localhost:2181 

num.network.threads=5
num.io.threads=8
socket.send.buffer.bytes=102400
socket.receive.buffer.bytes=102400
socket.request.max.bytes=104857600
...
```

* Ejecutar un productor de test de rendimiento
* Ejecutar un consumidor de test de rendimiento


## Ejecutar un productor de test de rendimiento

```bash
# *** Ejemplos Linux / Mac ***

bin/kafka-producer-perf-test.sh \
--topic xxx \
--num-records 1000 \
--throughput 5 \
--record-size 1000 \
--producer-props \
bootstrap.servers=localhost:9092


# *** Ejemplos Windows ***

bin\windows\kafka-producer-perf-test.bat \
--topic xxx \
--num-records 1000 \
--throughput 5 \
--record-size 1000 \
--producer-props \
bootstrap.servers=localhost:9092
```

## Ejecutar un consumidor de test de rendimiento

```bash
# *** Ejemplos Linux / Mac ***

bin/kafka-consumer-perf-test.sh \
--broker-list localhost:9092 \
--topic perf \
--messages 1000000


# *** Ejemplos Windows ***

bin\windows\kafka-consumer-perf-test.bat \
--broker-list localhost:9092 \
--messages 10000 \
--topic xxx \

```

# Soporte de Producer por Consola

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

Este documento sirve como ayuda en el uso de los comandos de Kafka, en este caso se explicará detenidamente las posibilidades de utilizar el productor facilitado para su uso por consola.

* Ejecutar un productor por consola

Los parámetros "comunes" utilizados son :

* **--broker-list:** Establece la lista de Brokers con los que trabajará
    * *--broker-list localhost:9092*
    * *--broker-list localhost:9092,localhost:9093,localhost:9094*
* **--topic:** Establece el nombre del topic





## Ejecutar un productor por consola

```bash
# *** Ejemplos Linux / Mac ***

bin/kafka-console-producer.sh \
--broker-list localhost:9092 \
--topic XXX


# *** Ejemplos Windows ***

bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic XXX
```

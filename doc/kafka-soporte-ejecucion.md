# Soporte de Ejecución de Kafka

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

Este documento sirve como ayuda en el uso de los comandos de Kafka, este caso se explicará detenidamente las posibilidades para su ejecución.

Existen diferentes formas de ejecutar los comandos proporcionados por Apache Kafka :

* Dependiende de la plataforma sobre la que se instala : Windows, Linux y/o Mac
* Dependiendo de si se instala ad-hoc o a partir de alguna herramienta de gestión de paquetes / instalaciones
* Dependiendo de si se ha instala sobre la línea de comandos o a partir de un script

Por lo que pueden existir muchas posibilidades

**Ejemplos de creación de un topic**

```bash
# Método 1 : desde consola para cualquier sistema
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic XXX

# Método 2 : Ejecutando como .sh (Linux / Mac)
sh kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic XXX

# Método 3 : Ejecutando como sh con "./" (Linux Mac)
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic XXX

# Método 4 : Ejecutando como sh directo varias líneas
kafka-topics.sh \
--create \
--zookeeper localhost:2181 \
--replication-factor 1 \
--partitions 1 \
--topic XXX

# Método 5 : Ejecutando como .bat (Windows)
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic XXX 

...
```

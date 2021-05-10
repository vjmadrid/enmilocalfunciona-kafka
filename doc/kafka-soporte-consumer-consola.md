# Soporte de Consumer por Consola

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

Este documento sirve como ayuda en el uso de los comandos de Kafka, en este caso se explicará detenidamente las posibilidades de utilizar el consumidor facilitado para su uso por consola.

* Ejecutar un consumidor por consola
* Ejecutar un consumidor por consola monstrando la clave
* Ejecutar un consumidor por consola desde el principio de los mensajes
* Ejecutar un consumidor por consola desde el principio de los mensajes en una partición
* Ejecutar un consumidor por consola desde un offset específico en una partición

Los parámetros "comunes" utilizados son :

* **--bootstrap-server:** Establece la dirección de los Brokers con los que trabajara
* **--topic:** Establece el nombre del topic
* **--from-beginning:** Mostrará el contenido del topic desde el inicio de los datos recibidos en el topic
* **--partition:** Indicará sobre que partición trabajar
* **--group:** Indentificará un grupo de trabajo
* **--offset:** Indicará sobre que offset trabajar
* **--property:** Facilitará establecer una propiedad





## Ejecutar un consumidor por consola

```bash
# *** Ejemplos Linux / Mac ***

bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic XXX



# *** Ejemplos Windows ***

bin\windows\kafka-console-consumer.bat \
--bootstrap-server localhost:9092 \
--topic XXX
```




## Ejecutar un consumidor por consola monstrando la clave

```bash
# *** Ejemplos Linux / Mac ***

bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--property print.key=true \
--topic XXX



# *** Ejemplos Windows ***

bin\windows\kafka-console-consumer.bat \
--bootstrap-server localhost:9092 \
--property print.key=true \
--topic XXX
```




## Ejecutar un consumidor por consola desde el principio de los mensajes

```bash
# *** Ejemplos Linux / Mac ***

bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic XXX \
--from-beginning



# *** Ejemplos Windows ***

bin\windows\kafka-console-consumer.bat \
--bootstrap-server localhost:9092 \
--topic XXX \
--from-beginning
```





## Ejecutar un consumidor por consola desde el principio de los mensajes en una partición

```bash
bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--partition 3 \
--topic XXX \
--from-beginning



# *** Ejemplos Windows ***

bin\windows\kafka-console-consumer.bat \
--bootstrap-server localhost:9092 \
--partition 3 \
--topic XXX \
--from-beginning
```





## Ejecutar un consumidor por consola desde un offset específico en una partición

```bash
# *** Ejemplos Linux / Mac ***

bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--partition 3 \
--topic XXX \
--offset 0



# *** Ejemplos Windows ***

bin\windows\kafka-console-consumer.bat \
--bootstrap-server localhost:9092 \
--partition 3 \
--topic XXX \
--offset 0
```

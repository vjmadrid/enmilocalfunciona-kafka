# Soporte de Gestión de Topics

Este documento sirve como ayuda en el uso de los comandos de Kafka


**Importante :** Asegurarse que la infraestructura esta "arrancada"



##Crear un Topic

Permite crear un topic con una configuración específica

Primero de todo se requiere tener claros ciertos aspectos de la configuración de un topic como : replication-factor, partitions, ...

Los parámetros utilizados son :

* **--create:* Indica que la acción a realizar es la creación
* **--zookeeper:** Establece la dirección del Zookeeper con la que trabajara
* **--replication-factor:** Si Kafka se está ejecutando en un clúster, esto determina en cuántos brokers se replicará una partición (En este caso 1)
* **--partitions:** Define cuántas particiones habrá en un topic (En este caso 1)
* **--topic:** Establece el nombre del topic (En este caso "xxx")


Según cada caso de instalación utilizar alguno de los métodos siguientes :

```bash
# *** Ejemplos Linux / Mac ***

# Referencia a Zookeeper
bin/kafka-topics.sh \
--create \
--zookeeper localhost:2181 \
--replication-factor 1 \
--partitions 1 \
--topic XXX

# Referencia a Brokers
bin/kafka-topics.sh \
--create \
--bootstrap-server localhost:9092 \
--replication-factor 1 \
--partitions 1 \
--topic XXX



# *** Ejemplos Windows ***

# Referencia a Zookeeper
KAFKA_HOME\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic XXX

# Referencia a Brokers
KAFKA_HOME\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic XXX 
```

* Verificar que se ha creado correctamente con algún mensaje como "Created topic XXX."





##Listar todos los Topics

Muestra todos los topics que se encuentran dados de alta en Zookeeper

Los parámetros utilizados son :

* **--list:** Indica que la acción a realizar es el listado de topics
* **--zookeeper :** Establece la dirección del Zookeeper con la que trabajará
    * *--zookeeper localhost:2181*
* **--bootstrap-server :** Establece la dirección de los Brokers con los que se trabajará
    * *--bootstrap-server localhost:9092*
    * *--bootstrap-server localhost:9092,localhost:9093,localhost:9094*


```bash
# *** Ejemplos Linux / Mac ***

# Referencia a Zookeeper
bin/kafka-topics.sh \
--list \
--zookeeper localhost:2181

# Referencia a Brokers
bin/kafka-topics.sh \
--list \
--bootstrap-server localhost:9092



# *** Ejemplos Windows ***

# Referencia a Zookeeper
%KAFKA_HOME%\bin\windows\kafka-topics.bat --list --zookeeper localhost:2181

# Referencia a Brokers
%KAFKA_HOME%\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
```





##Detalle de un Topic

Permite ver la información detallada sobre el uso y configuración de un topic

Los parámetros utilizados son :

* **--describe:** Indica que la acción a realizar es la descripción
* **--zookeeper :** Establece la dirección del Zookeeper con la que trabajará
    * *--zookeeper localhost:2181*
* **--bootstrap-server :** Establece la dirección de los Brokers con los que se trabajará
    * *--bootstrap-server localhost:9092*
    * *--bootstrap-server localhost:9092,localhost:9093,localhost:9094*
* **--topic:** Establece el nombre del topic

Información que se muestra :

* **Leader:** Nodo responsable de todas las lecturas y escrituras de una proporción
* **Replicas:** Lista de nodos que replican el mensaje para esta partición independiente de si es leader o incluso si están activa
* **ISR:** Conjunto de "in-sync" réplicas

```bash
# *** Ejemplos Linux / Mac ***

# Referencia a Zookeeper
bin/kafka-topics.sh \
--describe \
--zookeeper localhost:2181
--topic XXX

# Referencia a Brokers
bin/kafka-topics.sh \
--describe \
--bootstrap-server localhost:9092 \
--topic XXX



# *** Ejemplos Windows ***

# Referencia a Zookeeper
KAFKA_HOME\bin\windows\kafka-topics.bat --describe --zookeeper localhost:2181 --topic XXX

# Referencia a Brokers
KAFKA_HOME\bin\windows\kafka-topics.bat --describe --bootstrap-server localhost:9092 --topic XXX
```





##Borrar un Topic

Permite borrar un topic

Para que el borrado sea "real" tiene que tener la siguiente propiedad activa : delete.topic.enable=true

Los parámetros utilizados son :

* **--delete:** Indica que la acción a realizar es la eliminación
* **--zookeeper :** Establece la dirección del Zookeeper con la que trabajará
    * *--zookeeper localhost:2181*
* **--bootstrap-server :** Establece la dirección de los Brokers con los que se trabajará
    * *--bootstrap-server localhost:9092*
    * *--bootstrap-server localhost:9092,localhost:9093,localhost:9094*
* **--topic:** Establece el nombre del topic

```bash
# *** Ejemplos Linux / Mac ***

# Referencia a Zookeeper
bin/kafka-topics.sh \
--delete \
--zookeeper localhost:2181
--topic XXX

# Referencia a Brokers
bin/kafka-topics.sh \
--delete \
--bootstrap-server localhost:9092 \
--topic XXX



# *** Ejemplos Windows ***

# Referencia a Zookeeper
KAFKA_HOME\bin\windows\kafka-topics.bat --delete --zookeeper localhost:2181 --topic XXX

# Referencia a Brokers
KAFKA_HOME\bin\windows\kafka-topics.bat --delete --bootstrap-server localhost:9092 --topic XXX
```



##Modificar un Topic

Existe la posibilidad de tener que modificar la configuración de un topic, para ellos existe el parámetro --alter.

Para ello hay que tener claro una serie de normas :

* Se pueden añadir particiones (y las particiones actuales no cambian) pero NO se pueden eliminar
* No se puede cambiar el factor de replicación
* Se le pueden pasar otros parámetros de configuración con --config
* Se le pueden eliminar ciertos parámetros de configuración con --deleteconfig

# demo-kafka-basic

Este proyecto representa un ejemplo básico de uso de **Kafka** con la condición de utilizar el soporte proporcionado por la SDK de Kafka (con la librería **kafka-clients**).

Este proyecto se compone de diferentes productores y consumidores que hacen uso de elementos de esa librería tratados como clases main independientes.

Se trabajará con un nº estipulado de mensajes del tipo : Hello World! ID_PROPIO - FECHA_ENVIO

Este proyecto destaca por propocionar :

* **Clases básicas del tipo productor / emisor**
* **Clases básicas del tipo consumidor / receptor**
* **Configuración Nativa** para el uso con Kafka en cada una de las clases tipo 





## Stack Tecnológico

* Java 8
* [Maven 3](https://maven.apache.org/) - Gestión de Dependencias
* [Apache Kafka ](https://kafka.apache.org/)

Dependencias de arquitectura

N/A

Dependencias de terceros

* **kafka-clients** [2.4.1] : Cliente para trabajar con Apache Kafka
* **logback** [1.2.3] : Framework para implementar el logging
* **slf4j** [1.7.25] : Framework para abstracciones sobre el logging




Importante :

* Requiere alinear la versión del cliente con el Kafka broker para poder conectarlo





## Prerrequisitos

Define que elementos son necesarios para instalar el software

* Java 8 instalado (1.5+ version requerida)
* Maven installed  (3+)
* Infraestructura Kafka + Topics (Adhoc o mediante contenedores)





## Instalación

N/A






## Testing

Este proyecto NO dispone de tests  : Unit + Integration





## Deploy

N/A





## Uso

Se ejecutarán las clases Java de forma individual segun cada uno de los casos

En muchos casos requiere montar o utilizar alguno de sus componentes de origen o destino de datos


Cada caso busca objetivos diferentes





## Configuración Apache Kafka 

* Requiere tener una instalación / configuración de Apache Kafka (adhoc o mediante contenedores)

* Requiere utilizar las utilidades de Kafka para su gestión -> Localizar donde se encuentran

* Crear el siguiente topic (que se encuentran referenciados como constantes en cada una de las clases o bien en un fichero de propiedades) :





###Crear Topic "topic-1"

Según cada caso de instalación utilizar alguno de los métodos siguientes :

```bash
# Método 1
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-1

# Método 2
sh kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-1

# Método 3
bin/kafka-topics.sh \
--create \
--zookeeper localhost:2181 \
--replication-factor 1 \
--partitions 1 \
--topic topic-1
```

* Verificar que se ha creado correctamente con algún mensaje como "Created topic topic-1."





## Versionado

**Nota :** [SemVer](http://semver.org/) es utilizado por el versionado

Para ver las versiones disponibles ver los tags del repositorio





## Autores

* **Víctor Madrid**

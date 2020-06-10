# basic

Contenedor Docker para el uso de la base de datos  **MySQL**

Representa una instalación básica y standalone de un entorno **Apache Kafka** básico con conetenedores **Docker** y gestionados por un fichero "docker-compose"

Se cubren diferentes instalaciones / configuraciones :

* zk-single-kafka-single : Una instancia de Zookeeper y una instancia de un Broker Kafka
* zk-single-kafka-multiples : Una instancia de Zookeeper y varias instancias (3) de un Broker Kafka
* zk-multiple-kafka-single : Varias instancias (3) de Zookeeper y una instancia de un Broker Kafka
* zk-multiple-kafka-multiple : Varias instancias (3) de Zookeeper y varias instancias (3) de un Broker Kafka

https://docs.confluent.io/current/installation/docker/installation/index.html


https://hub.docker.com/r/confluentinc/cp-kafka

https://github.com/confluentinc
https://github.com/confluentinc/examples
https://github.com/confluentinc/cp-all-in-one

https://docs.confluent.io/current/installation/docker/config-reference.html
https://docs.confluent.io/current/installation/docker/operations/logging.html

docker-compose -f docker-compose-zookeeper.yaml up -d

# 移除所有Zookeeper服务
docker-compose -f docker-compose-zookeeper.yaml rm -sf

echo stat | nc 127.0.0.1 2181



# Verificar que procesos estan disponibles
docker-compose -f zk-single-kafka-single.yml ps

# Verificar el numero de elementos
docker-compose -f $1 ps | grep Up | wc -l



## Stack Tecnológico

* [Docker](https://www.docker.com/) - Technología de Contenedores/Containers
* [Docker Hub](https://hub.docker.com/) - Repositorio de Docker Publico
* [MySQL](https://www.mysql.com/) - Base de Datos relacional (Version 5.7)

Dependencias con Proyectos de Arquitectura

N/A

Dependecias con Terceros

N/A





## Prerrequisitos

Define que elementos son necesarios para instalar el software

* Docker instalado (19+)





## Instalación

### Docker Compose



#### zk-single-kafka-single

Esta es la configuración más básica y permite hacer pruebas rápidas de comunicaciones, configuraciones, etc.

Configuración del fichero "docker-compose.yaml"

```bash
```

* Proporciona 1 Zookeeper en la úbicación : $DOCKER_HOST_IP:2181
* Proporciona 1 nodo de Kafka en la ubicación : $DOCKER_HOST_IP:9092



En este fichero se establece el constructor de la imágen que se utilizará, se establecerán una serie de variables de entorno necesarias para su ejecución, se definirán una serie de volúmenes y se publicará por el puerto específico de la aplicación

Pasos a seguir


1. Localizar el directorio principal del proyecto : <PROJECT_PATH> (infrastructure/environment/basic)

2. Ejecutar el siguiente comando

```bash
docker-compose up --build

ó

docker-compose up --build -d
```

3. Comprobar que la imágen ha sido creada

Verificar que parece como imágen Docker el nombre "mysql_test"

4. Comprobar que la aplicación ha sido desplegada correctamente

Verificar mediante un cliente de base datos que la conexión se puede realizar






## Pruebas

N/A





## Despliegue

N/A





## Uso

N/A





## Versionado

**Nota :** [SemVer](http://semver.org/) es usado para el versionado.





## Autores

* **Víctor Madrid**

# basic

Este área representa la funcionalidad que permite montar la infraestructura a utilizar basada en una instalación básica y standalone de un entorno **Apache Kafka** básico con contenedores **Docker** y gestionados por un fichero "docker-compose"

Se han definido diferentes implementaciones de su uso basñadas en la incorporación de un tipo de Zookeeper concreto

* zookeeper-3.4.9
* cp-zookeeper-5.5.0

Cada cada una de ellas se han implementado diferentes escenarios de uso mediante ficheros "docker-compose-yaml" (cubren diferentes instalaciones / configuraciones)

* **zk-single-kafka-single :** 1 Zookeeper y 1 Broker Kafka
* **zk-single-kafka-multiple :** 1 Zookeeper y 3 Brokers Kafka 
* **zk-multiple-kafka-single :** 3 Zookeepers y 1 Broker Kafka 
* **zk-multiple-kafka-multiple :** 3 Zookeepers y 3 Brokers Kafka 






## Stack Tecnológico

* Java 8
* [Docker](https://www.docker.com/) - Tecnología de Contenedores/Containers
* [Docker Hub](https://hub.docker.com/) - Repositorio de Docker Publico
* [Zookeeper](https://zookeeper.apache.org/) - Gestor centralizado para componentes distribuidos
* [Kafka](https://kafka.apache.org/) - Plataforma de Streaming distribuida

Dependencias con Proyectos de Arquitectura

N/A

Dependecias con Terceros

N/A





## Prerrequisitos

Define que elementos son necesarios para instalar el software

* Docker instalado (19+)





## Instalación



### Variables de entorno

Para la configuración centralizada de los ficheros de docker-compose utilizados se ha establecido una variable de entorno 

```bash
# General (Es el valor por defecto y por lo tanto en muchos casos NO hace falta ni añadirla)
export DOCKER_HOST_IP=127.0.0.1

# PAra algunas versiones de Docker para MAC o para Docker Toolbox for Windows su valor cambia
export DOCKER_HOST_IP=192.168.99.100
```



### Docker Compose

Cada escenario dispone de un fichero "docker-compose.yml"


Pasos a seguir


1. Localizar el directorio de la imeplmentación a utilizar : <PROJECT_PATH> (infrastructure/environment/basic/xxx)

2. Ejecutar el siguiente comando

```bash
docker-compose -f <ESCENARIO> up

ó

docker-compose -f <ESCENARIO> up -d
```

Donde "ESCENARIO" es la referencia al fichero a utilizar y el parámetro "-d" define si se quieres establecer en modo background


![]()

3. Comprobar que las imágenes utilizadas han sido creadas

4. Comprobar que los contenedores han sido creados

```bash
# Si NO existen más contenedores
docker ps

ó

# Si SOLO se quieren mostrar los contenedores creados
docker-compose -f <ESCENARIO> ps
```





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

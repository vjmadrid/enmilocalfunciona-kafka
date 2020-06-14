# basic

Esta área representa la funcionalidad que permite montar la infraestructura a utilizar basada en una instalación básica y standalone de un entorno **Apache Kafka** básico con contenedores **Docker** y gestionados por un fichero "docker-compose"

Se han definido diferentes implementaciones de su uso basadas en la incorporación de un tipo de Zookeeper concreto

* zookeeper-3.4.9
* cp-zookeeper-5.5.0

Cada una de ellas se han implementado diferentes escenarios de uso mediante ficheros "docker-compose-yaml" (cubren diferentes instalaciones / configuraciones)

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

Dependencias con Terceros

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

# Psra algunas versiones de Docker para MAC o para Docker Toolbox for Windows su valor cambia
export DOCKER_HOST_IP=192.168.99.100
```



### Docker Compose

Cada escenario dispone de un fichero "docker-compose.yml"


Pasos a seguir


1. Localizar el directorio de la implementación a utilizar : <PROJECT_PATH> (infrastructure/environment/basic/xxx)

2. Ejecutar el siguiente comando

```bash
docker-compose -f <ESCENARIO> up

ó

docker-compose -f <ESCENARIO> up -d
```

Donde "ESCENARIO" es la referencia al fichero a utilizar y el parámetro "-d" define si se quieres establecer en modo background


![Ejemplo de Up del escenario zk-single-kafka-single](https://github.com/vjmadrid/enmilocalfunciona-kafka/blob/master/infrastructure/environment/basic/images/zk-single-kafka-single-up.png)

3. Comprobar que las imágenes utilizadas han sido creadas

4. Comprobar que los contenedores han sido creados

```bash
# Si NO existen más contenedores
docker ps

ó

# Si SOLO se quieren mostrar los contenedores creados
docker-compose -f <ESCENARIO> ps
```

![Ejemplo de PS del escenario zk-single-kafka-single](https://github.com/vjmadrid/enmilocalfunciona-kafka/blob/master/infrastructure/environment/basic/images/zk-single-kafka-single-ps.png)





## Pruebas

Se han habilitado dos scripts para ayudar a entender y probar los entornos Kafka:

* info-kafka-infrastructure-v1
* test-kafka-infrastructure-v1

Los scripts se ubican en el [repositorio](https://github.com/vjmadrid/enmilocalfunciona-kafka/tree/master/infrastructure/environment/basic/scripts)


### info-kafka-infrastructure-v1

Script informativo utilizado para :

* Mostrar los procesos involucrados en un fichero "docker-compose"
* Validar el nº de procesos ejecutados respecto a un parámetro esperado
* Mostrar los brokers disponibles

Pasos a seguir :


1. Localizar el directorio de la imeplmentación a utilizar : <PROJECT_PATH> (infrastructure/environment/basic/script)

2. Ejecutar el siguiente comando

```bash
sh info-kafka-infrastructure-v1.sh <ESCENARIO> <NUM_PROCESOS_ESPERADOS>


# Ejemplo
sh info-kafka-infrastructure-v1.sh ../zookeeper-3.4.9/zk-single-kafka-single.yml 2  
```

![Ejemplo de INFO del escenario zk-single-kafka-single](https://github.com/vjmadrid/enmilocalfunciona-kafka/blob/master/infrastructure/environment/basic/images/zk-single-kafka-single-info.png)


### test-kafka-infrastructure-v1

Script de testing de la infraestructura utilizado para :

* Probar la creación y eliminación de topics
* Probar el envío de mensajes
* Probar la recepción de mensajes
* Validar el número de mensajes recibidos para validar la prueba


1. Localizar el directorio de la implementación a utilizar : <PROJECT_PATH> (infrastructure/environment/basic/script)

2. Ejecutar el siguiente comando

```bash
sh test-kafka-infrastructure-v1.sh <ESCENARIO> <FLAG_BORRADO_INICIAL_TOPICS_TEST>


# Ejemplo
sh test-kafka-infrastructure-v1.sh ../zookeeper-3.4.9/zk-single-kafka-single.yml false
```

![Ejemplo de TEST del escenario zk-single-kafka-single](https://github.com/vjmadrid/enmilocalfunciona-kafka/blob/master/infrastructure/environment/basic/images/zk-single-kafka-single-test.png)





## Despliegue

N/A





## Uso

N/A





## Versionado

**Nota :** [SemVer](http://semver.org/) es usado para el versionado.





## Autores

* **Víctor Madrid**

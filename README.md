# enmilocalfunciona-kafka

Este repositorio se encarga de servir como estructura de recursos utilizados para los artículos publicados en la plataforma **enmilocalfunciona.io** relacionados con el uso de **[Apache Kafka](https://kafka.apache.org/)**

* [Web Principal](https://kafka.apache.org/)
* [Documentación](https://kafka.apache.org/documentation/)
* [Repositiorio de código Github](https://github.com/apache/kafka)
* [Repositorio de contenedores Docker Hub Zookeeper](https://hub.docker.com/_/zookeeper)
* [Repositorio de contenedores Docker Hub Apache Kakfa Zookeeper](https://hub.docker.com/r/confluentinc/cp-zookeeper)
* [Repositorio de contenedores Docker Hub Apache Kakfa](https://hub.docker.com/r/confluentinc/cp-kafka)




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

* Java 8 instalado (1.5+ version requerido)
* Docker instalado (19+)





Se encuentra organizado en áreas :

* infrastructure





## infrastructure

Directorio encargado de almacenar todos aquellos aspectos que puedan ayudar a la hora de realizar la instalación y/o uso de una infraestructura de Apache Kakfa

Actualmente sólo dispone de un área : "environment/basic"


### environment/basic

Se definen las implementaciones utilizadas así como herramientas de soporte/ayuda


* zookeeper-3.4.9
* cp-zookeeper-5.5.0

Cada cada una de ellas se han implementado diferentes escenarios de uso mediante ficheros "docker-compose-yaml"

* **zk-single-kafka-single :** 1 Zookeeper y 1 Broker Kafka
* **zk-single-kafka-multiple :** 1 Zookeeper y 3 Brokers Kafka 
* **zk-multiple-kafka-single :** 3 Zookeepers y 1 Broker Kafka 
* **zk-multiple-kafka-multiple :** 3 Zookeepers y 3 Brokers Kafka 





## Autor

* **Víctor Madrid**

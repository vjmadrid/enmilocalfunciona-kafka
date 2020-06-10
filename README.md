# enmilocalfunciona-kafka

Este repositorio se encarga de servir como estructura de recursos utilizados para los artículos publicados en la plataforma **enmilocalfunciona.io** relacionados con el uso de **[Apache Kafka](https://kafka.apache.org/)**

* [Web Principal](https://kafka.apache.org/)
* [Documentación](https://kafka.apache.org/documentation/)
* [Repositiorio de código Github](https://github.com/apache/kafka)
* [Repositorio de contenedores Docker Hub Zookeeper](https://hub.docker.com/_/zookeeper)
* [Repositorio de contenedores Docker Hub Apache Kakfa](https://hub.docker.com/r/confluentinc/cp-kafka)




## Stack Tecnológico

* Java 8
* [Docker](https://www.docker.com/) - Technología de Contenedores/Containers
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





## infraestructure

Directorio encargado de almacenar los ficheros "docker-compose-yaml" que permiten montar cada uno de los entornos kafka que se utilizarán

Lo entornos (environment) utilizados son :

* basic : incorpora las piezas de Zookeeper y un Nodo Kafka con diferentes configuraciones

IMPORTANTE : Seguir en detalle el fichero explicativo de cada caso.




## Autor

* **Víctor Madrid**

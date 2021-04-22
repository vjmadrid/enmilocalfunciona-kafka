# demo-kafka-spring-boot-producer

Este proyecto representa un ejemplo de uso de un productor con **Kafka** con **Spring + Spring Boot** y su soporte

Este proyecto se compone de un productor 

* SpringKafkaApplication : Dispara una ejecución de mensaje planificada cada 2 segundos
* SpringKafkaLimitApplication : Dispara un nº limitado de mensajes cada 2 segundoss


Se trabajará con un nº estipulado de mensajes del tipo : Hello World! <ID> - <FECHA>


Este proyecto destaca por propocionar :

* **Clase básica de productor / emisor** basada en Spring
* **Configuración de Spring** para el uso con Kafka
* **Varios ejecutores del Productor** (Diferentes clases Application)
* **Dockerfile** Fichero de dockerización de la aplicación





## Stack Tecnológico

* Java 8
* [Maven 3](https://maven.apache.org/) - Gestión de Dependencias
* [Docker](https://www.docker.com/) - Tecnología de contenedores
* [Apache Kafka ](https://kafka.apache.org/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring](https://spring.io)

Dependencias de arquitectura

N/A

Dependencias de terceros

* **spring-boot-starter-parent** [2.3.4.RELEASE] : Spring Boot + Spring Framework 
* **spring-boot-starter-test** [Boot 2.3.4.RELEASE] : Starter del Framework de Spring para realizar testing
* **spring-kafka** [Boot 2.3.4.RELEASE] : Integración de Spring - Kafka
* **spring-kafka-test** [Boot 2.3.4.RELEASE] : Soporte para testing de Kafka con Spring




Importante :

* La versión de Spring Kafka esta enlaza con la versión del cliente Kafka Version 
* Requiere alinear Spring Kafka con el Kafka broker para poder conectarlo
* [Compatibilidad](https://spring.io/projects/spring-kafka#kafka-client-compatibility)





## Prerrequisitos

Define que elementos son necesarios para instalar el software

* Java 8 instalado (1.5+ version requerida)
* Maven installed  (3+)
* Docker installed (19+)
* Infraestructura Kafka + Topics (Adhoc o mediante contenedores)





## Instalación

Pasos a seguir

* Arrancar un terminal
* Localizar el PATH de instalación (el lugar donde esta el proyecto localizado)
* Verificar que el fichero "pom.xml" esta disponible

Ejecutar el siguiente comando

```bash
mvn clean install
```

El resultado será la generación de un artefacto en el repositorio Maven Local






## Testing

Este proyecto NO dispone de tests  : Unit + Integration





## Deploy

Spring Boot

* Método de Uso 1 : Ejecutar Application (Fichero Spring Boot)
* Método de Uso 2 : Ejecutar Spring Boot desde Plugin
* Método de Uso 3 : Ejecutar JAR



### Método de Uso 1 : Ejecutar Application (Fichero Spring Boot)

1. Ejecutar el fichero Application.java -> En este caso "SpringKafkaApplication"

* Opción 1 : Por defecto
* Opción 2 : Con configuración desde el IDE "Run Configurations" -> En algunos casos puede ser interesante establecer el "profile" con -Dspring.profiles.active=<id_profile>



### Método de Uso 2 : Ejecutar Spring Boot desde Plugin

1. Ejecutar el siguiente comando

```bash
mvn spring-boot:run
```

Opcional : Usar perfiles / profile



### Método de Uso 3 : Ejecutar JAR


1. Ejecutar los siguientes comandos

```bash
mvn package

or

mvn package -P<id_profile>
```

Opcional : Usar perfiles / profile

Ejecutar

```bash
java -jar target/demo-kafka-spring-boot-producer-0.0.1-SNAPSHOT.jar
```





## Uso

Uso como librería custom



## Configuración Apache Kafka 

* Requiere tener una instalación / configuración de Apache Kafka (adhoc o mediante contenedores)

* Requiere utilizar las utilidades de Kafka para su gestión -> Localizar donde se encuentran

* Crear el siguiente topic (que se encuentran referenciados como constantes en cada una de las clases o bien en un fichero de propiedades) :


**topic-1**

Según cada caso de instalación utilizar alguno de los métodos

```bash
# Metodo 1
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-1

# Metodo 2
sh kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-1
```

* Verificar que se ha creado correctamente con algún mensaje como "Created topic topic-1."





## Versionado

**Nota :** [SemVer](http://semver.org/) es utilizado por el versionado

Para ver las versiones disponibles ver los tags del repositorio





## Autores

* **Víctor Madrid**

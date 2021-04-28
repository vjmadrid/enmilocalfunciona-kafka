# demo-kafka-spring-boot-testing

This project represents a basic example of working with **Kafka** and **Testing**

Send and receive "Hello World! "+new Date() message

This projects stands out for:

* Provides **projets class** : receiver y sender
* Different types of testing : EmbeddedKafkaRule, @EmbeddedKafka,...
* Different strategies: basic and advanced
* New Feature (Pending) : Kafka in Docker  

		<dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>testcontainers</artifactId>
            <version>${testcontainers.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>kafka</artifactId>
            <version>${testcontainers.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${testcontainers.version}</version>
            <scope>test</scope>
        </dependency>





## Stack Tecnológico

* Java 8
* [Maven 3](https://maven.apache.org/) - Gestión de Dependencias
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring](https://spring.io)
* [Docker](https://www.docker.com/) - Tecnología de contenedores
* [Apache Kafka ](https://kafka.apache.org/)

Dependencias de arquitectura

N/A

Dependencias de terceros

* **spring-boot-starter-parent** [2.3.4.RELEASE] : Spring Boot + Spring Framework 
* **spring-boot-starter-test** [Boot 2.3.4.RELEASE] : Starter del Framework de Spring para realizar testing
* **spring-kafka** [Boot 2.3.4.RELEASE] : Integración de Spring - Kafka
* **spring-kafka-test** [Boot 2.3.4.RELEASE] : Soporte para testing de Kafka con Spring

* **kafka-junit** [4.1.8] : JUnit rule to spin-up a Kafka broker during tests



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

Este proyecto dispone de tests  : Unit + Integration





## Deploy

Spring Boot

* Deploy Method 1 : Application (Spring Boot File)
* Deploy Method 2 : Spring Boot Run
* Deploy Method 3 : Execute JAR



### Deploy Method 1 : Application (Spring Boot File)

1. Execute Application.java File

* Default 
* Configure Java "Run Configurations" IDE -> Use "Environment" with -Dspring.profiles.active=<id_profile>


### Deploy Method 2 : Spring Boot Run

1. Execute the following command

```bash
mvn spring-boot:run
```

Optional : use profile


### Deploy Method 3 : Execute JAR

Use Spring profiles with Maven Profiles -> Special Integration

* spring.profiles.active=@spring.profiles.active@
* enable resource filtering

Package the application in a single/fat JAR file (executable JAR + All dependencies + Embedded Servlet Container if its a web applications)

To run the jar file use the following command 

In this case define : "dev", "uat" and "prod"

1. Execute the following command

```bash
mvn package

or

mvn package -P<id_profile>
```

Execute

```bash
java -jar target/demo-kafka-spring-boot-testing-0.0.1-SNAPSHOT.jar
```

Use default environment -> dev or <id_profile> environment






## Uso

Uso como librería custom



## Configuración Apache Kafka 

* Requiere tener una instalación / configuración de Apache Kafka (adhoc o mediante comparadores)

* Requiere utilizar las utilidades de Kafka

* Crear el siguiente topic (que se encuentran referenciados como constantes en cada una de las clases) :


**topic-1**

Según cada caso de instalación utilizar alguno de los métodos

```bash
# Metodo 1
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-1

# Metodo 2
sh kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-1
```






## Versionado

**Nota :** [SemVer](http://semver.org/) es utilizado por el versionado

Para ver las versiones disponibles ver los tags del repositorio





## Autores

* **Víctor Madrid**

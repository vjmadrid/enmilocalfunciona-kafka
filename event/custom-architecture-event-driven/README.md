# custom-architecture-event-driven

Este proyecto representa una **Librería de Arquitectura (dependencia)** a nivel **GLOBAL** relacionada con la creación de un **modelo de gestión de eventos** (event driven) para desarrollar las diferentes partes de un proyecto (standalone, módulos, librerías, etc.) de una forma homogénea.

Esta librería destaca por proporcionar :

* **Clases de constantes**
* Un **modelo de evento genérico**
* Una **tipología para eventos**
* **Clases de utilidades** : factory, validators, converters, etc.
* Una **excepción para un evento genérico** : "AcmeEventDrivenException" para tener diferentes excepciones por tipo dentro de un proyecto



Condiciones de construcción / despliegue :

* Uso como librería en otros proyectos
* Despliegue como librería





## Stack Tecnológico

* Java 8
* [Maven 3](https://maven.apache.org/) - Gestión de Dependencias

Dependencias de arquitectura

N/A

Dependencias de terceros 

* **commons-lang3** [3.9] : Utilidades para clases
* **commons-collections4** [4.4] : Utildiades para Collection

* **lombok** [1.18.12] : Herramienta de Java para la generación automática de getters, setters, equals, hashCode , toString, more

* **jackson-databind** [2.10.0] : Utilidades para trabajar con Jackson
* **jackson-datatype-jsr310** [2.10.0] : Librería de soporte JSR-310 (Java 8 Date & Time API
* **jackson-dataformat-yaml** [2.10.0] : Framework para trabajar con formatoo YAML

* **junit-jupiter-engine** [5.6.0] : Framework de test unitario v5 (Aañde JUnit Platform + API) usado para escribir test (incluye : annotations, etc.) (Version 5)





## Prerrequisitos

Define que elementos son necesarios para instalar el software

* Java 8 instalado (1.5+ version requerida)
* Maven instalado (3+)




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

Librería custom





## Uso

Librería custom





## Versionado

**Nota :** [SemVer](http://semver.org/) es utilizado por el versionado

Para ver las versiones disponibles ver los tags del repositorio





## Autores

* **Víctor Madrid**

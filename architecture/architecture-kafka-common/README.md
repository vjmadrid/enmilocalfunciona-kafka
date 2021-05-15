# architecture-kafka-common

This project represents a **architecture library (dependency)** related with **elements common to any project** that use **Apache Kafka** to develop the different parts in a homogeneous way

This library stands out for:

* Provides  **global contant classes**
* Provides **utility classes** to facilitaty testing with certain elements : converters, callback, transformers, etc.
* Provides a **generic exception** "AcmeKafkaException" (Kafka Main Business Exception) to have a differentiating type of exception in the project (the rest of exceptions should inherit from it)
* Provides  an **serialize/deserialize components**
* Define **kafka utiltity common frameworks** and their versioning (Help to define an architecture)






## Technological Stack

* Java 8
* [Maven 3](https://maven.apache.org/) - Dependency Management
* [Apache Kafka ](https://kafka.apache.org/)

Dependencies with architecture projects

* **architecture-testing** [0.0.1-SNAPSHOT] : Architecture library for testing used in the test environment
* **architecture-common** [0.0.1-SNAPSHOT] : Architecture library to provide global elements to projects

Dependencies with architecture projects

* **kafka-clients** [2.4.1] : Client for use Apache Kafka





## Prerequisites

Define what elements are needed to install the software

* Java 8 installed (1.5+ version required)
* Maven installed  (3+)
* Kafka infraestructure





## Installation

Steps to follow

* Start a terminal
* To be located in the PATH of installation (the place where the project is located)
* Verify that the file "pom.xml" is available

Execute the following command

```bash
mvn clean install
```

The result will be the generation of an artifact in your Maven repository (local)





## Testing

This project has tests : Unit + Integration

Execute with IDE or Maven





## Deploy

Custom Library





## Use

Custom Library





## Versioning

**Note :** [SemVer](http://semver.org/) is used for the versioning. 
To see the available versions access the repository tags





## Authors

* **Víctor Madrid**

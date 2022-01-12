# Spring Cloud Microservices sample
To learn Spring Cloud Microservices and other spring modules, I took code from few samples available in net and clubbed together and pushed it into my git repo to avoid data loss. 

## Environment Details
	Maven, Java8, Spring, Spring boot

## Architecture
Our sample microservice application having following modules:
### Config Server
- **config-service** - A Spring Boot application which uses Spring Cloud Config Server, environment specific configuration files for other services(discovery-service,student-service, etc) are placed on the classpath(*config-service/src/main/resources/config*). *@EnableConfigServer* makes the application as Cloud Config Server. This server should be started first to make other services configuration files ready.
<img src="https://github.com/prasath116/university-micro-service/blob/master/readme-images/ConfigServer.png" title="Config server setup"><br/>
### Netflix Eureka server
- **discovery-service** - A Spring Cloud Netflix Eureka embedded discovery server. *@EnableEurekaServer* makes the application as Eureka embedded discovery server and in config file need to ensure *eureka.client.registerWithEureka = false,    eureka.client.fetchRegistry = false*
<img src="https://github.com/prasath116/university-micro-service/blob/master/readme-images/DiscoveryServerConf.png" title="Discovery server setup"><br/>
### DiscoveryClients or Netflix Eureka clients
*@EnableDiscoveryClient* makes the application as Discovery Client where this will be registered with our Netflix Eureka server by adding this config eureka.client.serviceUrl.defaultZone=http://localhost:8061/eureka/, **http://localhost:8061** url is our Eureka server url. Following services are Eureka clients which are registered with our *discovery-service*.
<img src="https://github.com/prasath116/university-micro-service/blob/master/readme-images/DiscoveryClientConf.png" title="Discovery client setup"><br/>
- **gateway-service** - A Spring Boot application that acts as a gateway in our architecture, which recives requests from clients and routes to appropriate services in our micro service.
- **employee-service** -  A Spring Boot application that allows to perform CRUD operation on h2 db using spring data repository of employees.
- **student-service** - A Spring Boot application that allows to perform CRUD operation on h2 db using spring data repositoryof students.
- **department-service** -  A Spring Boot application that allows to perform CRUD operation on in-memory repository of departments. It communicates with employee-service. 
- **college-service** -  A Spring Boot application that allows to perform CRUD operation on in-memory repository of organizations. It communicates with both employee-service and department-service.
Here is the Discovery server dashboard. Instances currently registered with our Eureka Discovery server and their statuses
<img src="https://github.com/prasath116/university-micro-service/blob/master/readme-images/DiscoveryServer.png" title="Server status"><br/>

## Technologies used
	- Java 8
	- Lombok pluging 
	- Maven
	- Spring
### In Spring following modules we used to explore those 
- **AOP** : To log the request/ response from client and request/response which we sent to other external server.
- **Actuator** : 
- **Cloud** : For over all microservice architecture.
- **Data JPA** : Spring data jpa to connect with DB. Since all are small service with single table we didn't explore one-one, one-many, many-one, many-many mappings.
- **Exception Handler** : Yet to do.
- **H2 DB** : Instead of keeping in cache used h2 DB and stored in local file.
- **Logs** : Logback used to log.
- **Profile** : Spring profile used for different environment configuration. Also to run our application we need to add env valiable as shown.
				- *spring.profiles.active* dev or prod. Based on profile we have done logics in department-service
				- *username* db username.
				- *password* db password. Db credentials should be from environment variables for security purpose.
			<img src="https://github.com/prasath116/university-micro-service/blob/master/readme-images/EnvVariables.PNG" title="Environment Variables"><br/>
- **Swagger** : Swagger2 used.
- **Web, WebFlux** : To achieve non blocking I/O we are using spring 5 webflux & projectreactor. Flux and Mono.
- **FeignClient/WebClient** : Spring cloud FeignClient used for normal rest call to connect to external server. Spring 5 reactive WebClient used for non blocking client call to connect to external server.

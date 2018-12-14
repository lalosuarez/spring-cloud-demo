# spring-cloud-demo
Microservices for spring centralized configuration, proxy and api gateway

# microservices urls
config-service http://localhost:8888/reservation-service/default

# Download Java Cryptography Extension (JCE)
Extract the jar files from the zip and save them in ${java.home}/jre/lib/security/
http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

# Generate encryption key
keytool -genkeypair -alias mytestkey -keyalg RSA -dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=State,C=US" -keypass 0123456789abcdef0123456789abcdef -keystore server.jks -storepass 0123456789abcdef0123456789abcdef

Reference
https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html#_security

# Docker config-server
docker build --build-arg profiles=docker -f src/main/docker/Dockerfile -t lalossuarez/config-server .

docker run --rm -p 8888:8888 --name config-server lalossuarez/config-server

# Docker eureka-server
docker build --build-arg profiles=docker -f src/main/docker/Dockerfile -t lalossuarez/eureka-server .

docker run --rm -p 8761:8081 --name eureka-server lalossuarez/eureka-server

# Docker reservation-service
docker build --build-arg profiles=docker -f src/main/docker/Dockerfile -t lalossuarez/reservation-service .

docker run -ti --rm --link config-server:config-server -p 8000:8081 --name reservation-service lalossuarez/reservation-service

# Docker commands
docker build --build-arg profiles=docker -f src/main/docker/Dockerfile -t lalossuarez/config-server .

docker images

docker run -p 8888:8888 --name config-server lalossuarez/config-server

docker run -p 8761:8081 (THE FIRST VALUE IS THE PORT THAT YOU WANT TO SEE AND DE SECOND IS THE PORT WHERE THE SERVICE HAS BEEN DEPLOYED)

docker-compose -f docker/docker-compose.yml up -d

First run:
docker-compose -f docker/docker-compose.config-server.yml up

And then
docker-compose -f docker/docker-compose.yml up -d

To clear containers:

docker rm -f $(docker ps -a -q)
To clear images:

docker rmi -f $(docker images -a -q)
To clear volumes:

docker volume rm $(docker volume ls -q)
To clear networks:

docker network rm $(docker network ls | tail -n+2 | awk '{if($2 !~ /bridge|none|host/){ print $1 }}')
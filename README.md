# spring-cloud-demo
Microservices for spring centralized configuration, proxy and api gateway

# Download Java Cryptography Extension (JCE)
Extract the jar files from the zip and save them in ${java.home}/jre/lib/security/
http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

# Generate encryption key
keytool -genkeypair -alias mytestkey -keyalg RSA -dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=State,C=US" -keypass 0123456789abcdef0123456789abcdef -keystore server.jks -storepass 0123456789abcdef0123456789abcdef

Reference
https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html#_security

# Docker
docker build -f src/main/docker/Dockerfile -t docker/config-server .

docker images

docker run -p 8888:8888 docker/config-server

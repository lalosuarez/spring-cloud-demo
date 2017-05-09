# spring-cloud-demo
Microservices for spring centralized configuration, proxy and api gateway


docker build -f src/main/docker/Dockerfile -t docker/config-server .

docker images

 docker run -p 8888:8888 docker/config-server
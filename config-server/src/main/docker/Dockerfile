FROM java:8
VOLUME /tmp
ADD /target/config-server-0.0.1.jar app.jar
RUN sh -c 'touch /app.jar'

# Allows docker to read de keystore generated for encrypt
ADD /server.jks server.jks

RUN sh -c 'touch /server.jks'
EXPOSE 8888

# Sets de profile passed in docker build --build-arg profiles=default
# to run de app with the desired profile
ARG profiles="default"
ENV PROFILES=$profiles
RUN echo "PROFILES=$PROFILES"

ENV JAVA_OPTS="-Dspring.profiles.active=$PROFILES"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
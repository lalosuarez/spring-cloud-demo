FROM java:8
VOLUME /tmp
ADD /target/auth-service-0.0.1.jar app.jar
RUN sh -c 'touch /app.jar'

# Sets de profile passed in docker build --build-arg profiles=default
# to run de app with the desired profile
ARG profiles="default"
ENV PROFILES=$profiles
RUN echo "PROFILES=$PROFILES"

ENV JAVA_OPTS="-Dspring.profiles.active=$PROFILES"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
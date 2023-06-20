FROM openjdk:11
VOLUME /tmp
ADD ./target/servicio-users-0.0.1-SNAPSHOT.jar servicio-users.jar
ENTRYPOINT ["java","-jar","/servicio-users.jar"]
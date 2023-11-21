FROM openjdk:17
MAINTAINER Mainuddin Talukdar

WORKDIR /app

#RUN ./gradle build

COPY build/libs/alumni-authentication-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080

CMD ["java", "-jar", "alumni-authentication-0.0.1-SNAPSHOT.jar"]
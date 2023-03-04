## Définition de l'image de base
#FROM openjdk:8-jdk-alpine
#
## Définition des variables d'environnement
#ENV APP_HOME /app
#ENV APP_VERSION 1.0.0-SNAPSHOT
#ENV APP_NAME j2ee_application_cv
#
## Création du répertoire de l'application
#RUN mkdir -p ${APP_HOME}/logs ${APP_HOME}/config
#
## Copie du jar de l'application dans l'image Docker
#COPY target/${APP_NAME}-${APP_VERSION}.jar ${APP_HOME}/${APP_NAME}.jar
#
## Définition du point d'entrée de l'application
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/j2ee_application_cv.jar"]


FROM maven:3.6.3-jdk-11 as builder
WORKDIR /build
COPY . .
RUN mvn package

FROM tomcat:9-jdk11
COPY --from=builder /build/target/*.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
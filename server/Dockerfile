FROM gradle:8.9.0-jdk22-alpine AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:22
EXPOSE 8080
RUN mkdir /app 
COPY --from=build /home/gradle/src/build/libs/*.jar /app/springlar-server.jar
ENTRYPOINT ["java", "-jar","/app/springlar-server.jar"]
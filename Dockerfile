FROM openjdk:17

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

<<<<<<< feat/#8/bus-api-integration
ENTRYPOINT ["java", "-jar", "/app.jar"]
=======
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILE}", "/app.jar"]
>>>>>>> develop

FROM openjdk:8
EXPOSE 8085
ADD target/skier.jar skier.jar
ENTRYPOINT ["java", "-jar", "skier.jar"]

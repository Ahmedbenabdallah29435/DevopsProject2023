FROM adoptopenjdk/openjdk11:alpine-jre
# Add the necessary database driver (e.g., MySQL)




ADD target/SkiStationProject-0.0.1.jar SkiStationProject-0.0.1.jar
ENTRYPOINT ["java", "-jar","SkiStationProject-0.0.1.jar"]
EXPOSE 8090
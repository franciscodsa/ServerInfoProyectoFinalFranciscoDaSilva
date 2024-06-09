FROM maven:3.9-sapmachine-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:21-jdk-oracle

COPY --from=build /app/target/contaeasy_info_api.jar /contaeasy_info_api.jar
ENTRYPOINT ["java","-jar","/contaeasy_info_api.jar"]
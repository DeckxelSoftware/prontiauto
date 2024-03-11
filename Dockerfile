FROM maven:3.8.6-jdk-8-slim

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY . .

EXPOSE 8082

RUN mvn clean

RUN mvn install -DskipTests

CMD ["java", "-jar", "./target/prontiauto8082.jar"]

FROM openjdk:latest

ENV KAFKA_HOST="192.168.1.244:9092"

WORKDIR /app

COPY ./device-simulator/target/scala-2.12/device-sim-0.1.0.jar /app

CMD cd /app && java -jar device-sim-0.1.0.jar

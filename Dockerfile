# Použij oficiální image Javy
FROM openjdk:17-jdk-slim

# Nastav pracovní adresář v kontejneru
WORKDIR /app

# Zkopíruj JAR soubor do kontejneru
COPY target/RodeFlowServer-0.0.1-SNAPSHOT.jar /app/app.jar

# Spusť aplikaci
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
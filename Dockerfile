FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp
COPY --from=builder /app/target/interview-challenge-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]
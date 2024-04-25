FROM eclipse-temurin:17 as builder
WORKDIR /workspace
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} catalog-service.jar
RUN java -Djarmode=layertools -jar catalog-service.jar extract
RUN ls /workspace/spring-boot-loader/

FROM eclipse-temurin:17
WORKDIR /workspace
RUN useradd spring
USER spring
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./
# ENTRYPOINT [ "ls", "-1a", "./org/springframework/boot/loader/launch" ]
ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]

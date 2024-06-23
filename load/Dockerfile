FROM public.ecr.aws/amazoncorretto/amazoncorretto:17.0.8-al2-native-headless AS builder
RUN yum install -y tar gzip
WORKDIR /app
COPY src ./src
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw clean package

FROM public.ecr.aws/amazoncorretto/amazoncorretto:17.0.8-al2-native-headless
ENV JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto
WORKDIR /app
COPY --from=builder /app/target/load-*.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "./app.jar"]

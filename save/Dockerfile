FROM public.ecr.aws/amazoncorretto/amazoncorretto:17.0.8-al2-native-headless as builder
WORKDIR /app
COPY src ./src
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM public.ecr.aws/amazoncorretto/amazoncorretto:17.0.8-al2-native-headless
ENV JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto
WORKDIR /app
COPY --from=build /app/target/save-*.jar ./app.jar
CMD ["java", "-jar", "./app.jar"]

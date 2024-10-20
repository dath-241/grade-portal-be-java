# Stage 1: Build application using Maven
FROM maven:3.8.4-openjdk-17-slim AS build

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file pom.xml và cài đặt dependencies trước
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Sao chép toàn bộ source code vào container
COPY src ./src

# Build dự án, bỏ qua các test để tiết kiệm thời gian build
RUN mvn clean package -DskipTests

# Stage 2: Run application
FROM openjdk:17-jdk-alpine

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file JAR từ bước build trước vào container này
COPY --from=build /app/target/gradeportal-0.0.1-SNAPSHOT.jar /app/gradeportal.jar

# Sao chép file .env vào container
COPY .env /app/.env  
# Sao chép file .env vào thư mục /app

# Mở cổng 8080 để ứng dụng có thể chạy
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/gradeportal.jar"]

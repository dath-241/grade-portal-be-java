# Sử dụng image JDK chính thức
FROM openjdk:17-jdk-alpine

# Đặt thư mục làm việc
WORKDIR /app

# Copy file JAR từ quá trình build bên ngoài
COPY target/gradeportal-0.0.1-SNAPSHOT.jar /app/gradeportal.jar

# Mở cổng 8080 để ứng dụng có thể chạy
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/gradeportal.jar"]

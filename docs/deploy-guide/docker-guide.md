<a id="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="">
    <img src="../../hcmut.png" alt="HCMUT Logo" width="160" height="160">
    <img src="https://spring.io/img/spring-2.svg" alt="Spring Boot Logo" width="160" height="160">
  </a>

  <h3 align="center">HCMUT Grade Portal Service Server</h3>

  <p align="center">
    Dự án hướng đến mục tiêu xây dựng một trang web cung cấp dịch vụ quản lý điểm số tiện lợi nhanh chóng dành cho sinh viên và giảng viên trong nhà trường.
    <br />
    <a href="../../README.md"><strong>Tổng quan dự án</strong></a>
    •
    <a href="../../reports/report.md"><strong>Báo cáo tiến độ</strong></a>
    •
    <a href="../user-guide.md"><strong>Hướng dẫn sử dụng</strong></a>
    •
    <a href="../document.md"><strong> Tài liệu dự án</strong></a>
    <br />
    <br />
  </p>
</div>

---

# Sử dụng Docker

## 1. Sử dụng Dockerfile và Docker Compose

### 1.1 Dockerfile

Dockerfile được thiết kế để đóng gói ứng dụng Spring Boot thành một image độc lập, đảm bảo tính nhất quán trong các môi trường triển khai.

#### Nội dung Dockerfile

```dockerfile
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
```

### 1.2 Docker Compose

Docker Compose được sử dụng để quản lý và khởi chạy nhiều container, bao gồm container cho ứng dụng Spring Boot và container cho cơ sở dữ liệu PostgreSQL.

#### Nội dung docker-compose.yml

```yaml
version: "3.8"

services:
  app:
    image: trandaiviet/gradeportal-app:latest
    container_name: gradeportal_app
    ports:
      - "24580:8080" # Map cổng 8080 trong container với cổng 24580 trên host
    env_file:
      - .env
    depends_on:
      - db # Đảm bảo database container khởi động trước

  db:
    image: postgres:17
    container_name: gradeportal_db
    env_file:
      - .env
    environment:
      POSTGRES_DB: HCMUT_Grade_Portal_DB
      POSTGRES_USER: ${DB_USERNAME} # Đọc từ file .env
      POSTGRES_PASSWORD: ${DB_PASSWORD} # Đọc từ file .env
    ports:
      - "24532:5432" # Map cổng 5432 trong container với cổng 24532 trên host
    volumes:
      - pgdata:/var/lib/postgresql/data # Lưu trữ dữ liệu PostgreSQL

volumes:
  pgdata:
```

## 2. Quy trình triển khai

### Bước 1: Build Docker Image

Trước tiên, cần build image cho ứng dụng Spring Boot:

```bash
docker build -t trandaiviet/gradeportal-app:latest .
```

### Bước 2: Khởi chạy Docker Compose

Khởi động hệ thống với Docker Compose:

```bash
docker-compose up -d
```

Lệnh trên sẽ:

- Tạo và khởi động container `gradeportal_app` cho ứng dụng.
- Tạo và khởi động container `gradeportal_db` cho cơ sở dữ liệu PostgreSQL.

### Bước 3: Kiểm tra trạng thái container

Đảm bảo các container đang hoạt động:

```bash
docker ps
```

### Bước 4: Truy cập hệ thống

- Ứng dụng Spring Boot có thể truy cập qua địa chỉ:  
  `http://localhost:24580`
- PostgreSQL có thể truy cập qua cổng `24532`.

---

## 4. Lợi ích khi sử dụng Docker và Docker Compose

1. **Tính nhất quán**: Đảm bảo môi trường phát triển, kiểm thử và sản xuất giống nhau.
2. **Dễ dàng triển khai**: Khởi chạy hệ thống chỉ với một lệnh `docker-compose up`.
3. **Quản lý tài nguyên hiệu quả**: Chỉ sử dụng tài nguyên khi cần thiết, và các container có thể được dừng/xóa dễ dàng.
4. **Khả năng mở rộng**: Hỗ trợ dễ dàng mở rộng khi hệ thống yêu cầu thêm tài nguyên hoặc tính năng mới.
5. **Bảo mật**: Cách ly từng container giúp giảm rủi ro về bảo mật.

---

## 5. Kết luận

Việc sử dụng Dockerfile và Docker Compose đã giúp nhóm đơn giản hóa quy trình triển khai và quản lý hệ thống Grade Portal. Với cấu trúc hiện tại, hệ thống có thể dễ dàng mở rộng, bảo trì, và chuyển đổi sang môi trường sản xuất một cách nhanh chóng và hiệu quả.

```

```

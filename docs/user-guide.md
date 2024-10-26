<a id="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="">
    <img src="../hcmut.png" alt="HCMUT Logo" width="160" height="160">
    <img src="https://spring.io/img/spring-2.svg" alt="Spring Boot Logo" width="160" height="160">
  </a>

  <h3 align="center">HCMUT Grade Portal Service Server</h3>

  <p align="center">
    Dự án hướng đến mục tiêu xây dựng nên một trang web cung cấp dịch vụ quản lý điểm số tiện lợi nhanh chóng dành cho sinh viên, giảng viên  trong nhà trường.
    <br />
    <a href="../README.md"><strong>Tổng quan dự án </strong></a>
    •
    <a href="../reports/report.md"><strong>Báo cáo tiến độ </strong></a>
    •
    <a href="#hướng-dẫn-sử-dụng"><strong>Hướng dẫn sử dụng</strong></a>
    •
    <a href="./document.md"><strong>Tài liệu dự án</strong></a>
    <br />
    <br />
  </p>
</div>

<!-- ABOUT MEMBER TEAM-->

## Team Grade Portal BE Java

- Đề tài: Grade Portal
- Nhiệm vụ: Backend Java
- Tên nhóm: `Nhóm thầy Thuận`

- Danh sách thành viên:

| STT | Tên thành viên     | Vai trò       | Mã số sinh viên | GitHub                                   |
| --- | ------------------ | ------------- | --------------- | ---------------------------------------- |
| 1   | Trần Đại Việt      | Product Owner | 2213951         | [Github](https://github.com/VietTranDai) |
| 2   | Phạm Văn Quốc Việt | Developer     | 2213950         | [Github](https://github.com/phaiHP)      |
| 3   | Nguyễn Nhật Khoa   | Developer     | 2211629         | [Github](https://github.com/Sherllgen)   |
| 4   | Phạm Việt Anh      | Developer     | 2210128         | [Github](https://github.com/vietank62)   |
| 5   | Nguyễn Gia Nguyên  | Developer     | 2212303         | [Github](https://github.com/NguyenBk22)  |
| 6   | Lê Đăng Khoa       | Developer     | 2211599         | [Github](https://github.com/thisIsKhoa)  |

# Hướng dẫn sử dụng

## Mục lục

1. [Giới thiệu](#giới-thiệu)
2. [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
3. [Cài đặt và chạy dự án](#cài-đặt-và-chạy-dự-án)
   - [Clone dự án](#clone-dự-án)
   - [Thiết lập biến môi trường](#thiết-lập-biến-môi-trường)
   - [Cài đặt và chạy dự án không dùng Docker](#cài-đặt-và-chạy-dự-án-không-dùng-docker)
   - [Cài đặt và chạy dự án với Docker](#cài-đặt-và-chạy-dự-án-với-docker)
4. [Kiểm tra ứng dụng](#kiểm-tra-ứng-dụng)
5. [Tài liệu tham khảo](#tài-liệu-tham-khảo)

## Giới thiệu

**Grade Portal** là một hệ thống quản lý điểm số trực tuyến dành cho sinh viên và giảng viên tại Đại học Bách Khoa - Đại học Quốc gia TP.HCM. Dự án này sử dụng nhiều công nghệ hiện đại như **Java Spring Boot**, **PostgreSQL**, **Docker**, **SOPS**, và nhiều công nghệ khác để cung cấp một giải pháp quản lý điểm số hiệu quả, tiện lợi và bảo mật.

## Yêu cầu hệ thống

Trước khi bắt đầu, đảm bảo rằng hệ thống của bạn đáp ứng các yêu cầu sau:

- **Java 17**: Phiên bản Java Development Kit (JDK) 17 trở lên.
- **Maven**: Công cụ quản lý dự án Maven.
- **Docker**: Đã cài đặt Docker và Docker Compose (nếu sử dụng Docker).
- **PostgreSQL**: Nếu không sử dụng Docker, bạn cần cài đặt PostgreSQL thủ công.
- **Git**: Đã cài đặt Git để clone repository.
- **SOPS**: Đã cài đặt SOPS để hỗ trợ mã hóa file dev.enc hay prod.enc.
- **Age**: Cung cấp mã mã hóa cần thiết.

## Cài đặt và chạy dự án

### Clone dự án

Sử dụng lệnh sau để clone repository dự án từ GitHub:

```bash
git clone https://github.com/your-repository/gradeportal.git
cd gradeportal
```

### Thiết lập biến môi trường

#### Nếu bạn được cung cấp file key.txt

Nếu bạn được cung cấp khóa **Age** trong file `key.txt`, cấu trúc của file này sẽ như sau:

```plaintext
# created: 2024-10-19T18:05:45+07:00
# public key: age1l4zc9ppdyvz6hvwj67uca0pfgyydm9efs7kgmmks3h9pz7xa9v3scrl3sn
AGE-SECRET-KEY-1T4Y4TH7S8GMAUW9J7SFMP6YXRG2FF0UFX08MSPWS540CA20NL40QVJ60VS
```

Đặt file `key.txt` được cung cấp vào folder gradeportal. Bạn có thể tham chiếu file này khi sử dụng SOPS để giải mã các file chứa biến môi trường `dev.enc` hay `prod.enc` để có được file `.env` như sau:

```bash
sops --age key.txt --decrypt dev.enc > .env
```

Tuy nhiên một số máy sẽ gặp lỗi khi sử dụng câu lệnh trên nên để giải mã được file mỗi trường bị mã hóa ta làm các bước sau:

1. Trước hết thiết lập biến môi trường Age Key trực tiếp với câu lệnh:

   ```bash
   set SOPS_AGE_KEY=AGE-SECRET-KEY-1T4Y4TH7S8GMAUW9J7SFMP6YXRG2FF0UFX08MSPWS540CA20NL40QVJ60VS
   ```

2. Thực hiện giải mã một trong hai file dev.enc hay prod.enc:

   ```bash
   sops --decrypt --input-type dotenv --output-type dotenv dev.enc > .env
   ```

#### Nếu bạn không được cung cấp file key.txt

Nếu bạn không được cung cấp khóa file `key.txt` bạn có thể tự tạo file .env trong folder và điền các đường dẫn tài nguyên cần sử dụng theo file [`example.env`](../example.env) được cung cấp sẵn.

### Cài đặt và chạy dự án không dùng Docker

Nếu bạn không sử dụng Docker, bạn cần cài đặt PostgreSQL và các công cụ hỗ trợ thủ công.

#### 1. **Cài đặt PostgreSQL**

Trước tiên, bạn cần cài đặt PostgreSQL. Bạn có thể tải PostgreSQL từ trang chủ tại đây:

- [PostgreSQL Download](https://www.postgresql.org/download/)

Sau khi cài đặt PostgreSQL, tạo một cơ sở dữ liệu với tên `HCMUT_Grade_Portal_DB` và đảm bảo thông tin đăng nhập giống với cấu hình sau:

```plaintext
POSTGRES_USER=postgres
POSTGRES_PASSWORD=19072004
POSTGRES_DB=HCMUT_Grade_Portal_DB
```

Lưu ý: Bạn có thể điều chỉnh thông tin này trong file `.env` cho phù hợp với cấu hình của hệ thống.

#### 2. **Cấu hình môi trường**

Tạo một file `.env` trong thư mục gốc của dự án với nội dung tương tự như sau (giải mã từ `dev.enc` nếu cần):

```plaintext
SPRING_APPLICATION_NAME=Grade Portal Service
SERVER_PORT=8080
DB_URL=jdbc:postgresql://localhost:5432/HCMUT_Grade_Portal_DB
DB_USERNAME=postgres
DB_PASSWORD=19072004
JACKSON_TIME_ZONE=Asia/Ho_Chi_Minh
```

#### 3. **Chạy ứng dụng Spring Boot**

Sử dụng Maven để build và chạy ứng dụng:

```bash
mvn clean install
mvn spring-boot:run
```

Ngoài ra nếu bạn muốn init sẵn những dữ liệu cơ bản trong database có thể sử dụng các câu lệnh sau

```bash
mvn clean install
mvn spring-boot:run "-Dspring-boot.run.arguments=--init-data=true"
```

Lệnh này sẽ build ứng dụng và khởi động Spring Boot trên cổng `8080`. Đảm bảo rằng PostgreSQL đã được khởi động và kết nối đúng với ứng dụng.

### Cài đặt và chạy dự án với Docker

#### Chưa hoàn thiện

Nếu bạn đã cài đặt Docker, có thể dễ dàng khởi chạy dự án bằng Docker Compose như sau:

#### Cài đặt Docker

Đảm bảo Docker và Docker Compose đã được cài đặt. Nếu chưa cài đặt, bạn có thể tải và cài đặt Docker theo hướng dẫn sau:

- [Cài đặt Docker](https://docs.docker.com/get-docker/)
- [Cài đặt Docker Compose](https://docs.docker.com/compose/install/)

Nếu chưa cài đặt SOPS, bạn có thể cài đặt từ [đây](https://github.com/mozilla/sops).

#### Chạy dự án bằng Docker Compose

Sau khi cấu hình môi trường, bạn có thể sử dụng lệnh sau để build và chạy dự án:

```bash
docker compose up --build -d
```

Lệnh này sẽ tạo và chạy container cho ứng dụng **Spring Boot** và **PostgreSQL**.

## Kiểm tra ứng dụng

Sau khi khởi động thành công, bạn có thể truy cập vào ứng dụng tại địa chỉ sau:

```
http://localhost:8080
```

Nếu không có lỗi, bạn sẽ thấy giao diện chính của hệ thống **Grade Portal** hoặc API đầu ra của Spring Boot.

Đối với Docker, bạn có thể kiểm tra trạng thái của các container đang chạy bằng lệnh:

```bash
docker ps
```

Để xem log của ứng dụng:

```bash
docker logs gradeportal_app
```

## Tài liệu tham khảo

1. [Java 17 Documentation](https://docs.oracle.com/en/java/javase/17/)
2. [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
3. [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
4. [PostgreSQL Documentation](https://www.postgresql.org/docs/17/)
5. [Maven Documentation](https://maven.apache.org/guides/index.html)
6. [Docker Documentation](https://docs.docker.com/)
7. [Docker Compose Documentation](https://docs.docker.com/compose/)
8. [SOPS Documentation](https://github.com/mozilla/sops)
9. [Age Documentation](https://github.com/FiloSottile/age)
10. [JWT Introduction](https://jwt.io/introduction)
11. [HikariCP Documentation](https://github.com/brettwooldridge/HikariCP)
12. [JavaDotEnv Documentation](https://github.com/cdimascio/java-dotenv)
13. [VS Code Documentation](https://code.visualstudio.com/docs)
14. [Lombok Documentation](https://projectlombok.org/features/all)
15. [GIT Documentation](https://git-scm.com/doc)

<p align="right">(<a href="#top">Về đầu trang</a>)</p>

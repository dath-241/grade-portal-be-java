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
    Dự án hướng đến mục tiêu xây dựng một trang web cung cấp dịch vụ quản lý điểm số tiện lợi nhanh chóng dành cho sinh viên và giảng viên trong nhà trường.
    <br />
    <a href="../README.md"><strong>Tổng quan dự án</strong></a>
    •
    <a href="../reports/report.md"><strong>Báo cáo tiến độ</strong></a>
    •
    <a href="./user-guide.md"><strong>Hướng dẫn sử dụng</strong></a>
    •
    <a href="#tài-liệu-dự-án"><strong> Tài liệu dự án</strong></a>
    <br />
    <br />
  </p>
</div>

---

# Tài liệu dự án

Đây là nơi nhóm sẽ hệ thống lại những tài liệu quan trọng trong quá trình hiện thực dự án, cũng như hướng dẫn sử dụng dự án hay các quy trình làm việc sẽ được hệ thống lại.

## Mục lục

1. [Giới thiệu dự án](#giới-thiệu)
2. [Thành viên nhóm](#thành-viên)
3. [Mô hình hệ thống](#mô-hình-hệ-thống)
4. [Thiết kế CSDL](#thiết-kế-csdl)
5. [Tài liệu API](#tài-liệu-api)
6. [Deploy Guide](#hướng-dẫn-triển-khai)
7. [Hướng dẫn sử dụng](#hướng-dẫn-sử-dụng)
8. [Quy trình Git Flow](#quy-trình-git-flow)
9. [Tài liệu tham khảo](#tài-liệu-tham-khảo)

---

## 1. Giới thiệu dự án <a id="giới-thiệu"></a>

Dự án Grade Portal nhằm mục đích cung cấp một hệ thống quản lý điểm số cho sinh viên và giảng viên. Hệ thống giúp quản lý, tra cứu, và cập nhật thông tin điểm số một cách tiện lợi và hiệu quả.

---

## 2. Thành viên nhóm <a id="thành-viên"></a>

| STT | Tên thành viên     | Vai trò       | Mã số sinh viên | GitHub                                   |
| --- | ------------------ | ------------- | --------------- | ---------------------------------------- |
| 1   | Trần Đại Việt      | Product Owner | 2213951         | [Github](https://github.com/VietTranDai) |
| 2   | Phạm Văn Quốc Việt | Developer     | 2213950         | [Github](https://github.com/phaiHP)      |
| 3   | Nguyễn Nhật Khoa   | Developer     | 2211629         | [Github](https://github.com/Sherllgen)   |
| 4   | Phạm Việt Anh      | Developer     | 2210128         | [Github](https://github.com/vietank62)   |
| 5   | Nguyễn Gia Nguyên  | Developer     | 2212303         | [Github](https://github.com/NguyenBk22)  |
| 6   | Lê Đăng Khoa       | Developer     | 2211599         | [Github](https://github.com/thisIsKhoa)  |

---

## 3. Mô hình hệ thống <a id="mô-hình-hệ-thống"></a>

![Layered Architecture](./system-architecture/LayeredArchitecture.png)

- Phần này sẽ mô tả kiến trúc hệ thống của dự án, bao gồm các thành phần chính như:
  - Mô hình lớp ứng dụng
  - Sơ đồ kiến trúc được hiện thực chi tiết và được áp dụng trong hệ thống.
  - Tài liệu chi tiết về `System Architecture` sẽ ở [đây](./system-architecture/system-architecture.md)

---

## 4. Thiết kế CSDL <a id="thiết-kế-csdl"></a>

![Database Design](./database-design/EERD.png)

- Chi tiết thiết kế cơ sở dữ liệu (database) bao gồm:
  - Các bảng, cột, kiểu dữ liệu và quan hệ.
  - Được trình bày trong file `database-design.md`.
  - Tài liệu chi tiết về `Database Design` sẽ ở [đây](./database-design/database-design.md)

---

## 5. Tài liệu API <a id="tài-liệu-api"></a>

- Danh sách các API endpoints của hệ thống, bao gồm:
  - Phương thức HTTP (GET, POST, PUT, DELETE)
  - URL endpoint
  - Tham số đầu vào và đầu ra
  - Ví dụ về request/response
  - File chi tiết có trong `api-document.md`.
  - Api Document sẽ hiện thực tại [đây](./api-document/api-document.md)

---

## 6. Deploy Guide <a id="hướng-dẫn-triển-khai"></a>

![alt text](./deploy-guide/DeploymentDiagram.png)

- Tài liệu chi tiết về cách triển khai hệ thống:
  - Yêu cầu hệ thống (Java, Spring Boot, Docker, etc.)
  - Cách build và chạy ứng dụng.
  - File hướng dẫn cụ thể có trong `deploy-guide.md`.
  - Tài liệu chi tiết về Deploy Guide sẽ nằm ở [đây](./deploy-guide/deploy-guide.md)

---

## 7. Hướng dẫn sử dụng <a id="hướng-dẫn-sử-dụng"></a>

- Hướng dẫn chi tiết cho người dùng cuối (sinh viên, giảng viên, admin) về cách sử dụng các tính năng của hệ thống.
- Được trình bày trong file `user-guide.md`.
- Chưa cập nhập

---

## 8. Quy trình Git Flow <a id="quy-trình-git-flow"></a>

![git-flow](./git-flow/git-flow.png)

- Quy trình Git Flow mà nhóm sử dụng để quản lý source code, bao gồm các nhánh `main`, `feature`, `staging`, `release`, `hotfix`.
- Tài liệu chi tiết về `git workflow` sẽ ở [đây](./git-flow/git-flow.md).

---

## 9. Tài liệu tham khảo <a id="tài-liệu-tham-khảo"></a>

- Các nguồn tài liệu, bài viết, và thông tin tham khảo khác được sử dụng trong quá trình phát triển dự án.
  - [Tài liệu Spring Boot](https://spring.io/projects/spring-boot)
  - [Tài liệu Docker](https://docs.docker.com/get-started/)

---

<p align="right">(<a href="#readme-top">Quay lại đầu trang</a>)</p>

Hy vọng cấu trúc này sẽ giúp tài liệu của nhóm mình dễ đọc và tổ chức tốt hơn! Nếu có thêm câu hỏi, bạn hãy cho mình biết nhé!

> Mọi thắc mắc, báo lỗi, đề xuất tính năng cho ứng dụng xin hay liên hệ qua địa chỉ email: viet.trankhmtbk22@hcmut.edu.vn hoặc liên hệ qua github của từng thành viên.

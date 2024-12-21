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

# Grade Portal API Document

GradePortal API là giao diện lập trình ứng dụng của hệ thống quản lý điểm và lớp học, cho phép các nhà phát triển tích hợp và tương tác với dữ liệu của hệ thống một cách dễ dàng và hiệu quả.

Tài liệu này cung cấp thông tin chi tiết về các **endpoint**, phương thức HTTP, và các tham số cần thiết để thực hiện các thao tác như quản lý khóa học, lớp học, điểm số, giáo viên, và sinh viên.

Các API được xây dựng theo chuẩn **Restful**, giúp đảm bảo khả năng mở rộng và tương thích cao trong các ứng dụng hiện đại. Mỗi endpoint đi kèm với mô tả chi tiết về chức năng, yêu cầu đầu vào, và ví dụ về phản hồi trả về.

---

## Tài liệu API chi tiết

Dưới đây là tài liệu mô tả chi tiết cho mỗi endpoint, bao gồm các phương thức HTTP, yêu cầu đầu vào, và ví dụ về phản hồi trả về.
Document Api nằm ở [đây](https://documenter.getpostman.com/view/36861276/2sAYJ1m3AX).

Hoặc có thể tải về bản json ở [đây](./Grade%20Portal%20Api%20Document.postman_collection.json).

---

## Các Endpoint Chính

### /admin

1. **/auth**:

   - **Mô tả**: Quản lý việc đăng nhập (Log In), đăng xuất (Log Out), và lấy thông tin cá nhân cho người dùng với vai trò **Admin**.
   - **Phương thức**: `POST`, `GET`, `DELETE` (tùy thuộc vào chức năng cụ thể)

2. **/manage-user**:

   - **Mô tả**: Các API cho phép **Admin** quản lý người dùng trong hệ thống.
   - **Phương thức**: `POST`, `GET`, `PUT`, `DELETE`

3. **/manage-course**:

   - **Mô tả**: Các API cho phép **Admin** quản lý khóa học trong hệ thống.
   - **Phương thức**: `POST`, `GET`, `PUT`, `DELETE`

4. **/manage-class**:

   - **Mô tả**: Các API cho phép **Admin** quản lý các lớp học được mở ra trong hệ thống.
   - **Phương thức**: `POST`, `GET`, `PUT`, `DELETE`

5. **/manage-semester**:
   - **Mô tả**: Các API cho phép **Admin** quản lý học kỳ có trong hệ thống.
   - **Phương thức**: `POST`, `GET`, `PUT`, `DELETE`

### /student

1. **/auth**:

   - **Mô tả**: Quản lý việc đăng nhập (Log In), đăng xuất (Log Out), và lấy thông tin cá nhân cho người dùng với vai trò **Student**.
   - **Phương thức**: `POST`, `GET`, `DELETE`

2. **/class-info**:

   - **Mô tả**: Các API cho phép **Student** truy xuất thông tin lớp học có trong hệ thống.
   - **Phương thức**: `GET`

3. **/sheetmark-info**:

   - **Mô tả**: Các API cho phép **Student** truy xuất thông tin về bảng điểm trong hệ thống.
   - **Phương thức**: `GET`

4. **/teacher-info**:
   - **Mô tả**: Các API cho phép **Student** truy xuất thông tin giáo viên dạy học có trong hệ thống.
   - **Phương thức**: `GET`

### /teacher

1. **/auth**:

   - **Mô tả**: Quản lý việc đăng nhập (Log In), đăng xuất (Log Out), và lấy thông tin cá nhân cho người dùng với vai trò **Teacher**.
   - **Phương thức**: `POST`, `GET`, `DELETE`

2. **/manage-sheetmark**:

   - **Mô tả**: Các API cho phép **Teacher** quản lý các bảng điểm được phân quyền trong hệ thống.
   - **Phương thức**: `POST`, `GET`, `PUT`, `DELETE`

3. **/manage-class**:

   - **Mô tả**: Các API cho phép **Teacher** quản lý các lớp học được phân quyền trong hệ thống.
   - **Phương thức**: `POST`, `GET`, `PUT`, `DELETE`

4. **/student-info**:
   - **Mô tả**: Các API cho phép **Teacher** truy xuất thông tin sinh viên cần thiết.
   - **Phương thức**: `GET`

---

**Lưu ý**: Đảm bảo bạn có quyền truy cập và token xác thực thích hợp khi sử dụng các API này, vì một số endpoint yêu cầu quyền Admin, Teacher, hoặc Student tương ứng.

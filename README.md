# School Management API & Chuẩn Đầu Ra 

Dự án Hệ thống Quản lý Trường Học được xây dựng dựa trên kiến trúc 3 lớp (3-Tier Architecture) hiện đại. Hệ thống cung cấp cả giao diện Web (Thymeleaf) theo thiết kế tối giản và các API (RESTful) để phục vụ cho các nền tảng khác.

## 🚀 Công Nghệ Sử Dụng
- **Backend:** Java 21, Spring Boot 3.x, Spring Data JPA, Hibernate Validator.
- **Frontend:** Thymeleaf 3, HTML5, Vanilla CSS (Theme xám `#A2AAB6`, phong cách minimalism), Vanilla JS.
- **Cơ Sở Dữ Liệu:** MySQL (Môi trường Dev/Prod) và H2 Database (Môi trường Test).
- **Công Cụ:** Maven Wrapper, thiết lập biến môi trường qua `.env`.

---

## 📂 Cấu Trúc Thư Mục
Dự án được tổ chức chặt chẽ theo nguyên tắc Separation of Concerns:

```text
src/main/
├── java/com/example/schoolapi/
│   ├── controller/        # View Controllers (Thymeleaf) & API Controllers (JSON)
│   ├── service/           # Logic nghiệp vụ (Interfaces & Impl)
│   ├── dao/               # Data Access Object (Kế thừa JpaRepository)
│   ├── entity/            # Các thực thể CSDL (JPA Entities)
│   ├── dto/               # Data Transfer Objects (Sử dụng Java Records)
│   ├── mapper/            # Chuyển đổi dữ liệu giữa Entity và DTO
│   └── exception/         # Xử lý ngoại lệ tập trung (Global Exception Handling)
└── resources/
    ├── application.properties # Cấu hình dự án
    ├── static/            # Tài nguyên tĩnh (CSS, JS, Images)
    │   ├── css/           # main.css, sidebar.css, components.css...
    │   └── js/            # Các file Javascript thuần
    └── templates/         # Các view Thymeleaf HTML
        ├── fragments/     # Các thành phần dùng chung (layout, sidebar, topbar...)
        └── {module}/      # Các thư mục view theo từng tính năng
```

---

## 🗄️ Cấu Trúc Cơ Sở Dữ Liệu & Tính Năng

Hệ thống bao gồm 15 thực thể cốt lõi, được phân chia theo các module:

| Thực thể (Entity) | Bảng DB | Chức năng chính |
|---|---|---|
| `AcademicYear` | `academic_years` | Quản lý niên khóa (VD: K2026), thời gian bắt đầu/kết thúc |
| `CurriculumFramework` | `curriculum_frameworks` | Quản lý khung chương trình, tổng số tín chỉ |
| `Semester` | `semesters` | Quản lý học kỳ (VD: HK1, HK2) |
| `SubjectGroup` | `subject_groups` | Quản lý nhóm môn học (VD: Cơ sở ngành) |
| `Subject` | `subjects` | Quản lý môn học, số tín chỉ |
| `ClassSection` | `class_sections` | Quản lý lớp của môn học (gắn với học kỳ, giảng viên) |
| `Classroom` | `classrooms` | Quản lý phòng học, sức chứa, tòa nhà |
| `EducationLevel` | `education_levels` | Quản lý cấp bậc đào tạo (Đại học, Cao đẳng) |
| `Faculty` | `faculties` | Quản lý khoa (VD: Khoa CNTT) |
| `Major` | `majors` | Quản lý chuyên ngành (VD: KTPM) thuộc Khoa |
| `IndustryGroup` | `industry_groups` | Quản lý nhóm ngành |
| `Lecturer` | `lecturers` | Quản lý giảng viên, thông tin liên lạc, học vị |
| `Student` | `students` | Quản lý sinh viên |
| `Cdr` | `cdrs` | Quản lý chuẩn đầu ra (CDR) theo cấp bậc |
| `Feedback` | `feedbacks` | Quản lý nhận xét và phản hồi hệ thống |

---

## 🌐 Các Tuyến Đường (Routes) & Giao Diện

Giao diện người dùng sử dụng tông màu chủ đạo là xám (`#A2AAB6`) và nền trắng/sáng (`#F5F7FA`).

| URL | Phương thức | Mô tả View / API |
|-----|--------|------|
| `/` | GET | Landing page (Có selector chuyển đổi ngôn ngữ) |
| `/dashboard` | GET | Bảng điều khiển (Dashboard) |
| `/{tên-module}` | GET | Các trang quản lý danh sách (VD: `/academic-years`, `/students`, `/cdrs`) |
| `/api/{tên-module}` | GET/POST/PUT/DELETE | Cổng giao tiếp REST API (trả về JSON) cho toàn bộ 15 thực thể |

---

## 🛠 Hướng Dẫn Cài Đặt & Chạy Dự Án

### 1. Yêu cầu hệ thống
- Cài đặt **Java JDK 21**.
- Cài đặt **MySQL Server** (chạy ở cổng 3306).

### 2. Thiết lập Cơ sở dữ liệu & Môi trường
1. Tạo một database trong MySQL có tên là `school_db`.
2. Ở thư mục gốc của dự án, copy file `.env.example` thành `.env` và điền cấu hình:
   ```env
   # Mật khẩu thực tế của MySQL
   SCHOOL_DB_PASSWORD=mật_khẩu_mysql_của_bạn
   SERVER_PORT=8080
   ```

### 3. Khởi chạy Ứng dụng
Mở Terminal tại thư mục gốc của dự án, sử dụng Maven Wrapper để chạy:

- **Mac/Linux:** `./mvnw clean package && ./mvnw spring-boot:run`
- **Windows:** `mvnw.cmd clean package && mvnw.cmd spring-boot:run`

Truy cập trang Landing Page tại: `http://localhost:8080/`

### 4. Chạy Kiểm Thử (Unit Tests)
Dự án được cấu hình tự động chuyển sang cơ sở dữ liệu RAM (H2 Database) khi chạy Test.
- Lệnh chạy test: `./mvnw clean test`

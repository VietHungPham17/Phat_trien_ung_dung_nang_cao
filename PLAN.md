# Plan: Triển khai Web UI cho School Management API

## Context

Dự án hiện tại ở `G:\My Drive\bcse\S7\Web\W1\project` là một Spring Boot REST API quản lý **Student** (bảng `students` với id, department, studentname, active). Cần mở rộng thành **hệ thống Quản lý Môn học & Đánh giá Chuẩn đầu ra (CDR)** với:

- **Backend**: mở rộng thêm ~14 entity (Niên khóa, Khung CT, Học kỳ, Nhóm MH, Môn học, Lớp của MH, Phòng học, Cấp bậc ĐT, Khoa, Chuyên ngành, Nhóm ngành, Giảng viên, Sinh viên, CDR, Nhận xét) + REST API cho từng entity
- **Frontend**: Thymeleaf + HTML/CSS/JS, theme xám `#A2AAB6`, logo VNU-Japan, sidebar cố định, layout 2 cột
- **Localization**: Tất cả label tiếng Việt
- **Selector ngôn ngữ**: chỉ xuất hiện ở **Landing page**, không xuất hiện ở các trang chức năng
- **Khung avatar + tên admin**: có khung viền giống như khung ngôn ngữ
- **Triển khai**: localhost với env riêng, mã nguồn lưu tại `G:\My Drive\bcse\S7\Web\W2\project`

## Visual Reference

Logo: `G:\My Drive\bcse\S7\Web\W2\logo.png` (VNU - Vietnam Japan University, vòng tròn đỏ với hoa sen/sakura trắng, dòng chữ "VNU since 1906")
UI mockup: `G:\My Drive\bcse\S7\Web\W2\App.png`

## Tech Stack

- Spring Boot 4.1.1 (giữ nguyên hiện tại)
- Thymeleaf 3 (thêm mới)
- Spring Data JPA + MySQL (giữ nguyên)
- Hibernate Validator (giữ nguyên)
- Vanilla CSS (không dùng Bootstrap để giữ minimalism theo yêu cầu)
- Vanilla JS cho tương tác nhỏ (dropdown, pagination)
- Font: Inter hoặc system-ui

---

## Cấu trúc thư mục

```
G:\My Drive\bcse\S7\Web\W2\project\
├── .env                              # Biến môi trường (DB password, port, ...)
├── .env.example                      # Template
├── README.md                         # Hướng dẫn chạy
├── pom.xml
├── mvnw, mvnw.cmd, .mvn/             # Maven wrapper (copy từ W1)
├── src\
│   ├── main\
│   │   ├── java\com\example\schoolapi\
│   │   │   ├── SchoolApiApplication.java
│   │   │   ├── controller\                    # Controllers Thymeleaf + REST
│   │   │   ├── service\                       # Service interfaces
│   │   │   ├── service\impl\                  # Service implementations
│   │   │   ├── dao\                           # JPA repositories
│   │   │   ├── entity\                        # JPA entities
│   │   │   ├── dto\                           # DTO records
│   │   │   ├── mapper\                        # Entity ↔ DTO mappers
│   │   │   └── exception\                     # Exception handlers
│   │   └── resources\
│   │       ├── application.properties
│   │       ├── static\
│   │       │   ├── css\
│   │       │   │   ├── main.css               # Theme chính
│   │       │   │   ├── sidebar.css
│   │       │   │   ├── landing.css
│   │       │   │   └── components.css
│   │       │   ├── js\
│   │       │   │   ├── layout.js
│   │       │   │   └── table.js
│   │       │   └── images\
│   │       │       └── logo.png               # Copy từ W2
│   │       └── templates\
│   │           ├── fragments\
│   │           │   ├── layout.html            # Layout chung (functional pages)
│   │           │   ├── sidebar.html           # Fragment sidebar
│   │           │   ├── landing-layout.html    # Layout riêng cho landing
│   │           │   └── topbar.html            # Topbar cho functional pages
│   │           ├── landing.html               # Trang chủ - có language selector
│   │           ├── dashboard.html
│   │           ├── academic-year\             # Nhóm theo entity
│   │           │   └── list.html
│   │           ├── curriculum-framework\list.html
│   │           ├── semester\list.html
│   │           ├── subject-group\list.html
│   │           ├── subject\list.html
│   │           ├── class-section\list.html
│   │           ├── classroom\list.html
│   │           ├── education-level\list.html
│   │           ├── faculty\list.html
│   │           ├── major\list.html
│   │           ├── industry-group\list.html
│   │           ├── lecturer\list.html
│   │           ├── student\list.html
│   │           ├── feedback\list.html
│   │           └── cdr\list.html
│   └── test\
└── ...
```

---

## Backend (Mở rộng)

### Entities mới (giữ pattern Student hiện có)

Mỗi entity gồm: `Entity.java`, `*Request.java` (DTO input), `*Response.java` (DTO output), `*Mapper.java`, `*Dao.java` (extends JpaRepository), `*Service.java` interface, `*ServiceImpl.java`, `*Controller.java` (Thymeleaf view + JSON API).

| Entity | Bảng DB | Trường chính |
|---|---|---|
| `AcademicYear` | `academic_years` | id, name (K2026), description, startYear, endYear, active |
| `CurriculumFramework` | `curriculum_frameworks` | id, name, description, totalCredits, active |
| `Semester` | `semesters` | id, name (HK1), academicYearId, startDate, endDate |
| `SubjectGroup` | `subject_groups` | id, name (Cơ sở ngành), code, description |
| `Subject` | `subjects` | id, code, name, credits, subjectGroupId, description |
| `ClassSection` | `class_sections` | id, subjectId, semesterId, lecturerId, classroomId, sectionCode |
| `Classroom` | `classrooms` | id, code, capacity, building, active |
| `EducationLevel` | `education_levels` | id, name (Đại học, Cao đẳng), description |
| `Faculty` | `faculties` | id, name (Khoa CNTT), code, description |
| `Major` | `majors` | id, facultyId, name (KTPM), code |
| `IndustryGroup` | `industry_groups` | id, name (Nhóm ngành CNTT), code |
| `Lecturer` | `lecturers` | id, fullName, email, phone, facultyId, degree |
| `Student` (đã có) | `students` | id, department, studentname, active |
| `Cdr` (chuẩn đầu ra) | `cdrs` | id, code (CDR1), description, majorId, level |
| `Feedback` | `feedbacks` | id, senderName, content, targetType, targetId, createdAt |

### Hai loại controller

1. **View Controllers** (Thymeleaf) — serve HTML
2. **REST Controllers** (JSON) — cho API, giữ pattern hiện có của Student

Tách rõ: `controller/view/*Controller.java` cho Thymeleaf, `controller/api/*Controller.java` cho REST.

---

## Frontend

### Theme CSS (file `main.css`)

```css
:root {
  --color-primary: #A2AAB6;        /* Xám chủ đạo */
  --color-primary-light: #C5CCD6;
  --color-primary-dark: #7A8290;
  --color-bg: #F5F7FA;             /* Nền chính */
  --color-card: #FFFFFF;
  --color-text: #2D3748;
  --color-text-muted: #6B7785;
  --color-border: #E2E8F0;
  --color-table-header: #A2AAB6;   /* Gray header giống mockup */
  --sidebar-width: 248px;
  --topbar-height: 60px;
  --radius: 8px;
}
```

### Components chính

#### 1. Sidebar (fixed left)
- Logo ở top-left
- Tiêu đề "QUẢN LÝ MÔN HỌC" / "VÀ ĐÁNH GIÁ CHUẨN ĐẦU RA"
- Menu items với icon + label, active state highlight bằng `--color-primary-light` background
- Cuộn scroll nếu menu dài

#### 2. Topbar — Functional pages (KHÔNG có language selector)
- Bên phải chỉ có khung avatar + "admin" (admin user, không auth thật)
- Khung viền bo góc, nền trắng với border `--color-border`

#### 3. Topbar — Landing page
- Bên phải có CẢ HAI: khung ngôn ngữ (🇻🇳 VI) và khung avatar admin
- 2 khung nằm cạnh nhau, style giống nhau

#### 4. Khung (card-pill style)

```html
<div class="topbar-pill">
  <img src="/images/flag-vn.svg" /> VI
  <span class="caret"></span>
</div>
<div class="topbar-pill">
  <i class="icon-user"></i> admin
</div>
```

CSS:
```css
.topbar-pill {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: #fff;
  border: 1px solid var(--color-border);
  border-radius: 999px;
  padding: 6px 14px;
  font-size: 13px;
  color: var(--color-text);
  cursor: pointer;
}
```

#### 5. Table component
- Light gray header (`--color-table-header`) với text trắng
- Hover row: `--color-primary-light` background
- Cột "Thao tác": 2 nút "Chỉnh sửa" (icon bút) + nút xóa (icon thùng rác)
- Pagination ở dưới: "X-Y trong số Z" / phân trang / "Y/trang" dropdown

#### 6. Action buttons
- "Thêm mới" — nền đậm (`#2D3748` hoặc `--color-primary-dark`), chữ trắng
- "Xuất file" — viền, nền trắng, icon download
- "Tìm kiếm nâng cao" — viền, nền trắng, dropdown caret

#### 7. Search bar
- Ô search lớn với placeholder "Tìm kiếm theo tên niên khóa, mô tả, năm..."

---

## Routes

| URL | Method | View |
|-----|--------|------|
| `/` | GET | Landing page (with language selector) |
| `/dashboard` | GET | Dashboard |
| `/academic-years` | GET | Quản lý niên khóa |
| `/curriculum-frameworks` | GET | Quản lý khung CT |
| `/semesters` | GET | Quản lý học kỳ |
| `/subject-groups` | GET | Quản lý nhóm MH |
| `/subjects` | GET | Quản lý môn học |
| `/class-sections` | GET | Quản lý lớp của MH |
| `/classrooms` | GET | Quản lý phòng học |
| `/education-levels` | GET | Quản lý cấp bậc ĐT |
| `/faculties` | GET | Quản lý khoa |
| `/majors` | GET | Quản lý chuyên ngành |
| `/industry-groups` | GET | Quản lý nhóm ngành |
| `/lecturers` | GET | Quản lý giảng viên |
| `/students` | GET | Quản lý sinh viên |
| `/feedbacks` | GET | Nhận xét & Phản hồi |
| `/cdrs` | GET | Quản lý CDR |
| `/api/{entity}` | \* | REST API (giữ nguyên Student API + thêm các entity mới) |

---

## File `.env` và env riêng

```
SPRING_DATASOURCE_URL=jdbc:mysql://127.0.0.1:3306/school_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=school_app
SCHOOL_DB_PASSWORD=<password>
SERVER_PORT=8080
```

Spring Boot tự động nhận biến env có prefix `SPRING_` qua property mapping. `SCHOOL_DB_PASSWORD` map trực tiếp vào `spring.datasource.password`.

---

## Verification

Sau khi triển khai:

1. **Build & Run**
   ```bash
   cd "G:/My Drive/bcse/S7/Web/W2/project"
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```
2. **Truy cập** `http://localhost:8080/` → thấy landing với logo, 2 khung pill (ngôn ngữ + admin)
3. **Click "Bắt đầu" / Dashboard** → sidebar hiển thị, topbar chỉ có khung admin (KHÔNG có ngôn ngữ)
4. **Click menu "Quản lý niên khóa"** → bảng với header xám, nút "Thêm mới" đậm, "Xuất file" viền
5. **Test CRUD** từng entity qua REST API:
   ```bash
   curl http://localhost:8080/api/academic-years
   curl -X POST http://localhost:8080/api/academic-years -H 'Content-Type: application/json' -d '{...}'
   ```
6. **Maven test**: `./mvnw test` — tests cũ vẫn pass + thêm test cho entity mới
7. **Visual check**: mở browser, kiểm tra gray theme thống nhất, sidebar collapse/active, table hover

---

## Rủi ro & Giảm thiểu

| Rủi ro | Giảm thiểu |
|---|---|
| Database schema thay đổi nhiều | Dùng `ddl-auto=update` để Hibernate tự tạo bảng mới, không cần migration cho dev |
| Logo path khác nhau khi deploy | Đặt trong `static/images/`, tham chiếu `/images/logo.png` |
| CSS theme không đồng nhất | Định nghĩa CSS variables ở `:root`, mọi component dùng `var(--...)` |
| Thiếu favicon/logo SVG inline | Dùng PNG copy sẵn, có fallback nếu thiếu |
| Font tiếng Việt | Dùng web font hỗ trợ đầy đủ tiếng Việt (Inter hoặc system) |

---

## Quy trình thực hiện

1. **Bước 1 — Khởi tạo project**: copy `W1/project` → `W2/project`, thêm `.env`, `.env.example`, `README.md`
2. **Bước 2 — POM + Application config**: thêm Thymeleaf dependency, copy logo, tạo folder CSS/JS/templates
3. **Bước 3 — Backend entities**: tạo 14 entity mới + DAO + Service + ServiceImpl + Controller (REST)
4. **Bước 4 — Layout Thymeleaf**: fragments chung (sidebar, topbar, layout), CSS theme
5. **Bước 5 — Landing page**: riêng biệt, có ngôn ngữ + admin pill
6. **Bước 6 — Functional pages**: 16 trang (dashboard + 15 entity pages) với table + buttons + pagination
7. **Bước 7 — Tests**: thêm test cho entity mới, giữ test cũ Student
8. **Bước 8 — Verification**: chạy thử, smoke test CRUD trên browser

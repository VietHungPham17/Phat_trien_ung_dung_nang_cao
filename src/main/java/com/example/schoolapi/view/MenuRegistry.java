package com.example.schoolapi.view;

import java.util.List;

/**
 * Centralized metadata for sidebar menu items & dynamic CRUD pages.
 * Each entry knows its URL, Vietnamese label, icon glyph (Unicode), and the columns to render.
 */
public final class MenuRegistry {

    public record Column(String key, String header) {}

    public record Item(
            String key,
            String url,
            String label,
            String icon,
            List<Column> columns
    ) {}

    private static final List<Item> ITEMS = List.of(
            new Item("dashboard", "/dashboard", "Dashboard", "▤", List.of()),
            new Item("academic-year", "/academic-years", "Quản lý niên khóa", "▦",
                    List.of(new Column("tt", "TT"),
                            new Column("name", "TÊN NIÊN KHÓA"),
                            new Column("description", "MÔ TẢ"),
                            new Column("startYear", "NĂM BẮT ĐẦU"),
                            new Column("endYear", "NĂM KẾT THÚC"))),
            new Item("curriculum-framework", "/curriculum-frameworks", "Quản lý khung chương trình", "◈",
                    List.of(new Column("tt", "TT"),
                            new Column("name", "TÊN KHUNG"),
                            new Column("description", "MÔ TẢ"),
                            new Column("totalCredits", "TỔNG TÍN CHỈ"))),
            new Item("semester", "/semesters", "Quản lý học kỳ", "◎",
                    List.of(new Column("tt", "TT"),
                            new Column("name", "TÊN HỌC KỲ"),
                            new Column("startDate", "NGÀY BẮT ĐẦU"),
                            new Column("endDate", "NGÀY KẾT THÚC"))),
            new Item("subject-group", "/subject-groups", "Quản lý nhóm môn học", "◐",
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ"),
                            new Column("name", "TÊN NHÓM"),
                            new Column("description", "MÔ TẢ"))),
            new Item("subject", "/subjects", "Quản lý môn học", "◑",
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ MH"),
                            new Column("name", "TÊN MÔN HỌC"),
                            new Column("credits", "TÍN CHỈ"))),
            new Item("class-section", "/class-sections", "Quản lý lớp của môn học", "◒",
                    List.of(new Column("tt", "TT"),
                            new Column("sectionCode", "MÃ LỚP"),
                            new Column("maxStudents", "SĨ SỐ TỐI ĐA"))),
            new Item("classroom", "/classrooms", "Quản lý phòng học", "◓",
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ PHÒNG"),
                            new Column("building", "TÒA NHÀ"),
                            new Column("capacity", "SỨC CHỨA"))),
            new Item("education-level", "/education-levels", "Quản lý cấp bậc đào tạo", "◔",
                    List.of(new Column("tt", "TT"),
                            new Column("name", "CẤP BẬC"),
                            new Column("description", "MÔ TẢ"))),
            new Item("faculty", "/faculties", "Quản lý khoa", "◕",
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ KHOA"),
                            new Column("name", "TÊN KHOA"),
                            new Column("description", "MÔ TẢ"))),
            new Item("major", "/majors", "Quản lý chuyên ngành", "◖",
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ NGÀNH"),
                            new Column("name", "TÊN CHUYÊN NGÀNH"))),
            new Item("industry-group", "/industry-groups", "Quản lý nhóm ngành", "◗",
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ NHÓM"),
                            new Column("name", "TÊN NHÓM NGÀNH"))),
            new Item("lecturer", "/lecturers", "Quản lý giảng viên", "◘",
                    List.of(new Column("tt", "TT"),
                            new Column("fullName", "HỌ TÊN"),
                            new Column("email", "EMAIL"),
                            new Column("phone", "SỐ ĐIỆN THOẠI"),
                            new Column("degree", "HỌC VỊ"))),
            new Item("student", "/students", "Quản lý sinh viên", "◙",
                    List.of(new Column("tt", "TT"),
                            new Column("studentName", "HỌ TÊN"),
                            new Column("department", "KHOA"))),
            new Item("feedback", "/feedbacks", "Nhận xét & Phản hồi", "◚",
                    List.of(new Column("tt", "TT"),
                            new Column("senderName", "NGƯỜI GỬI"),
                            new Column("content", "NỘI DUNG"),
                            new Column("createdAt", "THỜI GIAN"))),
            new Item("cdr", "/cdrs", "Quản lý Chuẩn đầu ra (CĐR)", "◛",
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ CĐR"),
                            new Column("description", "MÔ TẢ"),
                            new Column("level", "CẤP ĐỘ")))
    );

    public static List<Item> items() {
        return ITEMS;
    }

    public static Item findByUrl(String url) {
        return ITEMS.stream().filter(i -> i.url().equals(url)).findFirst().orElse(null);
    }
}

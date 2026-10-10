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
    ) {
        public boolean hasSubmenu() {
            return "curriculum-framework".equals(key) || "student".equals(key) || "cdr".equals(key);
        }
    }

    private static final String I_DASH = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><rect x='3' y='3' width='7' height='9'></rect><rect x='14' y='3' width='7' height='5'></rect><rect x='14' y='12' width='7' height='9'></rect><rect x='3' y='16' width='7' height='5'></rect></svg>";
    private static final String I_YEAR = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><rect x='3' y='4' width='18' height='18' rx='2' ry='2'></rect><line x1='16' y1='2' x2='16' y2='6'></line><line x1='8' y1='2' x2='8' y2='6'></line><line x1='3' y1='10' x2='21' y2='10'></line></svg>";
    private static final String I_FRAME = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><polygon points='12 2 2 7 12 12 22 7 12 2'></polygon><polyline points='2 17 12 22 22 17'></polyline><polyline points='2 12 12 17 22 12'></polyline></svg>";
    private static final String I_SEM = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><circle cx='12' cy='12' r='10'></circle><polyline points='12 6 12 12 16 14'></polyline></svg>";
    private static final String I_SGRP = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z'></path></svg>";
    private static final String I_SUB = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z'></path><path d='M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z'></path></svg>";
    private static final String I_CSEC = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><rect x='2' y='3' width='20' height='14' rx='2' ry='2'></rect><line x1='8' y1='21' x2='16' y2='21'></line><line x1='12' y1='17' x2='12' y2='21'></line></svg>";
    private static final String I_ROOM = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z'></path><polyline points='9 22 9 12 15 12 15 22'></polyline></svg>";
    private static final String I_EDU = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><line x1='18' y1='20' x2='18' y2='10'></line><line x1='12' y1='20' x2='12' y2='4'></line><line x1='6' y1='20' x2='6' y2='14'></line></svg>";
    private static final String I_FAC = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><rect x='2' y='7' width='20' height='14' rx='2' ry='2'></rect><path d='M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16'></path></svg>";
    private static final String I_MAJ = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><circle cx='12' cy='12' r='10'></circle><line x1='22' y1='12' x2='18' y2='12'></line><line x1='6' y1='12' x2='2' y2='12'></line><line x1='12' y1='6' x2='12' y2='2'></line><line x1='12' y1='22' x2='12' y2='18'></line></svg>";
    private static final String I_IND = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><line x1='8' y1='6' x2='21' y2='6'></line><line x1='8' y1='12' x2='21' y2='12'></line><line x1='8' y1='18' x2='21' y2='18'></line><line x1='3' y1='6' x2='3.01' y2='6'></line><line x1='3' y1='12' x2='3.01' y2='12'></line><line x1='3' y1='18' x2='3.01' y2='18'></line></svg>";
    private static final String I_LEC = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2'></path><circle cx='12' cy='7' r='4'></circle></svg>";
    private static final String I_STU = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2'></path><circle cx='9' cy='7' r='4'></circle><path d='M23 21v-2a4 4 0 0 0-3-3.87'></path><path d='M16 3.13a4 4 0 0 1 0 7.75'></path></svg>";
    private static final String I_FB = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z'></path></svg>";
    private static final String I_CDR = "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><circle cx='12' cy='8' r='7'></circle><polyline points='8.21 13.89 7 23 12 20 17 23 15.79 13.88'></polyline></svg>";

    private static final List<Item> ITEMS = List.of(
            new Item("dashboard", "/dashboard", "Dashboard", I_DASH, List.of()),
            new Item("academic-year", "/academic-years", "Quản lý niên khóa", I_YEAR,
                    List.of(new Column("tt", "TT"),
                            new Column("name", "TÊN NIÊN KHÓA"),
                            new Column("description", "MÔ TẢ"),
                            new Column("startYear", "NĂM BẮT ĐẦU"),
                            new Column("endYear", "NĂM KẾT THÚC"))),
            new Item("curriculum-framework", "/curriculum-frameworks", "Quản lý khung chương trình", I_FRAME,
                    List.of(new Column("tt", "TT"),
                            new Column("name", "TÊN KHUNG"),
                            new Column("description", "MÔ TẢ"),
                            new Column("totalCredits", "TỔNG TÍN CHỈ"))),
            new Item("semester", "/semesters", "Quản lý học kỳ", I_SEM,
                    List.of(new Column("tt", "TT"),
                            new Column("name", "TÊN HỌC KỲ"),
                            new Column("startDate", "NGÀY BẮT ĐẦU"),
                            new Column("endDate", "NGÀY KẾT THÚC"))),
            new Item("subject-group", "/subject-groups", "Quản lý nhóm môn học", I_SGRP,
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ"),
                            new Column("name", "TÊN NHÓM"),
                            new Column("description", "MÔ TẢ"))),
            new Item("subject", "/subjects", "Quản lý môn học", I_SUB,
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ MH"),
                            new Column("name", "TÊN MÔN HỌC"),
                            new Column("credits", "TÍN CHỈ"))),
            new Item("class-section", "/class-sections", "Quản lý lớp của môn học", I_CSEC,
                    List.of(new Column("tt", "TT"),
                            new Column("sectionCode", "MÃ LỚP"),
                            new Column("maxStudents", "SĨ SỐ TỐI ĐA"))),
            new Item("classroom", "/classrooms", "Quản lý phòng học", I_ROOM,
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ PHÒNG"),
                            new Column("building", "TÒA NHÀ"),
                            new Column("capacity", "SỨC CHỨA"))),
            new Item("education-level", "/education-levels", "Quản lý cấp bậc đào tạo", I_EDU,
                    List.of(new Column("tt", "TT"),
                            new Column("name", "CẤP BẬC"),
                            new Column("description", "MÔ TẢ"))),
            new Item("faculty", "/faculties", "Quản lý khoa", I_FAC,
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ KHOA"),
                            new Column("name", "TÊN KHOA"),
                            new Column("description", "MÔ TẢ"))),
            new Item("major", "/majors", "Quản lý chuyên ngành", I_MAJ,
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ NGÀNH"),
                            new Column("name", "TÊN CHUYÊN NGÀNH"))),
            new Item("industry-group", "/industry-groups", "Quản lý nhóm ngành", I_IND,
                    List.of(new Column("tt", "TT"),
                            new Column("code", "MÃ NHÓM"),
                            new Column("name", "TÊN NHÓM NGÀNH"))),
            new Item("lecturer", "/lecturers", "Quản lý giảng viên", I_LEC,
                    List.of(new Column("tt", "TT"),
                            new Column("fullName", "HỌ TÊN"),
                            new Column("email", "EMAIL"),
                            new Column("phone", "SỐ ĐIỆN THOẠI"),
                            new Column("degree", "HỌC VỊ"))),
            new Item("student", "/students", "Quản lý sinh viên", I_STU,
                    List.of(new Column("tt", "TT"),
                            new Column("studentName", "HỌ TÊN"),
                            new Column("department", "KHOA"))),
            new Item("feedback", "/feedbacks", "Nhận xét & Phản hồi", I_FB,
                    List.of(new Column("tt", "TT"),
                            new Column("senderName", "NGƯỜI GỬI"),
                            new Column("content", "NỘI DUNG"),
                            new Column("createdAt", "THỜI GIAN"))),
            new Item("cdr", "/cdrs", "Quản lý Chuẩn đầu ra (CĐR)", I_CDR,
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

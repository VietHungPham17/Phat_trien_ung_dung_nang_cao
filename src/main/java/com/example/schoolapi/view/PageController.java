package com.example.schoolapi.view;

import com.example.schoolapi.dto.*;
import com.example.schoolapi.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {

    private final AcademicYearService academicYearService;
    private final CurriculumFrameworkService curriculumFrameworkService;
    private final SemesterService semesterService;
    private final SubjectGroupService subjectGroupService;
    private final SubjectService subjectService;
    private final ClassSectionService classSectionService;
    private final ClassroomService classroomService;
    private final EducationLevelService educationLevelService;
    private final FacultyService facultyService;
    private final MajorService majorService;
    private final IndustryGroupService industryGroupService;
    private final LecturerService lecturerService;
    private final com.example.schoolapi.service.StudentService studentService;
    private final FeedbackService feedbackService;
    private final CdrService cdrService;

    public PageController(AcademicYearService a, CurriculumFrameworkService c, SemesterService s,
                          SubjectGroupService sg, SubjectService sj, ClassSectionService cs,
                          ClassroomService cr, EducationLevelService el, FacultyService f,
                          MajorService m, IndustryGroupService ig, LecturerService le,
                          com.example.schoolapi.service.StudentService st, FeedbackService fb,
                          CdrService cd) {
        this.academicYearService = a;
        this.curriculumFrameworkService = c;
        this.semesterService = s;
        this.subjectGroupService = sg;
        this.subjectService = sj;
        this.classSectionService = cs;
        this.classroomService = cr;
        this.educationLevelService = el;
        this.facultyService = f;
        this.majorService = m;
        this.industryGroupService = ig;
        this.lecturerService = le;
        this.studentService = st;
        this.feedbackService = fb;
        this.cdrService = cd;
    }

    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("activeMenu", "dashboard");
        model.addAttribute("menuItems", MenuRegistry.items());
        return "dashboard";
    }

    @GetMapping("/academic-years")
    public String academicYears(Model model) {
        return renderList(model, "academic-year", "Quản lý niên khóa",
                academicYearService.findAll(false));
    }

    @GetMapping("/curriculum-frameworks")
    public String curriculumFrameworks(Model model) {
        return renderList(model, "curriculum-framework", "Quản lý khung chương trình",
                curriculumFrameworkService.findAll(false));
    }

    @GetMapping("/semesters")
    public String semesters(Model model) {
        return renderList(model, "semester", "Quản lý học kỳ",
                semesterService.findAll(false));
    }

    @GetMapping("/subject-groups")
    public String subjectGroups(Model model) {
        return renderList(model, "subject-group", "Quản lý nhóm môn học",
                subjectGroupService.findAll(false));
    }

    @GetMapping("/subjects")
    public String subjects(Model model) {
        return renderList(model, "subject", "Quản lý môn học",
                subjectService.findAll(false));
    }

    @GetMapping("/class-sections")
    public String classSections(Model model) {
        return renderList(model, "class-section", "Quản lý lớp của môn học",
                classSectionService.findAll(false));
    }

    @GetMapping("/classrooms")
    public String classrooms(Model model) {
        return renderList(model, "classroom", "Quản lý phòng học",
                classroomService.findAll(false));
    }

    @GetMapping("/education-levels")
    public String educationLevels(Model model) {
        return renderList(model, "education-level", "Quản lý cấp bậc đào tạo",
                educationLevelService.findAll(false));
    }

    @GetMapping("/faculties")
    public String faculties(Model model) {
        return renderList(model, "faculty", "Quản lý khoa",
                facultyService.findAll(false));
    }

    @GetMapping("/majors")
    public String majors(Model model) {
        return renderList(model, "major", "Quản lý chuyên ngành",
                majorService.findAll(false));
    }

    @GetMapping("/industry-groups")
    public String industryGroups(Model model) {
        return renderList(model, "industry-group", "Quản lý nhóm ngành",
                industryGroupService.findAll(false));
    }

    @GetMapping("/lecturers")
    public String lecturers(Model model) {
        return renderList(model, "lecturer", "Quản lý giảng viên",
                lecturerService.findAll(false));
    }

    @GetMapping("/students")
    public String students(Model model) {
        return renderList(model, "student", "Quản lý sinh viên",
                studentService.findAll(false));
    }

    @GetMapping("/feedbacks")
    public String feedbacks(Model model) {
        return renderList(model, "feedback", "Nhận xét & Phản hồi",
                feedbackService.findAll(false));
    }

    @GetMapping("/cdrs")
    public String cdrs(Model model) {
        return renderList(model, "cdr", "Quản lý Chuẩn đầu ra (CĐR)",
                cdrService.findAll(false));
    }

    private String renderList(Model model, String activeMenu, String title, List<?> items) {
        MenuRegistry.Item item = MenuRegistry.findByUrl(urlFor(activeMenu));
        model.addAttribute("activeMenu", activeMenu);
        model.addAttribute("pageTitle", title);
        model.addAttribute("menuItem", item);
        model.addAttribute("items", items);
        model.addAttribute("menuItems", MenuRegistry.items());
        return "list";
    }

    private String urlFor(String key) {
        return MenuRegistry.items().stream()
                .filter(i -> i.key().equals(key))
                .map(MenuRegistry.Item::url)
                .findFirst()
                .orElse("/");
    }
}

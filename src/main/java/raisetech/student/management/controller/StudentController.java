package raisetech.student.management.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.model.data.Student;
import raisetech.student.management.model.data.StudentCourse;
import raisetech.student.management.model.domain.StudentDetail;
import raisetech.student.management.model.services.StudentService;

@Controller
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;

  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/students")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentsCourses = service.searchStudentsCourseList();
    List<StudentDetail> studentsDetails = converter.convertStudentDetails(students,
        studentsCourses);

    model.addAttribute("studentList", studentsDetails);
    return "studentList";
  }

  @GetMapping("/students-courses")
  public String getStudentCourseList(Model model) {
    List<StudentCourse> studentsCourses = service.searchStudentsCourseList();

    model.addAttribute("studentCourseList", studentsCourses);
    return "studentCourseList"; // studentCourseList.htmlを指す。
  }

  @GetMapping("/students/new")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail()); //StudentDetailの要素を登録フォームに適用できるようにする
    return "registerStudent";
  }

  @PostMapping("/students")
  // ビューの登録フォームで入力されたstudentDetailの情報をstudentServiceに送る
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }

    // フォームで入力されたstudentDetailのstudentの情報とstudentCourseの情報（最初の一つ）をserviceにあるregisterStudentメソッドの引数とする
    service.registerStudent(studentDetail.getStudent(),
        studentDetail.getStudentCourse().getFirst());
    return "redirect:/students";
  }
}

package raisetech.student.management.model.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.model.data.Student;
import raisetech.student.management.model.data.StudentCourse;

/**
 * 受講生および受講生コースの情報をDBから取得する。
 */
@Mapper
public interface StudentRepository {

  /**
   * @return データベースから受講生の情報を全件取得する。
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   * @return データベースから受講生のコース情報を全件取得する。
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentsCourses();

  /**
   * @return 受講生の情報をデータベースに登録する。
   */
  @Insert("INSERT students values(#{id}, #{fullname}, #{furigana}, #{nickname}, #{mail}, #{address}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  // ↓SQLで自動生成されたidをStudentオブジェクトのidとして仕様できるようにする。
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  /**
   * @return 受講生コースの情報をデータベースに登録する。
   */
  @Insert("INSERT students_courses values(#{id}, #{studentId}, #{courseName}, #{startDate}, #{endDate})")
  void registerStudentCourse(StudentCourse studentCourse);
}

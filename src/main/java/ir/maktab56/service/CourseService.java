package ir.maktab56.service;

import ir.maktab56.model.Course;
import ir.maktab56.model.Student;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CourseService {

    List<Course> findAll();

    Course findCoursesByCourseId(String courseId);

    Course save(Course course);

    void deleteCourseByCourseId(String courseId);

    boolean removeProfessorIdFromCourse(Set<Course> courses);

    boolean removeStudentIdFromCourse(Student student);

    void updateCourse(List<Map<String, Object>> course);

    List<Course> findCoursesByProfessor_Username(String professorUsername);
}

package ir.maktab56.service.impl;

import ir.maktab56.model.Course;
import ir.maktab56.model.Student;
import ir.maktab56.repository.CourseRepository;
import ir.maktab56.service.CourseService;
import ir.maktab56.service.ProfessorService;
import ir.maktab56.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private ProfessorService professorService;
    private StudentService studentService;

    public CourseServiceImpl(CourseRepository courseRepository){
        this.repository = courseRepository;
    }

    @Autowired
    public void setProfessorService(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public List<Course> findAll() {
        return repository.findAll();
    }

    @Override
    public Course findCoursesByCourseId(String courseId) {
        return repository.findCoursesByCourseId(courseId);
    }

    @Override
    public Course save(Course course) {
        return repository.save(course);
    }

    @Override
    public void deleteCourseByCourseId(String courseId) {
        repository.deleteCourseByCourseId(courseId);
    }

    @Override
    public boolean removeProfessorIdFromCourse(Set<Course> courses) {

        for (Course course : courses) {
            course.setProfessor(null);
        }
        repository.saveAll(courses);

        return true;
    }

    @Override
    public boolean removeStudentIdFromCourse(Student student) {

        for (Course course : student.getCourse()) {
            course.getStudents().remove(student);
        }
        repository.saveAll(student.getCourse());
        return true;
    }

    @Override
    public void updateCourse(List<Map<String, Object>> course) {
        String title = (String) course.get(0).get("title");
        String courseId = (String) course.get(1).get("courseId");
        LocalDate startDate = LocalDate.parse((String) course.get(2).get("startDate"));
        LocalDate endDate = LocalDate.parse((String) course.get(3).get("endDate"));
        String professorUser = (String) course.get(4).get("professor");
        List<Map<String, String>> studentsUsername = (List<Map<String, String>>) course.get(5).get("students");

        Course existsCourse = findCoursesByCourseId(courseId);
        existsCourse.setTitle(title);
        existsCourse.setStartDate(startDate);
        existsCourse.setEndDate(endDate);
        if (professorUser != null)
            existsCourse.setProfessor(professorService.findUserByUsername(professorUser).orElse(null));

        Set<Student> students = new HashSet<>();
        studentsUsername.forEach(a -> students.add(studentService.findUserByUsername(a.get("username")).orElseThrow()));

        existsCourse.setStudents(students);
        save(existsCourse);
    }

    @Override
    public List<Course> findCoursesByProfessor_Username(String professorUsername) {
        return repository.findCoursesByProfessor_Username(professorUsername);
    }
}

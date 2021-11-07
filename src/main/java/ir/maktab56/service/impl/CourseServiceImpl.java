package ir.maktab56.service.impl;

import ir.maktab56.model.Course;
import ir.maktab56.model.Student;
import ir.maktab56.repository.CourseRepository;
import ir.maktab56.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;


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
}

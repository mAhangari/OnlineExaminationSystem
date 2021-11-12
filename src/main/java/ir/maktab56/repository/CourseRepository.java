package ir.maktab56.repository;

import ir.maktab56.model.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CourseRepository extends JpaRepository<Course, Long> {

    @EntityGraph(attributePaths = "professor")
    Course findCoursesByCourseId(String courseId);

    @Transactional(readOnly = false)
    void deleteCourseByCourseId(@Param(value = "courseId") String courseId);

    List<Course> findCoursesByProfessor_Username(String professorUsername);

}

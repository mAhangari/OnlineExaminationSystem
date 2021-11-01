package ir.maktab56.service.impl;

import ir.maktab56.model.Student;
import ir.maktab56.repository.UserRepository;
import ir.maktab56.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends UserServiceImpl<Student> implements StudentService {

    public StudentServiceImpl(UserRepository<Student> repository) {
        super(repository);
    }
}

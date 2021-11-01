package ir.maktab56.service.impl;

import ir.maktab56.model.Professor;
import ir.maktab56.repository.UserRepository;
import ir.maktab56.service.ProfessorService;
import org.springframework.stereotype.Service;

@Service
public class ProfessorServiceImpl extends UserServiceImpl<Professor> implements ProfessorService {

    public ProfessorServiceImpl(UserRepository<Professor> repository) {
        super(repository);
    }
}

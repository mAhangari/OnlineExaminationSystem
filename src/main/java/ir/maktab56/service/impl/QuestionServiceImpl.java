package ir.maktab56.service.impl;

import ir.maktab56.model.Question;
import ir.maktab56.repository.QuestionRepository;
import ir.maktab56.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository repository;

    @Override
    public Question save(Question question) {
        return repository.save(question);
    }
}

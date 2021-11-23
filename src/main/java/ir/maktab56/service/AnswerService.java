package ir.maktab56.service;

import ir.maktab56.model.Answer;

import java.util.Optional;

public interface AnswerService {

    Answer save(Answer answer);

    Optional<Answer> findByStudent_Username(String username);

}

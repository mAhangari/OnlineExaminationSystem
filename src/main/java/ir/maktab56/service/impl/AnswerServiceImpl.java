package ir.maktab56.service.impl;

import ir.maktab56.model.*;
import ir.maktab56.repository.AnswerRepository;
import ir.maktab56.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository repository;

    @Override
    public Answer save(Answer answer) {
        return repository.save(answer);
    }

    @Override
    public Optional<Answer> findByStudent_Username(String username) {

        return repository.findByStudent_Username(username);
    }


}

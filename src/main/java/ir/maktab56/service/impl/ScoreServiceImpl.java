package ir.maktab56.service.impl;

import ir.maktab56.model.Score;
import ir.maktab56.repository.ScoreRepository;
import ir.maktab56.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository repository;

    @Override
    public Score save(Score score) {
        return repository.save(score);
    }
}

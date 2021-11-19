package ir.maktab56.service;

import ir.maktab56.model.MultipleChoice;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MultipleChoiceService {

    MultipleChoice save(MultipleChoice question);

    Optional<MultipleChoice> findById(Long id);

    void creatOrUpdateMultipleChoice(List<Map<String, Object>> multipleChoice);
}

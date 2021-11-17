package ir.maktab56.service;

import ir.maktab56.model.MultipleChoice;

import java.util.List;
import java.util.Map;

public interface MultipleChoiceService {

    MultipleChoice save(MultipleChoice question);

    void creatMultipleChoice(List<Map<String, Object>> multipleChoice);
}

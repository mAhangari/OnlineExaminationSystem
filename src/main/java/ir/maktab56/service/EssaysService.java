package ir.maktab56.service;

import ir.maktab56.model.Essays;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EssaysService {

    Essays save(Essays question);

    Optional<Essays> findById(Long id);

    void creatOrUpdateEssays(List<Map<String, Object>> essays);
}

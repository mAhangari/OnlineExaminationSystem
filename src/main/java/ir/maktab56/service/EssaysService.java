package ir.maktab56.service;

import ir.maktab56.model.Essays;

import java.util.List;
import java.util.Map;

public interface EssaysService {

    Essays save(Essays question);

    void creatEssays(List<Map<String, Object>> essays);
}

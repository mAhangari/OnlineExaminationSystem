package ir.maktab56.repository;

import ir.maktab56.model.MultipleChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultipleChoiceRepository extends JpaRepository<MultipleChoice, Long> {
}

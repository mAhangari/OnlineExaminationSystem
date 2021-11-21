package ir.maktab56.repository;

import ir.maktab56.model.Essays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssaysRepository extends JpaRepository<Essays, Long> {
}

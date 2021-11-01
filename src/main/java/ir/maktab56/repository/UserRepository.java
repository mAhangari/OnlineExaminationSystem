package ir.maktab56.repository;

import ir.maktab56.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface UserRepository<E extends User> extends JpaRepository<E, Long> {

    @EntityGraph(attributePaths = {"roles", "roles.operations"})
    Optional<E> findUserByUsername(String username);

    boolean existsUserByUsername(String username);

}

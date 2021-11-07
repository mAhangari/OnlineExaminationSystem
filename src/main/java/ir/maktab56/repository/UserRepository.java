package ir.maktab56.repository;

import ir.maktab56.model.User;
import ir.maktab56.model.enumeration.UserType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@Transactional
public interface UserRepository<E extends User> extends JpaRepository<E, Long> {

    @EntityGraph(attributePaths = {"roles", "roles.operations"})
    Optional<E> findUserByUsername(String username);

    boolean existsUserByUsername(String username);

    List<E> findAllByIsActive(boolean active);

    @Query("SELECT u FROM User u WHERE (:userType IS NULL OR u.userType = :userType) AND u.userType <> 'ADMIN'" +
            " AND (:username IS NULL OR u.username = :username)" +
            " AND (:firstName IS NULL OR u.firstName = :firstName)" +
            " AND (:lastName IS NULL OR u.lastName = :lastName) AND u.isActive = TRUE")
    List<E> findAllByUserTypeAndUsernameAndFirstNameAndLastName(
            @Param(value = "userType") UserType userType,
            @Param(value = "username") String username,
            @Param(value = "firstName") String firstName,
            @Param(value = "lastName") String lastName
    );

    void deleteUserByUsername(String username);

    @Modifying
    @Query("update User u set u.isActive = true where u.username = :username")
    void updateUserIsActiveByUsernameAndIsActive(@Param(value = "username") String username);

}

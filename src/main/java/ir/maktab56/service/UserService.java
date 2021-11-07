package ir.maktab56.service;

import ir.maktab56.model.User;
import ir.maktab56.model.enumeration.UserType;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserService<E extends User> {

    Optional<E> findUserByUsername(String username);

    List<E> findAll();

    List<E> findAllByIsActive(boolean active);

    List<E> findAllByUserTypeAndUsernameAndFirstNameAndLastName(
            @Param(value = "userType") UserType userType,
            @Param(value = "username") String username,
            @Param(value = "firstName") String firstName,
            @Param(value = "lastName") String lastName
    );

    E save(E user);

    boolean existsUserByUsername(String username);

    void deleteUserByUsername(String username);

    void delete(E entity);

    void updateUserActiveByUsernameAndIsActive(@Param(value = "username") String username);

}

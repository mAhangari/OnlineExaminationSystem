package ir.maktab56.service;

import ir.maktab56.model.BaseEntity;
import ir.maktab56.model.User;

import java.util.Optional;

public interface UserService<E extends User> {

    Optional<E> findUserByUsername(String username);

    E save(E user);

    boolean existsUserByUsername(String username);
}

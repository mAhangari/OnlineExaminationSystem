package ir.maktab56.service.impl;

import ir.maktab56.model.User;
import ir.maktab56.model.enumeration.UserType;
import ir.maktab56.repository.UserRepository;
import ir.maktab56.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl<E extends User> implements UserService<E> {

    private final UserRepository<E> repository;

    public UserServiceImpl(UserRepository<E> repository){
        this.repository = repository;
    }

    @Override
    public Optional<E> findUserByUsername(String username) {
        return repository.findUserByUsername(username);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public List<E> findAllByIsActive(boolean active) {
        return repository.findAllByIsActive(active);
    }

    @Override
    public List<E> findAllByUserTypeAndUsernameAndFirstNameAndLastName(
            @Param(value = "userType") UserType userType, @Param(value = "username") String username,
            @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName) {
        return repository.findAllByUserTypeAndUsernameAndFirstNameAndLastName(
                userType, username, firstName, lastName);
    }

    @Override
    public E save(E user) {
        return repository.save(user);
    }

    @Override
    public boolean existsUserByUsername(String username) {
        return repository.existsUserByUsername(username);
    }

    @Override
    public void deleteUserByUsername(String username) {
        repository.deleteUserByUsername(username);
    }

    @Override
    public void delete(E entity) {
        repository.delete(entity);
    }

    @Override
    public void updateUserActiveByUsernameAndIsActive(@Param(value = "username") String username) {
        repository.updateUserIsActiveByUsernameAndIsActive(username);
    }

}

package ir.maktab56.service.impl;

import ir.maktab56.model.BaseEntity;
import ir.maktab56.model.User;
import ir.maktab56.repository.UserRepository;
import ir.maktab56.service.UserService;
import org.springframework.stereotype.Service;

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
    public E save(E user) {
        return repository.save(user);
    }

    @Override
    public boolean existsUserByUsername(String username) {
        return repository.existsUserByUsername(username);
    }

}

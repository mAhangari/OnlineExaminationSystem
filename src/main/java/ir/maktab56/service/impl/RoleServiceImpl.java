package ir.maktab56.service.impl;

import ir.maktab56.model.Role;
import ir.maktab56.repository.RoleRepository;
import ir.maktab56.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Optional<Role> findRoleByName(String name) {
        return repository.findRoleByName(name);
    }
}

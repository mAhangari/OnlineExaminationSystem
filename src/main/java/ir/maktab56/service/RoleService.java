package ir.maktab56.service;

import ir.maktab56.model.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findRoleByName(String name);
}

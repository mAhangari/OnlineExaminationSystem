package ir.maktab56.service.impl;

import ir.maktab56.model.Admin;
import ir.maktab56.model.enumeration.UserType;
import ir.maktab56.repository.AdminRepository;
import ir.maktab56.service.AdminService;
import ir.maktab56.service.RoleService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends UserServiceImpl<Admin> implements AdminService {

    private final AdminRepository repository;
    private final RoleService roleService;

    public AdminServiceImpl(AdminRepository repository, RoleService roleService) {
        super(repository);
        this.repository = repository;
        this.roleService = roleService;
    }

    @Override
    public void initAdmin() {
        if (repository.count() == 0) {
            Admin admin = new Admin();
            admin.setFirstName("Morteza");
            admin.setLastName("Ahangari");
            admin.setUsername("mAhangari");
            admin.setPassword(new BCryptPasswordEncoder().encode("Dan9011216"));
            admin.setNationalCode("2150382342");
            admin.setUserType(UserType.ADMIN);
            admin.setPersonnelId(9814163102L);
            admin.setRoles(roleService.findRoleByName("ADMIN").orElse(null));
            save(admin);
        }
    }
}

package ir.maktab56.service.impl;

import ir.maktab56.model.Admin;
import ir.maktab56.repository.UserRepository;
import ir.maktab56.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends UserServiceImpl<Admin> implements AdminService {

    public AdminServiceImpl(UserRepository<Admin> repository) {
        super(repository);
    }
}

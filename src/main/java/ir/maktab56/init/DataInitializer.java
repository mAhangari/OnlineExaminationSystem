package ir.maktab56.init;

import ir.maktab56.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final AdminService adminService;

    @PostConstruct
    public void initData() {
        initUsers();
    }

    private void initUsers() {
        adminService.initAdmin();
    }

}

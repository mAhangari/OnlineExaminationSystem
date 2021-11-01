package ir.maktab56.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExamController {

    @GetMapping(value = "/home")
    public String homePage() {
        return "redirect:/";
    }

//    @PostMapping("/user-page")
//    public String userPage() {
//        return "views/customer-profile";
//    }
//


    @GetMapping(value = "/sign-up")
    public String signUp() {
        return "views/sign-up";
    }


}

package ir.maktab56.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    // create get mapping for home page
    @GetMapping(value = "/home")
    public String homePage() {
        return "redirect:/";
    }

    // create get mapping for sign up page
    @GetMapping(value = "/sign-up")
    public String signUp() {
        return "views/sign-up";
    }

}

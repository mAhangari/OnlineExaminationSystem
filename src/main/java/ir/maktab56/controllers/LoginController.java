package ir.maktab56.controllers;

import ir.maktab56.model.User;
import ir.maktab56.model.enumeration.UserType;
import ir.maktab56.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService<User> userService;

    // mapping for login url
    @RequestMapping(value = "/login")
    public String loginForm() {
        return "views/login";
    }

    // forward user to their personal pages
    @RequestMapping(value = "/user-page")
    public String userPage(HttpServletRequest request) {
        Optional<User> user = userService.findUserByUsername(request.getParameter("username"));
        if (user.isPresent()) {
            if (user.get().getUserType().equals(UserType.ADMIN))
                return "redirect:admin/admin-profile";
            else if (user.get().getUserType().equals(UserType.PROFESSOR))
                return "redirect:professor/professor-profile";
            else
                return "redirect:student/student-profile";
        } else return "redirect:/";
    }

    // mapping for sign-out request
    @RequestMapping(value = "/sign-out")
    public String logout() {
        return "redirect:/";
    }
}

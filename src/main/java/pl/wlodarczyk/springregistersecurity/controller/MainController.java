package pl.wlodarczyk.springregistersecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.wlodarczyk.springregistersecurity.models.User;
import pl.wlodarczyk.springregistersecurity.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class MainController {

    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/forUser")
    public String forUser() {
        return "userpage";
    }

    @GetMapping("/forAdmin")
    public String forAdmin() {
        return "adminpage";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("register", "user", new User());
    }

    @RequestMapping("/register")
    public ModelAndView register(User user, HttpServletRequest request) {
        userService.addNewUser(user, request);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verify")
    public ModelAndView verify(@RequestParam String token){

        userService.verify(token);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verifyAdmin")
    public ModelAndView verifyAdmin(@RequestParam String token){
        userService.verify(token);
        userService.switchToAdmin(token);
        return new ModelAndView("redirect:/login");
    }
}

package pl.wlodarczyk.springregistersecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.wlodarczyk.springregistersecurity.models.User;
import pl.wlodarczyk.springregistersecurity.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
    public String login(ModelAndView model,String error) {
        model.addObject("error",error);
        return "login";
    }


    @RequestMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("register", "user", new User());
    }

    @RequestMapping("/register")
    public ModelAndView register(User user, HttpServletRequest request) {
        if(!userService.isExist(user))userService.addNewUser(user, request);
        else return new ModelAndView("login", "error", "User already exist");
        return new ModelAndView("login", "error", "");
    }

    @RequestMapping("/verify")
    public String verify(@RequestParam String token){
        userService.verify(token);
        return "redirect:/login";
    }

    @RequestMapping("/verifyAdmin")
    public String verifyAdmin(@RequestParam String token){
        userService.verify(token);
        return "redirect:/login";
    }
    @PostMapping("/forUser")
    public void afterLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/forUser");
    }
    @GetMapping("/")
    public String index(){
        return "redirect:/login";
    }
    @GetMapping("login/error")
    public ModelAndView loginError(){
        return new ModelAndView("login","error","bad credentials");
    }
}

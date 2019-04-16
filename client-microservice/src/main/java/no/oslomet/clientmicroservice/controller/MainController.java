package no.oslomet.clientmicroservice.controller;

import no.oslomet.clientmicroservice.model.User;
import no.oslomet.clientmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private User loggedUser;

    @Autowired
    private UserService userService;
    @GetMapping({"/", "/login"})
    public String index(){
        return "login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }


    @PostMapping("/login")
    public String login(@RequestParam("email")String email,
                                      @RequestParam("password") String password,
                                      Model model){
        System.out.println("inside login");
       User user = userService.login(new User(email, password));
       if(user == null){
           model.addAttribute("loginError", "Invalid username or password");
           return "login";
       } else {
           loggedUser = user;
           model.addAttribute("user", user);
           return "index";
       }
    }
    @GetMapping("/logout")
    public String logout(){
        loggedUser = null;
        return "index";
    }
    @PostMapping("/signup")
    public String processRegistration(@ModelAttribute("user") User user){
       userService.signup(user);
        return "redirect:/login";
    }


}

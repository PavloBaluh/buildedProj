package jarvizz.project.controllers;

import jarvizz.project.models.AccountCredentials;
import jarvizz.project.models.User;
import jarvizz.project.sevices.UserService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MainController {
    UserService userService;
    PasswordEncoder passwordEncoder;
    @GetMapping("/home")
    public String home (){
        return "lol";
    }


    @GetMapping("/register")
    public String register (User user){
        user.setUsername("qwe");
        user.setPassword("qwe");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "ok";
    }
}

package jarvizz.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jarvizz.project.models.*;
import jarvizz.project.sevices.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
public class MainController {
    UserService userService;
    FoodService foodService;
    PasswordEncoder passwordEncoder;
    MailService mailService;
    UserInfoService userInfoService;
    CardInfoService cardInfoService;
//    @GetMapping("/")
//    public String home (){
////        String property = System.getProperty("user.dir") + File.separator + "icons" +  File.separator + "pizza.jpg";
////        File file = new File(property);
////        String name = file.getName();
//
//        String s = "Телятина гриль на крем-соусі з буряка та булгуром/ Салат з печеного буряка на горошковому пюре з ароматною олією";
//        Food food = foodService.findAllByType(Type.BURGER).get(0);
//        food.setDescription(s);
//        foodService.save(food);
//        return "lol";
//    }


    @PostMapping("/register")
    public String register(@RequestHeader("username") String name,
                           @RequestHeader("password") String pass,
                           @RequestHeader("email") String mail) throws MessagingException, IOException {
        String good = "На вашу поштову адресу був відправлений лист з підтвердженням";
        String bad = "Користувач з такою поштовою адресю або логіном уже зареєстрований";
        User user = new User(name, passwordEncoder.encode(pass), mail);
        System.out.println(user);
        if (userService.findByEmail(mail) == null && userService.findByName(name) == null) {
            userService.save(user);
            mailService.send(mail, userService.findByEmail(mail));
            return good;
        }
        return bad;
    }
    @GetMapping("/basket")
    public List<Food> basket (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user =userService.findByName(name);
        List<Food> basket = user.getBasket();
        return basket;
    }

    @PostMapping("/updateUserInfo")
    public void updateUserInfo (HttpServletRequest request) throws IOException {
       UserInfo userInfo =  new ObjectMapper().readValue(request.getInputStream(), UserInfo.class);
       userInfo.getCardInfo().setUserInfo(userInfo);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user =userService.findByName(name);
        userInfo.setUser(user);
        userInfoService.save(userInfo);
    }

}

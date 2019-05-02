package jarvizz.project.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jarvizz.project.models.*;
import jarvizz.project.sevices.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
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
    OrderService orderService;
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
    public List<Food> basket() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userService.findByName(name);
        List<Food> basket = user.getBasket();
        return basket;
    }

    @PostMapping("/updateUserInfo")
    public void updateUserInfo(HttpServletRequest request) throws IOException {
        UserInfo userInfo = new ObjectMapper().readValue(request.getInputStream(), UserInfo.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userService.findByName(name);
        if (user.getUserInfo() != null) {
            UserInfo userInfo1 = userInfoService.get(user.getUserInfo().getId());
            userInfo1.setAddress(userInfo.getAddress());
            userInfo1.setName(userInfo.getName());
            userInfo1.setPhoneNumber(userInfo.getPhoneNumber());
            userInfo1.setSurname(userInfo.getSurname());
            userInfoService.save(userInfo1);
        } else {
            userInfo.setUser(user);
            userInfoService.save(userInfo);
            user.setUserInfo(userInfo);
            userService.save(user);
        }
    }

    @GetMapping("/getUserInfo")
    public UserInfo getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userService.findByName(name);
        return user.getUserInfo();
    }

    @PostMapping("/makeOrder/basket/{foods}")
    public String makeOrder(HttpServletRequest request,  @PathVariable("foods") String foodInp) throws IOException {
        String[] split = foodInp.split(",");
        List<Food> foods = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        List<Food> list = objectMapper.readValue(foodInp, TypeFactory.defaultInstance().constructCollectionType(List.class, Food.class));
        List<Food> list = new ArrayList<>();
        for (String s : split) {
            list.add(foodService.findByName(s));
        }
        Orders orders = objectMapper.readValue(request.getInputStream(),Orders.class);
        System.out.println(orders.getName());
        for (Food food : list) {
            Food byName = foodService.findByName(food.getName());
            byName.setOrder(orders);
            foods.add(byName);
        }
        orders.setFoods(foods);
        orderService.save(orders);
        return "";
    }

}

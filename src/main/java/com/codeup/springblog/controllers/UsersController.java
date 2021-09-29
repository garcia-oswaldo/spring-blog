package com.codeup.springblog.controllers;

import com.codeup.springblog.models.User;
import com.codeup.springblog.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {
    @Autowired
    private UserRepository userDao;

    @GetMapping("/user/create")
    public String createUserForm() {
        return "create";
    }

    @GetMapping("/user/{username}/ads")
    public String showUserAds(@PathVariable String username,
                              Model model
    ) {

        User userToDisplay = userDao.getByUsername(username);

        model.addAttribute("user", userToDisplay);


        return "user/displayAds";
    }

    @PostMapping("/user/create")
    @ResponseBody
    public String createUser(@RequestParam(name = "uname") String username, @RequestParam(name = "psw") String password) {
        System.out.println("Username" + username);
        System.out.println("Password" + password);
        return "user created";
    }

}

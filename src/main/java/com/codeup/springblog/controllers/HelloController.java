package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/hello")
//    tells us we are returning
    @ResponseBody
    public String helloFromSpring() {
        return "Hello from the world of Spring";
    }

    @GetMapping("/HelloWorld/{username}")
    @ResponseBody
    public String helloWorld(@PathVariable String username) {
        return "Hello" + username + "!";
    }

    @GetMapping("/happy_birthday/{username}/{age}")
    @ResponseBody
    public String helloWorld2(
            @PathVariable String username,
            @PathVariable int age,
            Model model
    ) {
        model.addAttribute ("name",username);
                model.addAttribute("age",age);

        return "Happy_Birthday";
    }
//    @GetMapping ("/random/number")
//    @ResponseBody
//    public int RandomNumber(){
//        Random r = new Random();
//        int low=1;
//
//    }
@GetMapping("weather")
    public String viewWeather(Model model){
        model.addAttribute("temp","98F");
        return "WeatherPage";
}
}

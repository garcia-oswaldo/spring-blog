package com.codeup.springblog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

public class DiceRoll {
    @GetMapping("/roll-dice/{guess}")
    @ResponseBody
    public int randomNumber (){
    Random  r = new Random();
    int low =1;
    int high=6;
    return r.nextInt(high-low)+ low;
    }
}

package com.warren.exercise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreController {

    @GetMapping("/store")
    public String toStore() {
        return "store";
    }

    @GetMapping("/")
    public String toIndex() {
        return "store";
    }

    @GetMapping("/cart")
    public String toCart() {
        return "cart";
    }

}

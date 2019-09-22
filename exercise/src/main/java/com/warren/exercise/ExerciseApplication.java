package com.warren.exercise;

import com.warren.exercise.service.Impl.ShoppingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExerciseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExerciseApplication.class, args);
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
//        shoppingService.appendCommodity("苹果", 20);
        String[] names = new String[20];
        names[0] = "苹果";
        shoppingService.deleteCommodity(names);
    }

}

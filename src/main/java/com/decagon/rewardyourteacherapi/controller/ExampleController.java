package com.decagon.rewardyourteacherapi.controller;

    import java.util.ArrayList;
import java.util.List;
    import java.util.regex.Pattern;

    import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class ExampleController {
        @RequestMapping(value = "/products", method = RequestMethod.GET)
        public List<String> getProducts() {
            List<String> productsList = new ArrayList<>();
            productsList.add("Honey");
            productsList.add("Almond");
            return productsList;
        }
        @RequestMapping(value = "/products", method = RequestMethod.POST)
        public String createProduct() {
            return "Product is saved successfully";
        }



        public static boolean patternMatches(String emailAddress, String regexPattern) {
            return Pattern.compile(regexPattern)
                    .matcher(emailAddress)
                    .matches();
        }
    }


package com.example.PBL6.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path ="/app/v1",
        produces = "application/json",
        method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE}
)
public class HomePageController {

    @GetMapping("")
    public String index() {
        return "helo123";
    }
}

package com.progmasters.mars.controller;

import com.progmasters.mars.service.IndividualUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class IndividualUserController {

    private final IndividualUserService individualUserService;

    public IndividualUserController(IndividualUserService individualUserService) {
        this.individualUserService = individualUserService;
    }

    @GetMapping("/test")
    public ResponseEntity saveUser() {

        //  userService.saveUser();
        return new ResponseEntity(HttpStatus.OK);
    }
}

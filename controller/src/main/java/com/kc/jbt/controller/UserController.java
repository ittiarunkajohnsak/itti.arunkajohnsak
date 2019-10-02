package com.kc.jbt.controller;

import com.kc.jbt.service.entity.UserEntity;
import com.kc.jbt.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>> getUsers() throws Exception {
        return ResponseEntity.ok(userService.findAll());
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<UserEntity> register(@RequestBody UserEntity userModel) {
        return ResponseEntity.ok(userService.create(userModel));
    }
}

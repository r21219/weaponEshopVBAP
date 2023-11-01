package cz.osu.weaponeshop.controller;

import cz.osu.weaponeshop.model.dto.UserDTO;
import cz.osu.weaponeshop.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl service;
    @PostMapping("/login")
    public void login(@RequestBody UserDTO userCredentials){

    }
    @PostMapping("/register")
    public void register(@RequestBody UserDTO registerCredentials){

    }
    @DeleteMapping
    public void deleteUser(@RequestBody UserDTO deletedUser){

    }
    @PutMapping
    public void updateUser(){

    }
}

package cz.osu.weaponeshop.controller;

import cz.osu.weaponeshop.model.dto.UserDTO;
import cz.osu.weaponeshop.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl service;
    @PostMapping("/login")
    @Operation(summary = "Logs in the user into the system", description = "Logs in the user")
    public void loginUser(@RequestBody UserDTO userCredentials){

    }
    @PostMapping("/register")
    @Operation(summary = "Registers users into the system", description = "Registers the user and adds them to the database")
    public void registerUser(@RequestBody UserDTO registerCredentials){

    }
    @DeleteMapping
    @Operation(summary = "Deletes a user based on given id", description = "Deletes user from the database")
    public void deleteUser(@RequestBody UserDTO deletedUser){

    }
    @PutMapping
    @Operation(summary = "Updates a user based on given id", description = "Updates user")
    public void updateUser(){

    }
}
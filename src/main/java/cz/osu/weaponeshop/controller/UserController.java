package cz.osu.weaponeshop.controller;

import cz.osu.weaponeshop.model.dto.user.LoginRequest;
import cz.osu.weaponeshop.model.dto.user.RegisterRequest;
import cz.osu.weaponeshop.model.dto.user.UpdateRequest;
import cz.osu.weaponeshop.model.response.AuthenticationResponse;
import cz.osu.weaponeshop.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl service;
    @PostMapping("/auth/register")
    @Operation(summary = "Registers users into the system", description = "Registers the user and adds them to the database")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/auth/login")
    @Operation(summary = "Logs the user into the system", description = "Authenticates the user then logs them in")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginRequest request){
        return ResponseEntity.ok(service.login(request));
    }
    @DeleteMapping
    @Operation(summary = "Deletes a user based on given request", description = "Deletes user from the database")
    public ResponseEntity<String> deleteUser(@RequestBody RegisterRequest deletedUser){
        service.deleteUser(deletedUser);
        return new ResponseEntity<>("User was successfully deleted", HttpStatus.OK);
    }
    @PutMapping
    @Operation(summary = "Updates a user based on given request", description = "Updates user")
    public ResponseEntity<String> updateUser(@RequestBody UpdateRequest updateRequest){
        service.updateUser(updateRequest);
        return new ResponseEntity<>("User was successfully updated", HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    @Operation(summary = "Gets a users username based on id", description = "Fetches userName from database")
    public ResponseEntity<String> getUser(@PathVariable Long userId){
        return new ResponseEntity<>(service.getUser(userId), HttpStatus.OK);
    }
}
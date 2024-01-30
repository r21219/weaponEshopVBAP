package cz.osu.weaponeshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @PostMapping
    @Operation(summary = "Creates a cart", description = "Creates a cart")
    public void createCart(){

    }
    @GetMapping
    @Operation(summary = "Get a cart ", description = "Logs in the user")
    public void getCart(){

    }
    @PutMapping
    @Operation(summary = "Logs in the user into the system", description = "Logs in the user")
    public void putCart(){

    }
    @DeleteMapping
    @Operation(summary = "Logs in the user into the system", description = "Logs in the user")
    public void deleteCart(){

    }
}
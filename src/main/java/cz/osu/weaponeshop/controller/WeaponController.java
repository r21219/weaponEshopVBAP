package cz.osu.weaponeshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weapon")
public class WeaponController {
    @PostMapping
    @Operation(summary = "Creates a weapon and adds it into the system", description = "Adds weapon into the database")
    public void addWeapon() {

    }

    @GetMapping
    @Operation(summary = "Gets a weapon based on id", description = "Fetches a weapon")
    public void getWeapon() {

    }

    @PutMapping
    @Operation(summary = "Updates the weapon based on give id", description = "Updates a weapon")
    public void updateWeapon() {

    }

    @DeleteMapping
    @Operation(summary = "Deletes a weapon based on id", description = "Deletes a weapon from the database")
    public void deleteWeapon() {

    }
}

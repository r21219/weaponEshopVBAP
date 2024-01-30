package cz.osu.weaponeshop.controller;

import cz.osu.weaponeshop.model.dto.WeaponDTO;
import cz.osu.weaponeshop.service.WeaponServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weapon")
public class WeaponController {
    private final WeaponServiceImpl service;
    @PostMapping
    @Operation(summary = "Creates a weapon and adds it into the system", description = "Adds weapon into the database")
    public ResponseEntity<String> addWeapon(@RequestBody WeaponDTO newWeapon) {
        service.addNewWeapon(newWeapon);
        return new ResponseEntity<>("Weapon successfully added", HttpStatus.OK);
    }

    @GetMapping("/{weaponId}")
    @Operation(summary = "Gets a weapon based on id", description = "Fetches a weapon")
    public ResponseEntity<WeaponDTO> getWeapon(@PathVariable Long weaponId) {
        return ResponseEntity.ok(service.getWeaponById(weaponId));
    }

    @PutMapping(("/{weaponId}"))
    @Operation(summary = "Updates the weapon based on give id", description = "Updates a weapon")
    public ResponseEntity<String> updateWeapon(@RequestBody WeaponDTO newWeapon, @PathVariable Long weaponId){
        service.updateWeapon(newWeapon,weaponId);
        return new ResponseEntity<>("Weapon successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/{weaponId}")
    @Operation(summary = "Deletes a weapon based on id", description = "Deletes a weapon from the database")
    public ResponseEntity<String> deleteWeapon(@PathVariable Long weaponId){
        service.removeWeaponById(weaponId);
        return new ResponseEntity<>("Weapon successfully deleted", HttpStatus.OK);
    }

}

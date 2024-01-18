package cz.osu.weaponeshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {
    @PostMapping
    @Operation(summary = "Creates a new tag", description = "Creates a tag and saves it into database")
    public void createTag(){

    }
    @GetMapping
    @Operation(summary = "Fetches a tag based on id", description = "Gets a tag based id")
    public void getTag(){

    }
    @PutMapping
    @Operation(summary = "Updates a tag based on id", description = "Updates a tag based on tag")
    public void updateTag(){

    }
    @DeleteMapping
    @Operation(summary = "Deletes a tag based on id", description = "Deletes a tag from the database")
    public void deleteTag(){

    }
}
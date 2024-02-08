package cz.osu.weaponeshop.controller;
import cz.osu.weaponeshop.model.dto.TagDTO;
import cz.osu.weaponeshop.service.TagServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagServiceImpl service;
    @PostMapping
    @Operation(summary = "Creates a tag and adds it into the system", description = "Adds tag into the database")
    public ResponseEntity<String> addTag(@RequestBody TagDTO newTag) {
        service.addNewTag(newTag);
        return new ResponseEntity<>("Tag successfully added", HttpStatus.OK);
    }

    @GetMapping("/{tagId}")
    @Operation(summary = "Gets a tag based on id", description = "Fetches a tag")
    public TagDTO getTag(@PathVariable Long tagId) {
        return service.getTagById(tagId);
    }

    @PutMapping(("/{tagId}"))
    @Operation(summary = "Updates the tag based on give id", description = "Updates a tag")
    public ResponseEntity<String> updateTag(@RequestBody TagDTO newTag, @PathVariable Long tagId){
        service.updateTag(newTag,tagId);
        return new ResponseEntity<>("Tag successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/{tagId}")
    @Operation(summary = "Deletes a tag based on id", description = "Deletes a tag from the database")
    public ResponseEntity<String> deleteTag(@PathVariable Long tagId){
        service.removeTagById(tagId);
        return new ResponseEntity<>("Tag successfully removed", HttpStatus.OK);
    }
}
package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.exception.BadRequestException;
import cz.osu.weaponeshop.exception.NotFoundException;
import cz.osu.weaponeshop.model.Tag;
import cz.osu.weaponeshop.model.Weapon;
import cz.osu.weaponeshop.model.dto.TagDTO;
import cz.osu.weaponeshop.repository.TagRepository;
import cz.osu.weaponeshop.repository.WeaponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl {
    private final TagRepository tagRepository;
    private final WeaponRepository weaponRepository;
    private final String TAG_NOT_FOUND = "Tag not found with ID: ";
    private final String TAG_ID_NULL = "Tag id cannot be empty";
    private final String TAG_INVALID = "Tag name cannot be empty";
    public void addNewTag(TagDTO newTagDTO) {
        if (newTagDTO.isEmpty()){
            throw new BadRequestException(TAG_INVALID);
        }
        Tag newTag = mapTagDTOToTag(newTagDTO);
        tagRepository.save(newTag);
    }

    public TagDTO getTagById(Long tagId) {
        if (tagId == null){
            throw new BadRequestException(TAG_ID_NULL);
        }
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        return optionalTag.map(this::mapTagToTagDTO)
                .orElseThrow(() -> new NotFoundException(TAG_NOT_FOUND + tagId));
    }

    public void updateTag(TagDTO updatedTagDTO, Long tagId) {
        if (updatedTagDTO.isEmpty()){
            throw new BadRequestException(TAG_INVALID);
        }
        if (tagId == null){
            throw new BadRequestException(TAG_ID_NULL);
        }
        Optional<Tag> optionalExistingTag = tagRepository.findById(tagId);

        optionalExistingTag.ifPresentOrElse(existingTag -> {
            updateTagFromTagDTO(existingTag, updatedTagDTO);
            tagRepository.save(existingTag);
        }, () -> {
            throw new NotFoundException(TAG_NOT_FOUND + tagId);
        });
    }

    public void removeTagById(Long tagId) {
        if (tagId == null){
            throw new BadRequestException(TAG_ID_NULL);
        }
        Optional<Tag> optionalTag = tagRepository.findById(tagId);

        optionalTag.ifPresentOrElse(tagToRemove -> {
            disassociateTagFromWeapons(tagToRemove);
            tagRepository.delete(tagToRemove);
        }, () -> {
            throw new NotFoundException(TAG_NOT_FOUND + tagId);
        });
    }

    private Tag mapTagDTOToTag(TagDTO tagDTO) {
        List<Weapon> weapons = fetchWeaponsFromDatabase(tagDTO.getWeaponIds());

        return Tag.builder()
                .name(tagDTO.getName())
                .weapons(weapons)
                .build();
    }

    private TagDTO mapTagToTagDTO(Tag tag) {
        return TagDTO.builder()
                .name(tag.getName())
                .weaponIds(tag.getWeapons().stream().map(Weapon::getId).collect(Collectors.toList()))
                .build();
    }

    private void updateTagFromTagDTO(Tag existingTag, TagDTO updatedTagDTO) {
        existingTag.setName(updatedTagDTO.getName());

        List<Weapon> weapons = fetchWeaponsFromDatabase(updatedTagDTO.getWeaponIds());
        existingTag.setWeapons(weapons);
    }

    private List<Weapon> fetchWeaponsFromDatabase(List<Long> weaponIds) {
        return weaponRepository.findAllById(weaponIds);
    }

    private void disassociateTagFromWeapons(Tag tag) {
        List<Weapon> weapons = tag.getWeapons();
        for (Weapon weapon : weapons) {
            weapon.getTags().remove(tag);
        }
    }
}

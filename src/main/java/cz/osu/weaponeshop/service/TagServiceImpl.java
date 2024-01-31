package cz.osu.weaponeshop.service;

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

    public void addNewTag(TagDTO newTagDTO) {
        Tag newTag = mapTagDTOToTag(newTagDTO);
        tagRepository.save(newTag);
        updateWeaponsBasedOnTagDTO(newTag, newTagDTO);
    }

    private void updateWeaponsBasedOnTagDTO(Tag tag, TagDTO tagDTO) {
/*        for (Long weaponId : tagDTO.getWeaponIds()){
            Weapon weapon = weaponRepository.findById(weaponId).orElseThrow(() -> new NotFoundException("Weapon not found with ID: " + weaponId));
            weapon.getTags().add(tag);
            weaponRepository.save(weapon);
        }*/
    }

    public TagDTO getTagById(Long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        return optionalTag.map(this::mapTagToTagDTO)
                .orElseThrow(() -> new NotFoundException("Tag not found with ID: " + tagId));
    }

    public void updateTag(TagDTO updatedTagDTO, Long tagId) {
        Optional<Tag> optionalExistingTag = tagRepository.findById(tagId);

        optionalExistingTag.ifPresentOrElse(existingTag -> {
            updateTagFromTagDTO(existingTag, updatedTagDTO);
            tagRepository.save(existingTag);
            updateWeaponsBasedOnTagDTO(existingTag, updatedTagDTO);
        }, () -> {
            throw new NotFoundException("Tag not found with ID: " + tagId);
        });
    }

    public void removeTagById(Long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);

        optionalTag.ifPresentOrElse(tagToRemove -> {
            disassociateTagFromWeapons(tagToRemove);
            tagRepository.delete(tagToRemove);
        }, () -> {
            throw new NotFoundException("Tag not found with ID: " + tagId);
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

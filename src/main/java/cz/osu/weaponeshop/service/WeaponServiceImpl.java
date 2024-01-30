package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.exception.NotFoundException;
import cz.osu.weaponeshop.model.Tag;
import cz.osu.weaponeshop.model.Weapon;
import cz.osu.weaponeshop.model.dto.WeaponDTO;
import cz.osu.weaponeshop.repository.TagRepository;
import cz.osu.weaponeshop.repository.WeaponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeaponServiceImpl {
    private final WeaponRepository repo;
    private final TagRepository tagRepo;

    public void addNewWeapon(WeaponDTO newWeaponDTO) {
        Weapon newWeapon = mapDTOToEntity(newWeaponDTO);
        repo.save(newWeapon);
    }

    public WeaponDTO getWeaponById(Long weaponId) {
        Optional<Weapon> optionalWeapon = repo.findById(weaponId);

        return optionalWeapon.map(this::mapEntityToDTO)
                .orElseThrow(() -> new NotFoundException("Weapon not found with ID: " + weaponId));
    }

    public void updateWeapon(WeaponDTO updatedWeaponDTO, Long weaponId) {
        Optional<Weapon> optionalExistingWeapon = repo.findById(weaponId);

        optionalExistingWeapon.ifPresentOrElse(existingWeapon -> {
            updateEntityFromDTO(existingWeapon, updatedWeaponDTO);
            repo.save(existingWeapon);
        }, () -> {
            throw new NotFoundException("Weapon not found with ID: " + weaponId);
        });
    }

    public void removeWeaponById(Long weaponId) {
        Optional<Weapon> optionalWeapon = repo.findById(weaponId);

        optionalWeapon.ifPresentOrElse(repo::delete, () -> {
                    throw new NotFoundException("Weapon not found with ID: " + weaponId);
                });
    }

    private Weapon mapDTOToEntity(WeaponDTO weaponDTO) {
        List<Tag> tags = fetchTagsFromDatabase(weaponDTO.getTagIds());

        return Weapon.builder()
                .name(weaponDTO.getName())
                .description(weaponDTO.getDescription())
                .tags(tags)
                .price(weaponDTO.getPrice())
                .build();
    }

    private WeaponDTO mapEntityToDTO(Weapon weapon) {
        return WeaponDTO.builder()
                .name(weapon.getName())
                .description(weapon.getDescription())
                .tagIds(weapon.getTags().stream().map(Tag::getId).collect(Collectors.toList()))
                .price(weapon.getPrice())
                .build();
    }

    private void updateEntityFromDTO(Weapon existingWeapon, WeaponDTO updatedWeaponDTO) {
        existingWeapon.setName(updatedWeaponDTO.getName());
        existingWeapon.setDescription(updatedWeaponDTO.getDescription());

        List<Tag> tags = fetchTagsFromDatabase(updatedWeaponDTO.getTagIds());
        existingWeapon.setTags(tags);

        existingWeapon.setPrice(updatedWeaponDTO.getPrice());
    }

    private List<Tag> fetchTagsFromDatabase(List<Long> tagIds) {
        return tagRepo.findAllById(tagIds);
    }
}

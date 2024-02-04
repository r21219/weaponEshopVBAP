package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.exception.BadRequestException;
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
    private final WeaponRepository weaponRepo;
    private final TagRepository tagRepo;
    private final String WEAPON_NOT_FOUND = "Weapon not found with ID: ";
    private final String WEAPON_ID_NULL = "Weapon id cannot be empty";
    private final String WEAPON_INVALID = "Invalid weapon name and description hast to be filled in, while the price has to be bigger than 0";

    public void addNewWeapon(WeaponDTO newWeaponDTO) {
        if (newWeaponDTO.isInValid()){
            throw new BadRequestException(WEAPON_INVALID);
        }
        Weapon newWeapon = mapWeaponDTOToWeapon(newWeaponDTO);
        weaponRepo.save(newWeapon);
    }

    public WeaponDTO getWeaponById(Long weaponId) {
        if (weaponId == null){
            throw new BadRequestException(WEAPON_ID_NULL);
        }
        Optional<Weapon> optionalWeapon = weaponRepo.findById(weaponId);

        return optionalWeapon.map(this::mapWeaponToWeaponDTO)
                .orElseThrow(() -> new NotFoundException(WEAPON_NOT_FOUND + weaponId));
    }

    public void updateWeapon(WeaponDTO updatedWeaponDTO, Long weaponId) {
        if (weaponId == null){
            throw new BadRequestException(WEAPON_ID_NULL);
        }
        if (updatedWeaponDTO.isInValid()){
            throw new BadRequestException(WEAPON_INVALID);
        }
        Optional<Weapon> optionalExistingWeapon = weaponRepo.findById(weaponId);

        optionalExistingWeapon.ifPresentOrElse(existingWeapon -> {
            updateWeaponFromWeaponDTO(existingWeapon, updatedWeaponDTO);
            weaponRepo.save(existingWeapon);
        }, () -> {
            throw new NotFoundException(WEAPON_NOT_FOUND + weaponId);
        });
    }
    public void removeWeaponById(Long weaponId) {
        if (weaponId == null){
            throw new BadRequestException(WEAPON_ID_NULL);
        }
        Optional<Weapon> optionalWeapon = weaponRepo.findById(weaponId);

        optionalWeapon.ifPresentOrElse(weaponRepo::delete, () -> {
                    throw new NotFoundException(WEAPON_NOT_FOUND + weaponId);
                });
    }

    private Weapon mapWeaponDTOToWeapon(WeaponDTO weaponDTO) {
        List<Tag> tags = fetchTagsFromDatabase(weaponDTO.getTagIds());

        return Weapon.builder()
                .name(weaponDTO.getName())
                .description(weaponDTO.getDescription())
                .tags(tags)
                .price(weaponDTO.getPrice())
                .build();
    }

    private WeaponDTO mapWeaponToWeaponDTO(Weapon weapon) {
        return WeaponDTO.builder()
                .name(weapon.getName())
                .description(weapon.getDescription())
                .tagIds(weapon.getTags().stream().map(Tag::getId).collect(Collectors.toList()))
                .price(weapon.getPrice())
                .build();
    }

    private void updateWeaponFromWeaponDTO(Weapon existingWeapon, WeaponDTO updatedWeaponDTO) {
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

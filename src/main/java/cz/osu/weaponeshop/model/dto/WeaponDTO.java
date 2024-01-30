package cz.osu.weaponeshop.model.dto;

import lombok.*;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeaponDTO {
    private String name;
    private String description;
    private List<Long> tagIds;
    private int price;
}

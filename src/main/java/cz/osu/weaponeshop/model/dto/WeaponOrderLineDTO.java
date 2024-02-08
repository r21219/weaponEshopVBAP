package cz.osu.weaponeshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeaponOrderLineDTO {
    private String weaponName;
    private int totalPrice;
    private int count;
}

package cz.osu.weaponeshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private String userName;
    private List<WeaponOrderLineDTO> weaponOrderLineDTO;
}

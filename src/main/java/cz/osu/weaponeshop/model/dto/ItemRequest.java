package cz.osu.weaponeshop.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private Long weaponId;
    private int count;
}

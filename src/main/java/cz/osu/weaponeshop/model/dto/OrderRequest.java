package cz.osu.weaponeshop.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long cartId;
    private Long weaponId;
    private int count;
}

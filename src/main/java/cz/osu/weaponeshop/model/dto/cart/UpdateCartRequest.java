package cz.osu.weaponeshop.model.dto.cart;

import cz.osu.weaponeshop.model.dto.ItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartRequest {
    private List<ItemRequest> requestedItems;
}

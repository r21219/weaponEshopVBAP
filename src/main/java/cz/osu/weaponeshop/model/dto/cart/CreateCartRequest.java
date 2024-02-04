package cz.osu.weaponeshop.model.dto.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.osu.weaponeshop.model.dto.ItemRequest;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartRequest {
    String userName;
    private List<ItemRequest> requestedItems;
    @JsonIgnore
    public boolean isEmpty(){
        return StringUtils.isEmpty(userName);
    }
}

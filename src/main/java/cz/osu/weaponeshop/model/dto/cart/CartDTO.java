package cz.osu.weaponeshop.model.dto.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.osu.weaponeshop.model.dto.WeaponOrderLineDTO;
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
public class CartDTO {
    private String userName;
    private List<WeaponOrderLineDTO> weaponOrderLineDTO;
    @JsonIgnore
    public boolean isEmpty(){
        return StringUtils.isEmpty(userName);
    }
}

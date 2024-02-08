package cz.osu.weaponeshop.model.dto.weapon;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class WeaponDisplay {
    private Long id;
    private String name;
    private String description;
    private List<String> tags;
    private int price;

    @JsonIgnore
    public boolean isInValid(){
        return StringUtils.isEmpty(name) ||  StringUtils.isEmpty(description) || price <= 0;
    }
}

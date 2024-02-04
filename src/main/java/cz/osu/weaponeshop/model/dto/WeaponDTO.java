package cz.osu.weaponeshop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.util.StringUtils;
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

    @JsonIgnore
    public boolean isInValid(){
        return StringUtils.isEmpty(name) ||  StringUtils.isEmpty(description) || price <= 0;
    }
}

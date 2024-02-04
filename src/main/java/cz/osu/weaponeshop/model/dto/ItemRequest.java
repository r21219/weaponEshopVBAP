package cz.osu.weaponeshop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private Long weaponId;
    private int count;
    @JsonIgnore
    public boolean isInValid(){
        return weaponId == null || count <= 0;
    }
}

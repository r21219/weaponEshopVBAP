package cz.osu.weaponeshop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.util.StringUtils;
import lombok.*;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private String name;
    private List<Long> weaponIds;
    @JsonIgnore
    public boolean isEmpty(){
        return StringUtils.isEmpty(name);
    }
}

package cz.osu.weaponeshop.model.dto;

import lombok.*;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private String name;
    private List<Long> weaponIds;
}

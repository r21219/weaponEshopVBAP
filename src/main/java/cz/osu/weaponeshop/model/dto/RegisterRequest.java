package cz.osu.weaponeshop.model.dto;

import cz.osu.weaponeshop.validator.ValidUsername;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @ValidUsername
    private String userName;
    private String password;
}

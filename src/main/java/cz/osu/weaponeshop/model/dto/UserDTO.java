package cz.osu.weaponeshop.model.dto;

import cz.osu.weaponeshop.validator.ValidPassword;
import cz.osu.weaponeshop.validator.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @ValidUsername
    private String userName;
    @ValidPassword
    private String password;
}

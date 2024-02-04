package cz.osu.weaponeshop.model.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String userName;
    private String password;

    @JsonIgnore
    public boolean isEmpty(){
        return StringUtils.isEmpty(userName) ||  StringUtils.isEmpty(password);
    }
}

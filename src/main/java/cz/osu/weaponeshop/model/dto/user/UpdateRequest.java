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
public class UpdateRequest {
    private String newUserName;
    private String currentUserName;
    @JsonIgnore
    public boolean isEmpty(){
        return StringUtils.isEmpty(newUserName) ||  StringUtils.isEmpty(currentUserName);
    }
}

package andre.chamis.socialnetwork.domain.user.dto;

import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDTO {
    private Long userId;
    private String username;
    private String email;

    public static GetUserDTO fromUser(User user) {
        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUserId(user.getUserId());
        getUserDTO.setUsername(user.getUsername());
        getUserDTO.setEmail(user.getEmail());

        return getUserDTO;
    }
}

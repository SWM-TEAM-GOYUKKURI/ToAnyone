package anyone.to.soma.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponse {
    private String name;
    private String email;
    private String token;
    private boolean registrationFormFilled;
}

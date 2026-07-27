package tobyspring.splearn.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record MemberLoginRequest(
        @Email String email,
        @Size(min = 8, max = 20) String password
) {
}

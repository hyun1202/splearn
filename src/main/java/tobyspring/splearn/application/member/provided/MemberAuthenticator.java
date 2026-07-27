package tobyspring.splearn.application.member.provided;

import jakarta.validation.Valid;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberLoginRequest;

/**
 * 회원 인증
 */
public interface MemberAuthenticator {
    Member login(@Valid MemberLoginRequest loginRequest) throws LoginFailedException;
}

package tobyspring.splearn.application.member.provided;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.domain.member.MemberLoginRequest;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;

import static org.assertj.core.api.Assertions.assertThat;


@ApplicationServiceTest
@RequiredArgsConstructor
class MemberAuthenticatorTest {
    final MemberAuthenticator memberAuthenticator;
    final MemberRegister memberRegister;

    @Test
    void login() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        Member member = memberRegister.register(registerRequest);
        member.activate();

        var loggedInMember = memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), registerRequest.password()));

        assertThat(member.getId()).isEqualTo(loggedInMember.getId());
    }

    @Test
    void loginFailedNotActivate() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest);

        Assertions.assertThatThrownBy(() -> {
            memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), registerRequest.password()));
        }).isInstanceOf(LoginFailedException.class);
    }

    @Test
    void loginFailedEmailNotExists() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        Assertions.assertThatThrownBy(() -> {
            memberAuthenticator.login(new MemberLoginRequest("notexists@email.com", registerRequest.password()));
        }).isInstanceOf(LoginFailedException.class);
    }

    @Test
    void loginFailedWrongPassword() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        Assertions.assertThatThrownBy(() -> {
            memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), "wrongPassword"));
        }).isInstanceOf(LoginFailedException.class);
    }
}
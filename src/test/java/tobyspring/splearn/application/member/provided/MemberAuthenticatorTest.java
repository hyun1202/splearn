package tobyspring.splearn.application.member.provided;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.domain.member.MemberLoginRequest;


@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
@RequiredArgsConstructor
class MemberAuthenticatorTest {
    final MemberAuthenticator memberAuthenticator;
    final MemberRegister memberRegister;

    @Test
    void login() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        var member = memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), registerRequest.password()));

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
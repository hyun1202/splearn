package tobyspring.splearn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.*;

@Getter
@ToString
public class Member {
    private String email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    @Builder
    private Member(String email, String nickname, String passwordHash) {
        this.email = requireNonNull(email);
        this.nickname = requireNonNull(nickname);
        this.passwordHash = requireNonNull(passwordHash);

        this.status = MemberStatus.PENDING;
    }

    private Member() {

    }

    // 파라미터가 너무 길어 실수를 줄이기 위한 방법 2. 정적 팩토리 + 파라미터 객체
    public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = requireNonNull(createRequest.email());
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));

        member.status = MemberStatus.PENDING;
        return member;
    }

    // 파라미터가 너무 길어 실수를 줄이기 위한 방법 1. 빌더 패턴 이용
//    public static Member create(String email, String nickname, String password, PasswordEncoder passwordEncoder) {
//        return Member.builder()
//                .email(email)
//                .nickname(nickname)
//                .passwordHash(passwordEncoder.encode(password))
//                .build();
//    }

    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        this.status = MemberStatus.ACTIVATE;
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVATE, "ACTIVATE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = requireNonNull(nickname);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVATE;
    }
}

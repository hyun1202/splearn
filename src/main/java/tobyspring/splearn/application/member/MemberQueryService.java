package tobyspring.splearn.application.member;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import tobyspring.splearn.application.member.provided.MemberFinder;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.support.stereotype.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당하는 회원을 찾을 수 없습니다. id: " + memberId));
        return member;
    }
}

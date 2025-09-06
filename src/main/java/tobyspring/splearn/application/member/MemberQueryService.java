package tobyspring.splearn.application.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tobyspring.splearn.application.member.provided.MemberFinder;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.member.Member;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당하는 회원을 찾을 수 없습니다. id: " + memberId));
        return member;
    }
}

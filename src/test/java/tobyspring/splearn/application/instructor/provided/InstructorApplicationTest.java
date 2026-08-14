package tobyspring.splearn.application.instructor.provided;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.application.instructor.required.InstructorRepository;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.instructor.InstructorStatus;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class InstructorApplicationTest {
    final InstructorApplication instructorApplication;
    final InstructorRepository instructorRepository;
    final MemberRepository memberRepository;

    @Test
    void apply() {
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);

        Instructor instructor = instructorApplication.apply(new InstructorApplyRequest(member.getId()));
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.PENDING);
    }

    @Test
    void duplicateApply() {
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);
        instructorApplication.apply(new InstructorApplyRequest(member.getId()));

        assertThatThrownBy(() -> instructorApplication.apply(new InstructorApplyRequest(member.getId())))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void approve() {
        Instructor instructor = instructorApplication.approve(preparedPendingInstructor().getId());

        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.ACTIVE);
    }

    @Test
    void reject() {
        Instructor instructor = instructorApplication.reject(preparedPendingInstructor().getId());

        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.REJECTED);
    }

    private Instructor preparedPendingInstructor() {
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);

        return instructorApplication.apply(new InstructorApplyRequest(member.getId()));
    }

}
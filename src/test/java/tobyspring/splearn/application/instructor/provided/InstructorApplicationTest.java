package tobyspring.splearn.application.instructor.provided;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.application.instructor.required.InstructorRepository;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.instructor.InstructorFixture;
import tobyspring.splearn.domain.instructor.InstructorStatus;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;
import tobyspring.splearn.support.test.BaseApplicationServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ApplicationServiceTest
@RequiredArgsConstructor
class InstructorApplicationTest extends BaseApplicationServiceTest {
    final InstructorApplication instructorApplication;
    final InstructorRepository instructorRepository;
    final MemberRepository memberRepository;

    @Test
    void apply() {
        prepareMember();

        Instructor instructor = instructorApplication.apply(InstructorFixture.createApplyRequest(member));
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.PENDING);
    }

    @Test
    void duplicateApply() {
        prepareMember();

        instructorApplication.apply(InstructorFixture.createApplyRequest(member));

        assertThatThrownBy(() -> instructorApplication.apply(InstructorFixture.createApplyRequest(member)))
                .isInstanceOf(DuplicateInstructorApplicationException.class);
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
        prepareMember();

        return instructorApplication.apply(InstructorFixture.createApplyRequest(member));
    }

}
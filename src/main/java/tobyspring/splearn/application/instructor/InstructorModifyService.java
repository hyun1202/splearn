package tobyspring.splearn.application.instructor;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tobyspring.splearn.application.instructor.provided.DuplicateInstructorApplicationException;
import tobyspring.splearn.application.instructor.provided.InstructorApplication;
import tobyspring.splearn.application.instructor.provided.InstructorApplyRequest;
import tobyspring.splearn.application.instructor.provided.InstructorFinder;
import tobyspring.splearn.application.instructor.required.InstructorRepository;
import tobyspring.splearn.application.member.provided.MemberFinder;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.member.Member;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
public class InstructorModifyService implements InstructorApplication {
    private final InstructorRepository instructorRepository;
    private final MemberFinder memberFinder;
    private final InstructorFinder instructorFinder;

    @Override
    public Instructor apply(InstructorApplyRequest applyRequest) {
        Member member = memberFinder.find(applyRequest.memberId());

        checkDuplicateApplication(member);

        Instructor instructor = Instructor.apply(member);

        return instructorRepository.save(instructor);
    }

    private void checkDuplicateApplication(Member member) {
        if (instructorRepository.findByMemberId(member.getId()).isPresent()) {
            throw new DuplicateInstructorApplicationException("중복으로 강사를 신청할 수 없습니다. ID: " + member.getId());
        }
    }

    @Override
    public Instructor approve(Long instructorId) {
        Instructor instructor = instructorFinder.find(instructorId);

        instructor.approve();

        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor reject(Long instructorId) {
        Instructor instructor = instructorFinder.find(instructorId);

        instructor.reject();

        return instructorRepository.save(instructor);
    }
}

package tobyspring.splearn.support.test;

import org.springframework.beans.factory.annotation.Autowired;
import tobyspring.splearn.application.instructor.provided.InstructorApplication;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.instructor.InstructorFixture;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;

@ApplicationServiceTest
public class BaseApplicationServiceTest {
    @Autowired MemberRegister memberRegister;
    @Autowired InstructorApplication instructorApplication;

    protected Member member;
    protected Instructor instructor;

    protected Instructor prepareInstructor(String email) {
        member = prepareMember(email);

        instructor = instructorApplication.apply(InstructorFixture.createApplyRequest(member));
        instructor.approve();

        return instructor;
    }

    protected Member prepareMember(String email) {
        member = memberRegister.register(MemberFixture.createMemberRegisterRequest(email));
        member.activate();
        return member;
    }

    protected Member prepareMember() {
        return prepareMember("test@test.com");
    }
}

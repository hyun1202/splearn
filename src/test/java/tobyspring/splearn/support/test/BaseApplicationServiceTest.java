package tobyspring.splearn.support.test;

import org.springframework.beans.factory.annotation.Autowired;
import tobyspring.splearn.application.course.provided.CourseCreator;
import tobyspring.splearn.application.instructor.provided.InstructorApplication;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.domain.course.CourseFixture;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.instructor.InstructorFixture;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;

@ApplicationServiceTest
public class BaseApplicationServiceTest {
    @Autowired CourseCreator courseCreator;
    @Autowired MemberRegister memberRegister;
    @Autowired InstructorApplication instructorApplication;

    protected Member member;
    protected Instructor instructor;
    protected Course course;

    private static final String EMAIL = "test@test.com";

    protected Instructor prepareInstructor() {
        return prepareInstructor(EMAIL);
    }

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
        return prepareMember(EMAIL);
    }

    protected Course prepareCourse() {
        String title = "title";
        prepareInstructor();

        course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), title));
        course.updateInfo(CourseFixture.createCourseInfoUpdateRequest(title).toInfo());

        return course;
    }
}

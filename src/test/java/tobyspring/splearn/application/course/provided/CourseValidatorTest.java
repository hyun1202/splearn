package tobyspring.splearn.application.course.provided;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.application.course.required.CourseRepository;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.domain.course.CourseFixture;
import tobyspring.splearn.support.exception.ValidationException;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;
import tobyspring.splearn.support.test.BaseApplicationServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ApplicationServiceTest
@RequiredArgsConstructor
class CourseValidatorTest extends BaseApplicationServiceTest {
    final CourseValidator courseValidator;
    final CourseRepository courseRepository;

    @Test
    void titleDuplication() {
        var instructor1 = prepareInstructor("test@test.com");

        var instructor2 = prepareInstructor("test2@test.com");

        Course course1 = courseRepository.save(CourseFixture.createCourse(instructor1, "Clean Spring"));
        Course course2 = courseRepository.save(CourseFixture.createCourse(instructor2, "Clean Code"));

        // 강사 1 강의 제목 중복 x
        courseValidator.validateForCreate(instructor1, new CourseCreateRequest(instructor1.getId(), "Spring 7", null));

        // 강사 1 강의 제목 중복 -> 예외 발생
        assertThatThrownBy(() -> courseValidator.validateForCreate(instructor1, new CourseCreateRequest(instructor1.getId(), "Clean Spring", null)))
                .isInstanceOfSatisfying(ValidationException.class, e -> {
                    assertThat(e.getErrors()).hasSize(1);
        });


        // 강사가 다른 경우 제목 중복 상관 없음
        courseValidator.validateForCreate(instructor2, new CourseCreateRequest(instructor2.getId(), "Spring Spring", null));
    }
}
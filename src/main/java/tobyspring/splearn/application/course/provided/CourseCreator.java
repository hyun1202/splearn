package tobyspring.splearn.application.course.provided;

import jakarta.validation.Valid;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.support.exception.ValidationException;

/**
 * 강의 준비
 */
public interface CourseCreator {
    Course create(@Valid CourseCreateRequest request) throws ValidationException;

    Course updateInfo(Long courseId, @Valid CourseInfoUpdateRequest updateRequest) throws ValidationException;
}

package tobyspring.splearn.application.course.provided;

import jakarta.validation.Valid;
import tobyspring.splearn.domain.course.Course;

/**
 * 강의 준비
 */
public interface CourseCreator {
    Course create(@Valid CourseCreateRequest request);

    Course updateInfo(Long courseId, @Valid CourseInfoUpdateRequest updateRequest);
}

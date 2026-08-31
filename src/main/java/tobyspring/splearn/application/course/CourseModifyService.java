package tobyspring.splearn.application.course;

import lombok.RequiredArgsConstructor;
import tobyspring.splearn.application.course.provided.*;
import tobyspring.splearn.application.course.required.CourseRepository;
import tobyspring.splearn.application.instructor.provided.InstructorFinder;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.support.exception.ValidationException;
import tobyspring.splearn.support.stereotype.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class CourseModifyService implements CourseCreator {
    private final CourseRepository courseRepository;
    private final CourseFinder courseFinder;
    private final InstructorFinder instructorFinder;
    private final CourseValidator courseValidator;

    @Override
    public Course create(CourseCreateRequest request) throws ValidationException {
        // 1. 강사 찾기
        Instructor instructor = instructorFinder.find(request.instructorId());

        // 2. 검증
        courseValidator.validateForCreate(instructor, request);

        // 3. 저장
        Course course = new Course(instructor, request.title(), request.description());

        return courseRepository.save(course);
    }

    @Override
    public Course updateInfo(Long courseId, CourseInfoUpdateRequest updateRequest) {
        Course course = courseFinder.find(courseId);

        courseValidator.validateForUpdate(course, updateRequest);

        course.updateInfo(updateRequest.toInfo());

        return courseRepository.save(course);
    }
}

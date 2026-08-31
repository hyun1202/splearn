package tobyspring.splearn.application.course;

import lombok.RequiredArgsConstructor;
import tobyspring.splearn.application.course.provided.CourseCreateRequest;
import tobyspring.splearn.application.course.provided.CourseCreator;
import tobyspring.splearn.application.course.provided.CourseFinder;
import tobyspring.splearn.application.course.provided.CourseInfoUpdateRequest;
import tobyspring.splearn.application.course.required.CourseRepository;
import tobyspring.splearn.application.instructor.provided.InstructorFinder;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.support.stereotype.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class CourseModifyService implements CourseCreator {
    private final CourseRepository courseRepository;
    private final CourseFinder courseFinder;
    private final InstructorFinder instructorFinder;

    @Override
    public Course create(CourseCreateRequest request) {
        // 1. 강사 찾기
        Instructor instructor = instructorFinder.find(request.instructorId());

        // 2. 검증


        // 3. 저장
        return null;
    }

    @Override
    public Course updateInfo(Long courseId, CourseInfoUpdateRequest updateRequest) {
        return null;
    }
}

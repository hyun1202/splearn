package tobyspring.splearn.application.course;

import lombok.RequiredArgsConstructor;
import tobyspring.splearn.application.course.provided.CourseCreateRequest;
import tobyspring.splearn.application.course.provided.CourseInfoUpdateRequest;
import tobyspring.splearn.application.course.provided.CourseValidator;
import tobyspring.splearn.application.course.required.CourseRepository;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.support.exception.ValidationException;
import tobyspring.splearn.support.stereotype.ApplicationService;

import java.util.ArrayList;

@ApplicationService
@RequiredArgsConstructor
public class CourseValidationService implements CourseValidator {
    private final CourseRepository courseRepository;

    @Override
    public void validateForCreate(Instructor instructor, CourseCreateRequest request) throws ValidationException {
        instructor.ensureActive();

        ArrayList<String> errors = new ArrayList<>();

        checkTitleDuplicationForCreate(instructor, request.title(), errors);
        checkBannedWords(request.title(), errors);
        checkBannedWords(request.description(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    @Override
    public void validateForUpdate(Course course, CourseInfoUpdateRequest updateRequest) throws ValidationException {
        ArrayList<String> errors = new ArrayList<>();

        checkTitleDuplicationForUpdate(course, course.getInstructor(), updateRequest.title(), errors);
        checkBannedWords(updateRequest.title(), errors);
        checkBannedWords(updateRequest.description(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void checkBannedWords(String text, ArrayList<String> errors) {

        // TODO
    }

    private void checkTitleDuplicationForCreate(Instructor instructor, String title, ArrayList<String> errors) {
        if (courseRepository.findByInstructorAndTitle(instructor, title).isPresent()) {
            errors.add("이미 사용 중인 강의 제목입니다." + title);
        }
    }

    private void checkTitleDuplicationForUpdate(Course course, Instructor instructor, String title, ArrayList<String> errors) {
        courseRepository.findByInstructorAndTitle(instructor, title).ifPresent(found -> {
            if (!found.equals(course)) {
                errors.add("이미 사용 중인 강의 제목입니다. " + title);
            }
        });
    }
}

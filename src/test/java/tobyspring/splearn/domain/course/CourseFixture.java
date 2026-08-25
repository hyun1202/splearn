package tobyspring.splearn.domain.course;

import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.instructor.InstructorFixture;

public class CourseFixture {
    public static Course createCourse() {
        return createCourse(InstructorFixture.createActiveInstructor());
    }

    public static Course createCourse(Instructor instructor) {
        return createCourse(instructor, "title");
    }

    public static Course createCourse(Instructor instructor, String title) {
        return new Course(instructor, title, "description");
    }
}

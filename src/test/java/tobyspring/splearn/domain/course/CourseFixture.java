package tobyspring.splearn.domain.course;

import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.instructor.InstructorFixture;

public class CourseFixture {
    public static Course createCourse() {
        Instructor instructor = InstructorFixture.createActiveInstructor();

        Course course = new Course(instructor, "title", "description");
        return course;
    }
}

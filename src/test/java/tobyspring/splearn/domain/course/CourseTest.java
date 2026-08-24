package tobyspring.splearn.domain.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.instructor.InstructorFixture;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    Course course;

    @BeforeEach
    void setUp() {
        course = CourseFixture.createCourse();
    }

    @Test
    void create() {
        Instructor instructor = InstructorFixture.createActiveInstructor();

        Course course = new Course(instructor, "title", "description");
        
        assertThat(course.getInstructor()).isEqualTo(instructor);
        assertThat(course.getTitle()).isEqualTo("title");
        assertThat(course.getStatus()).isEqualTo(CourseStatus.DRAFT);
        assertThat(course.getDetail().getDescription()).isEqualTo("description");
        assertThat(course.getDetail().getCreatedAt()).isNotNull();
    }

    @Test
    void createFailNotActiveInstructor() {
        Instructor instructor = InstructorFixture.createInstructor();
        assertThatThrownBy(() -> new Course(instructor, "title", null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void submitForReview() {
        course.submitForReview();

        assertThat(course.getStatus()).isEqualTo(CourseStatus.IN_REVIEW);

        assertThatThrownBy(() -> course.submitForReview())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void publish() {
        course.submitForReview();

        course.publish();

        assertThat(course.getStatus()).isEqualTo(CourseStatus.PUBLISHED);
        assertThat(course.getDetail().getPublishedAt()).isNotNull();

        assertThatThrownBy(() -> course.publish())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void archive() {
        course.submitForReview();
        course.publish();

        course.archive();

        assertThat(course.getStatus()).isEqualTo(CourseStatus.ARCHIVED);
        assertThat(course.getDetail().getArchivedAt()).isNotNull();

        assertThatThrownBy(() -> course.archive())
                .isInstanceOf(IllegalStateException.class);
    }
}
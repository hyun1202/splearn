package tobyspring.splearn.application.course.provided;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.domain.course.CourseStatus;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;
import tobyspring.splearn.support.test.BaseApplicationServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationServiceTest
@RequiredArgsConstructor
class CoursePublisherTest extends BaseApplicationServiceTest {
    final CoursePublisher coursePublisher;

    @BeforeEach
    void setUp() {
        prepareCourse();
    }

    @Test
    void submitForReview() {
        coursePublisher.submitForReview(course.getId());
        
        assertThat(course.getStatus()).isEqualTo(CourseStatus.IN_REVIEW);
    }

    @Test
    void publish() {
        coursePublisher.submitForReview(course.getId());
        coursePublisher.publish(course.getId());

        assertThat(course.getStatus()).isEqualTo(CourseStatus.PUBLISHED);
    }

    @Test
    void archive() {
        coursePublisher.submitForReview(course.getId());
        coursePublisher.publish(course.getId());
        coursePublisher.archive(course.getId());

        assertThat(course.getStatus()).isEqualTo(CourseStatus.ARCHIVED);
    }
}
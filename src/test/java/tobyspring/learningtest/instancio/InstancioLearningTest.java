package tobyspring.learningtest.instancio;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.domain.shared.Email;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InstancioLearningTest {
    @Test
    void user() {
        User user = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .generate(Select.field(User::getEmail), gen -> gen.net().email())
                .set(Select.field(User::getStatus), UserStatus.PENDING)
//                .supply(Select.field(User::getEmail), () -> new Email("")) // 직접 생성 로직 지정
                .create();

        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isNotEmpty();
        assertThat(user.getName()).isNotEmpty();
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    void userModel() {
        Model<User> model = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .generate(Select.field(User::getEmail), gen -> gen.net().email())
                .set(Select.field(User::getStatus), UserStatus.PENDING)
                .toModel();

        for (int i=0; i< 100; i++) {
            User user = Instancio.of(model).create();

            assertThat(user.getId()).isNull();
            assertThat(user.getEmail()).isNotEmpty();
            assertThat(user.getName()).isNotEmpty();
            assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        }
    }

    @Test
    void annotation() {
        UserRegisterRequest userRegisterRequest = Instancio.of(UserRegisterRequest.class).create();

        System.out.println(userRegisterRequest);

        assertThat(userRegisterRequest.email()).isNotEmpty();
        assertThat(userRegisterRequest.nickname()).isNotEmpty();
        assertThat(userRegisterRequest.password()).hasSizeBetween(8, 100);
    }
}

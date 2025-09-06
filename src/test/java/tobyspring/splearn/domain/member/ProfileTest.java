package tobyspring.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void profile(){
        new Profile("profile1234");
        new Profile("a123");
        new Profile("dfashjklw");
    }
    
    @Test
    void profileFail(){
        assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("toolongtoolongtoolong")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url(){
        var profile = new Profile("profile");

        assertThat(profile.url()).isEqualTo("@profile");
    }
}
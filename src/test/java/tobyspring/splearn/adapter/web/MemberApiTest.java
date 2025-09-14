package tobyspring.splearn.adapter.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.domain.member.MemberRegisterRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// 해당 클래스와 관련된 빈만 로딩해줌
@WebMvcTest(MemberApi.class)
@RequiredArgsConstructor
class MemberApiTest {
    @MockitoBean
    MemberRegister memberRegister;

    final MockMvcTester mvcTester;    // AssertJ와 접목되어있음..
    final ObjectMapper objectMapper;

    @Test
    void register() throws JsonProcessingException {
        Member member = MemberFixture.createMember(1L);
        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();

        when(memberRegister.register(any())).thenReturn(member);
        String requestJson = objectMapper.writeValueAsString(request);

        assertThat(mvcTester.post()
                           .uri("/api/members")
                           .contentType(MediaType.APPLICATION_JSON)
                           .content(requestJson))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.memberId").asNumber().isEqualTo(1);

        verify(memberRegister).register(request);
    }

    @Test
    void registerFail() throws JsonProcessingException {
        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest("invalid email");
        String requestJson = objectMapper.writeValueAsString(request);

        assertThat(mvcTester.post()
                           .uri("/api/members")
                           .contentType(MediaType.APPLICATION_JSON)
                           .content(requestJson))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }
}
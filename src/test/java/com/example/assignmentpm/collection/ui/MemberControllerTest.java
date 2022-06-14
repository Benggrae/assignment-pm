package com.example.assignmentpm.collection.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.assignmentpm.collection.dto.MemberRequest;
import com.example.assignmentpm.collection.dto.MemberResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("멤버를 수집한다.")
    void saveMember() throws Exception {
        final MemberRequest memberRequest = MemberRequest.builder()
                .hp("+8201055523456").name("이름").agreeYn("Y")
                .email("kbh@gamil.com").build();

        final MvcResult result = 멤버를_저장한다(memberRequest);
        final MemberResponse search = 멤버를_조회한다(result.getResponse().getHeader("Location"));

        assertAll(
                () -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(search.getName()).isEqualTo(memberRequest.getName()),
                () -> assertThat(search.getHp()).isEqualTo(memberRequest.getHp())
        );
    }

    @Test
    @DisplayName("멤버 동의를 받지 않은 경우 수집하지 않는다.")
    void saveNoAgree() throws Exception {
        final MemberRequest memberRequest = MemberRequest.builder()
                .hp("+8201055523456").name("이름").agreeYn("N")
                .email("kbh@gamil.com").build();

        final MvcResult result = 멤버를_저장한다(memberRequest);

        assertAll(
                () -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    @Test
    @DisplayName("유효성 체크")
    void validSaveMember() throws Exception {
        final MemberRequest memberRequest = MemberRequest.builder()
                .hp("").name("이름").agreeYn("N")
                .email("kbh@gamil.com").build();

        final MvcResult result = 멤버를_저장한다(memberRequest);

        assertAll(
                () -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    private MemberResponse 멤버를_조회한다(String location) throws Exception {
        return objectMapper.readValue(mockMvc.perform(get(location))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(Charset.defaultCharset()), MemberResponse.class);
    }


    private MvcResult 멤버를_저장한다(MemberRequest memberRequest) throws Exception{
        return mockMvc.perform(post("/collection/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRequest)))
                .andReturn();
    }




}